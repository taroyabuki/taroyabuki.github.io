---
date: 2011-12-30 14:11:58+00:00
title: Streaming APIで取得したつぶやきの処理方法
---

かつて、Streaming APIで大量のつぶやきをリアルタイムに「保存する方法」を紹介しました（[cURL編](http://blog.unfindable.net/archives/4230)・[Python編](http://blog.unfindable.net/archives/4257)）。つぶやきはJSON形式で保存していましたが、そこから何かを見出すためにはまず、JSON形式のデータを処理できなければなりません。ここではその方法を確認します。

Pythonで簡単に処理する方法を、@nokunoさんが紹介しています（[Twitter Streaming APIの使い方](https://web.archive.org/web/20151202031805/http://d.hatena.ne.jp:80/nokuno/20110117/1295227445)）。cURLで取得したつぶやきは、1行に1つずつファイルに格納されるので、次のように1行ずつ取り出してJSONデータをパースすればいいようです（ここでは`tweet['text']`、つまりつぶやきの本文だけを取り出しています）。

```python
#!/usr/bin/env python
import sys, json
for line in sys.stdin:
    tweet = json.loads(line)
    if 'text' in tweet:
        print(tweet['text'])
```

[cURL編](http://blog.unfindable.net/archives/4230)や[Python編](http://blog.unfindable.net/archives/4257)でつぶやきを保存したファイルがresult.datだとすると、「python3 parse.py < ressult.dat」などとして本文だけを取り出すことができそうです。

Streaming APIのsample.jsonならこれでもいいのですが、filter.jsonを使うときには、条件に合うつぶやきが途絶えることがあります。そういうときには、30秒ごとにCR LFが送信されてきて、それがファイルに書かれることになるので、上のように1行ずつ処理しようとすると、ValueErrorが発生して処理が途中で止まってしまいます（cURLの場合）。ですから、次のように例外を無視します。

```python
#!/usr/bin/env python
import sys, json
#from pprint import pprint
 
for line in sys.stdin:
    try:
        tweet = json.loads(line)
        #pprint(tweet)
        print(tweet['text'])
    except:
        pass
```

つぶやかれた日時は`tweet['created_at']`で取得できるのですが（このあたりのことは`pprint()`で確認できます）、この値は「Thu Dec 29 04:27:28 +0000 2011」のように標準時なので、日本ではちょっと使いにくいです。そこで、日本時間の年月日時分秒（20111231000000）のような表現に変換する関数を用意します。追記：日時の扱い方は、[Pythonの UTC ⇔ JST、文字列(UTC) ⇒ JST の変換とかのメモ](https://qiita.com/yoppe/items/4260cf4ddde69287a632)で紹介されている方法の方がよさそうです。

```python
#!/usr/bin/env python
import sys, json, time, calendar
#from pprint import pprint

def YmdHMS(created_at):
    time_utc = time.strptime(created_at, '%a %b %d %H:%M:%S +0000 %Y')
    unix_time = calendar.timegm(time_utc)
    time_local = time.localtime(unix_time)
    return int(time.strftime("%Y%m%d%H%M%S", time_local))

for line in sys.stdin:
    try:
        tweet = json.loads(line)
        #pprint(tweet)
        print(YmdHMS(tweet['created_at']), tweet['text'])
    except:
        pass
```

リツイートを除外したいときは、次のようにすればいいでしょう。

```python
for line in sys.stdin:
    try:
        tweet = json.loads(line)
        #pprint(tweet)
        if 'retweeted_status' not in tweet:
            print(YmdHMS(tweet['created_at']), tweet['text'])
    except:
        pass
```

[cURL編](http://blog.unfindable.net/archives/4230)で書いたように、こういうことをしているのは、Ustreamなどで配信された動画の整理のためなので、保存したつぶやきは、時間を指定してとりだせるようにしておきましょう。

以下のようなスクリプト（parse.py）を使えば、「python3 parse.py 20111230150000 20111230174600 < result.dat」などとして、日本時間の2011年12月30日15時から2011年12月30日17時46分までのつぶやきだけを取得できます（日時の指定は省略してもかまいません）。

```python
#!/usr/bin/env python
import sys, json, time, calendar
#from pprint import pprint

def YmdHMS(created_at):
    time_utc = time.strptime(created_at, '%a %b %d %H:%M:%S +0000 %Y')
    unix_time = calendar.timegm(time_utc)
    time_local = time.localtime(unix_time)
    return int(time.strftime("%Y%m%d%H%M%S", time_local))

argv = sys.argv
start_time = 0
end_time = 99999999999999
if 1 < len(argv):
    start_time = int(argv[1])
    end_time = int(argv[2])

for line in sys.stdin:
    try:
        tweet = json.loads(line)
        #pprint(tweet)
        if 'retweeted_status' not in tweet:
            tweet_time = YmdHMS(tweet['created_at'])
            if start_time <= tweet_time and tweet_time <= end_time:
                tweet_sec = tweet_time-start_time
                screen_name = tweet['user']['screen_name']
                text = tweet['text']
                url = "https://twitter.com/#!/%s/status/%s" % (screen_name, tweet['id_str'])
                #print tweet_sec, url, text
                #print text
                t = time.strptime(str(tweet_time), "%Y%m%d%H%M%S")
                print(time.strftime("%H:%M:%S", t), text)
    except:
        pass
```

どのような形式で出力するかは、後で何に使いたいかで決まります。たとえばyouTube上でキャプションを付けたいなら、YouTubeがサポートする形式（[参考](http://support.google.com/youtube/bin/answer.py?hl=ja&answer=100077)）にあわせるといいでしょう。

実際に、「#iwakamiyasumi」というハッシュタグで検索し保存したつぶやきのうち、「年末特番 ジャーナリスト休業直前！上杉隆氏ラストインタビュー」の配信時間中（2011年12月30日15:00から17:45）のものを抜き出してみました。（[結果](/supplement/20111230150000_iwakamiyasumi.txt)）

補足：日時の処理の応用例をもう一つ（Twitter登録時からの経過日数）

```python
#!/usr/bin/env python
import sys, json, time, calendar
from pprint import pprint

#日時の差の日数を計算する関数
def datetime_delta(datetime_start, datetime_end):
    start = time.strptime(datetime_start, '%a %b %d %H:%M:%S +0000 %Y')
    end = time.strptime(datetime_end, '%a %b %d %H:%M:%S +0000 %Y')

    start = time.mktime(start)
    end = time.mktime(end)

    delta = (end - start) / 60 / 60 / 24
    return delta
 
for line in sys.stdin:
    try:
        tweet = json.loads(line)
        #JSONを読めないときは、↓を有効にして、python parse.py < data.json | lessとかしてみる（qで終了）
        #pprint(tweet)
        user = tweet['user']
        user_created_at = user['created_at']
        screen_name = user['screen_name']
        created_at = tweet['created_at']
        delta = datetime_delta(user_created_at, created_at)
        print("{},{},{},{}".format(screen_name, user_created_at, created_at, delta))
    except Exception as e:
        #print(e.args)
        pass
```