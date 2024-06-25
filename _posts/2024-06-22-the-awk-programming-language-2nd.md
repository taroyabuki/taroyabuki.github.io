---
date: 2024-06-22 00:00:00+09:00
title: AWKのバイブル35年ぶりの改訂！
image: https://taroyabuki.github.io/images/2024-06-22-awk.jpg
twitter:
  card: summary
---

[『プログラミング言語AWK』](https://www.oreilly.co.jp/books/9784814400706/)35年ぶりの改訂．[第1版](https://ndlsearch.ndl.go.jp/books/R100000002-I000002015299)が初めて買ったIT本なので，感慨深いものがあります．かなり高かった気がしましたが，今見たら3400円でした．第2版は3630円．ノスタルジアマーケティングにやられて買いました．

西暦に和暦が並記されているのも，ノスタルジアマーケティングの一環でしょう．

> Awkは1977年（昭和52年），小規模なプログラムを対象に，テキストも数値も容易に操作できる簡潔なプログラミング言語として開発された．（p.ix）

6.3節「テキスト処理」で使うデータ，第1版の『不思議の国のアリス』と『ハックルベリー・フィンの冒険』に，伝道の書12:12からの次の引用が追加されていて，お疲れさまでしたという感じです．

> 書物を多く著すに際限はなく，多くを研究するは肉体を疲弊させる．

## AWKとは

AWKは**プログラム可能なフィルタ**です[^UNIX]．AWKを単独で使うというよりは，sed，grep，sort，uniqなど，他のコマンドと組み合わせた，いわゆるシェルスクリプトで使うことが多いです．シェルスクリプトを学んだことのある人は，AWKを知っているでしょう．

[^UNIX]: [カーニハン，パイク『UNIXプログラミング環境』（アスキー, 1985）](https://ndlsearch.ndl.go.jp/books/R100000002-I000001812930)

AWKは**汎用スクリプト言語**でもあります．私がAWKを初めて使ったときは，MS-DOS上で他に使える言語はC言語くらいしかなかったので，構文がC言語に似ていて，型指定が不要で，連想配列があるAWKは，とても魅力的でした．しかし，汎用スクリプト言語としてのAWKの役割は，後にPerlに，さらにはPythonに取って代わられたように思います．

言語の名前がAWKで，実装及びコマンド名がawkだと思っていたのですが，第2版で言語の表記がAwkに変わったことに困惑します．「見た目がうるさいため，本書ではAwkと表記した（p.xii）」とあるのですが，もうちょっと保守的になってもらいたいものです．（因みに，GNUの実装はGNU Awkで，その略称及びコマンド名がgawkだと思いますが，これもあまり自信がありません．）

困惑ついでに引用します．

> POSIX標準ではAwk言語を完全かつ厳密に定義している．しかし常に最新版ではないし，別実装のAwkは厳密には準拠していない．（p.xii）

そうは言っても，（その一部が）POSIX標準に含まれていることが，AWKが長く使われている理由の一つではあるでしょう．自分ではそう思っていなくても，それを理由に伝道している人の影響を受けているということも多そうです．ユーザからしたら，「`--csv`もPOSIXに入れてから改訂版を出してください」と言いたいところです．

## 改訂で変わったこと

『プログラミング言語AWK』の第1版は名著で，その日本語版は，トッパン（1989），シイエム・シイ出版部（2001），新紀元社（2004），USP研究所（2010）と，版元を変えながら出版され続けていました．第1版の原書は[Internet Archive](https://archive.org/details/pdfy-MgN0H1joIoDVoIC7)にあります．

第2版はどうでしょう．

日本語版まえがき（p.vii）によると，第2版では次の二つの重要な新機能についての記述を追加したとのことです．

- **CSV入力**：もともと，CSVの処理はAWKの得意分野でした．そこに，病的なCSV（後述）に対応するためのオプション「`--csv`」が追加されたのは，画竜点睛と言えるでしょう．
- **Unicode対応**：私がよく使うgawkはずいぶん前からUnicodeに対応していたと思います．あやふやなのは，私がバイナリとして処理できれば十分な場面でしかAWKを使ってこなかったからでしょう．

重要な変更がこの2点だけだとすると大改訂というわけではなさそうですが，ざっと見た感じでは，他にも次のような変更がありました．

- 新章「第2章 Awkの実践例」の追加
- 新章「第3章 探索的データ分析」の追加
- 第1版の「第2章 AWK言語」と「付録 AWKのまとめ」の，「付録A リファレンスマニュアル」への統合

細かいところでは，「1.6節 制御フロー文」にFizzBuzzが追加されたことと，第1版の「逆ポーランド電卓」と「普通の電卓」に「7.5 別アプローチ」（式の評価をAWKで行う）が追加されたことが挙げられます．

データサイエンスでは，AWKはデータを整形する前処理でしか使わない気がするので，新章「第3章 探索的データ分析」は意外でした．（それをAWKでやらなくてもいいでしょう，という意味で．しかも，方法や結果に間違いがある気がします．）

先に述べたように，便利な言語が他にもある今日では，AWKの用途は汎用スクリプト言語というよりはプログラム可能なフィルタだと思っているので，スクリプト言語としてできることが増えることよりも，フィルタとしての完成度が高まることを期待したわけですが，著者達の考えはそうではなかったようです．

とはいえ，35年ぶりのお祭りなので，不満は後に回しましょう．

## 実践

せっかくなので，少し試してみます．

- CSV
    - 通常の場合
    - 病的な場合
- Unicode
- 探索的データ分析
- おまけ：任意精度演算

Ubuntu 24.04のコンテナを使います．

```bash
docker run --rm -it ubuntu:noble
```

### AWKの処理系

AWKの実装は複数あります．Ubuntu 24.04には，gawk，mawk，original-awkがあります．全部入れると，`awk`は`gawk`になるようです．（`update-alternatives --config awk`で切り替えられます．）

```bash
apt update && apt install gawk mawk original-awk wget -y
```

各実装のバージョンを確認します．

```bash
dpkg-query -W gawk mawk original-awk
```

実装|バージョン
--|--
gawk|5.2.1-2build3
mawk|1.3.4.20240123-1build1
original-awk|2023-11-27-1

### CSV

#### 通常の場合

表計算ソフトウェアで次のような表を作ったとします．


||A|B|C
--|--|--|--
1|one|two|three
2|four|five|six

この表のデータを次のように変換したいとしましょう．

```
1 1 one
1 2 two
1 3 three
2 1 four
2 2 five
2 3 six
```

こういうタスクにAWKは向いています．コンマ区切りであること（コンマがフィールドの構成要素ではないこと）を表すオプション「`-F,`」を与えて処理して，上の結果を得ます．

```bash
echo -e "one,two,three\nfour,five,six" \
| awk -F, '{for(i=1;i<=NF;i++){print NR,i,$i}}'
```

#### 病的な場合

次のような，フィールドに改行が含まれる，病的な場合はどうでしょうか[^20]．古いAWKでこのような病的なCSVを扱うのはとても大変でした[^FPAT]．（こういう表を作るべきではないと思いますが，自分では作らなくても，誰かが作ったものを扱わなければならないことはあるでしょう．）

[^20]: [松浦智之『Windows/Mac/UNIX すべてで20年動くプログラムはどう書くべきか』（C&R研究所, 2016）](https://ndlsearch.ndl.go.jp/books/R100000002-I027689726)

[^FPAT]: gawk 4.0で導入された，フィールドの構成要素を指定するFPATでは，このCSVには対応できません．

||A|B|C|D
--|--|--|--|--
1|aaa|b"bb|c<br>cc|d d
2|f,f

CSVは次のとおりです．（「`"`」で挟まれたフィールド内の「`""`」は「"」のことです．）

```
aaa,"b""bb","c
cc",d d
"f,f"
```

ファイルsample.csvを作ります．

```bash
cd
echo -e 'aaa,"b""bb","c\ncc",d d\n"f,f"' > sample.csv
```

このような病的なCSVを扱う方法を二つ紹介します．

- parsrc.sh：文献[^20]で紹介されている方法です．POSIXの範囲内で実装されているということなので，おそらく20年後もこのまま動くでしょう．とはいえ，スクリプトはかなり複雑で，私は中身を確認せずに使っています（一応，文献[^20]の著者を信頼して）．
- `--csv`：POSIXには入っていませんが，gawk 5.3以降とoriginal-awkでサポートされています．これがあれば，parsrc.shと同じように動くスクリプトは簡単に書けます．

##### parsrc.sh

[parsrc.sh](https://github.com/ShellShoccar-jpn/Parsrs/blob/master/parsrc.sh)を使って，sample.csvを処理します．

```bash
wget https://raw.githubusercontent.com/ShellShoccar-jpn/Parsrs/master/parsrc.sh
sh parsrc.sh sample.csv
```

```
1 1 aaa
1 2 b"bb
1 3 c\ncc
1 4 d d
2 1 f,f
```

##### `--csv`

CSVのためのオプション`--csv`を使って，sample.csvを処理します．

###### original-awk

original-awkで先と同じ結果を得ます．

```bash
/usr/bin/original-awk --csv '{for(i=1;i<=NF;i++){gsub(/\n/,"\\n");print NR,i,$i}}' sample.csv
```

スクリプトを整形して，コメントを加えます．C言語の構文に慣れていれば，解読は容易でしょう．POSIX原理主義には敬意を表しますが，このアプローチの方が良いと思います．

```awk
{ # 各レコードについての処理
    for (i=1; i<=NF; i++) { # NFは処理中のレコードのフィールド数
        gsub(/\n/, "\\n");  # gsubはレコード全体の置換
        print NR, i, $i     # NRは処理したレコード数．$iはi番目のフィールド
    }
}
```

###### gawk 5.3

Ubuntu 24.04のaptで入るgawk 5.2.1は「`--csv`」をサポートしていません．そこで，「`--csv`」をサポートするgawk 5.3をビルドします．せっかくビルドするので，任意精度演算（後述）も有効にします．（任意精度演算が不要な場合は，1行目を省きます．）

```bash
apt install build-essential libgmp-dev libmpfr-dev -y
wget https://ftp.gnu.org/gnu/gawk/gawk-5.3.0.tar.xz
tar xf gawk-5.3.0.tar.xz
cd gawk-5.3.0
./configure
make -j$(nproc)
```

`./gawk --version`の結果が次のようになればビルドは成功です．

```
GNU Awk 5.3.0, API 4.0, PMA Avon 8-g1, (GNU MPFR 4.2.1, GNU MP 6.3.0)
```

gawk 5.3で先と同じ結果を得ます．

```bash
./gawk --csv '{for(i=1;i<=NF;i++){gsub(/\n/,"\\n");print NR,i,$i}}' ../sample.csv
```

### Unicode

「𠮷野家」の文字数を求めて，10を得ます（誤り）．これは文字数ではなくバイト数です[^4]．

[^4]: 「ASCII文字はすべて1バイト長だが，他の言語では2バイト長，3バイト長の文字がある（p.49）」とありますが，それで終わりではありません．例えば「𠮷」は4バイトです．因みに，「吉」は3バイトで，これを使うのが正しいと思います（「つちよし」にしたいときは，「吉」の字形がそうなっている書体を使います）．

```bash
/usr/bin/original-awk 'BEGIN{print length("𠮷野家")}' # 10
/usr/bin/gawk         'BEGIN{print length("𠮷野家")}' # 10
```

`LC_ALL`を設定してやり直して，3を得ます（正解）．

```bash
export LC_ALL=C.UTF-8
/usr/bin/original-awk 'BEGIN{print length("𠮷野家")}' # 3
/usr/bin/gawk         'BEGIN{print length("𠮷野家")}' # 3
```

### 探索的データ分析

「3.4節 Unicode文字」で，ビールの評価データreviews.csvに含まれる文字の出現頻度を求めるというタスクが紹介されています．それを試します．

reviews.csvを用意します．

```bash
cd
mkdir -p programs
wget -qO- https://www.awk.dev/programs.tar | tar xf - -C programs
gunzip programs/reviews.csv.gz
```

#### シェルスクリプト

まずは何も考えずに，1行1文字に分割して，sort，uniq，sortです（**約100秒**）．

```bash
export LC_ALL=C.UTF-8
time sed 's/./&\n/g' programs/reviews.csv | sort | uniq -c | sort -nr > result1
cat result1
```

#### AWK

AWKの改良版（p.53）を試します（**約26秒**）．

```bash
time programs/charfreq2 programs/reviews.csv > result2
cat result2
```

#### Python

Python版（p.54）を試します（**約28秒**）．

```bash
DEBIAN_FRONTEND=noninteractive apt install python3 -y
ln -s programs beer
(
  cd programs
  time python3 charfreq.py | sort -k2 -nr > ../result3
)
cat result3
```

これだけ見ると，AWKとPythonの性能がだいたい同じにみえます．しかし，Pythonではもう少し普通の書き方ができそうです．Pythonでは，頻度を数えるようなよくある処理は，モジュールになっています．

`Counter`を使います．頻度の高い順での出力もPythonで完結します（**約6秒**）．

```bash
(
cd programs
cat << 'EOF' > charfreq2.py
from collections import Counter

with open('../beer/reviews.csv', encoding='utf-8') as f:
  freq = Counter(f.read())
  for ch, count in freq.most_common():
    if ch != '\n':
      print(f'{ch}\t{count}')
EOF

time python3 charfreq2.py > ../result4
)
cat result4
```

#### 結果の確認

AWKの改良版（charfreq2）では集計結果を`sort -k2 -nr`に流していますが，これでは空白が最後になってしまいます．フィールドの区切りがタブ（`\t`）であることを明示して，この問題を解決します．

```bash
sort -t$'\t' -k2 -nr result2 > result2a
cat result2a
```

結果（result2a, result4）は次のとおりです．

```
,	19094985
e	12308925
 	10586176
r	8311408
4	7269630
a	7014111
5	6993858
...
À	233
黑	229
Ô	216
...
```

もっとも使用頻度が高いのは「,」なので，次は誤りです（空白文字は3番目）．

> もっとも使用頻度が高いのは空白文字で，以降に通常文字（印刷可能文字）が並ぶ．(p.54)

「黑」は最後ではないので，次も誤りです．

> 最後の文字「黑（hēi）」は，(p.55)

第1版の「訳者の序」によると，第1版の翻訳では，機械可読型の原稿から抜き出したコードをテストして，その出力を原稿に取り入れたそうです．第2版では，原書でも日本語版でも，そういう手間はかけられなかったのでしょう．

AWKは遅く，sortとの連携にも落とし穴がありました．探索的データ分析には，AWKより，ライブラリの充実したPythonの方が向いていると思います．

### 任意精度演算

これはgawk 4.1.1以降だけの話です．他の実装が追従することはおそらくないでしょう．『プログラミング言語AWK』にも，これに関する記述はありません．gawkの本家でも[こんな感じ](https://www.gnu.org/software/gawk/manual/html_node/MPFR-On-Parole.html)なので，この機能はそのうちなくなるかもしれません．

通常はフェルマー・ワイルズの定理の「反例」になるものが，gawkにオプション「`-M`」を付けて多倍長整数で計算すると，反例ではないことがわかります．

```bash
export a=5999856 b=99992800 c=100000000
/usr/bin/original-awk "BEGIN{print($a^3+$b^3==$c^3)}" # 1（反例）
/usr/bin/mawk         "BEGIN{print $a^3+$b^3==$c^3}"  # 1（反例）
/usr/bin/gawk         "BEGIN{print $a^3+$b^3==$c^3}"  # 1（反例）
/usr/bin/gawk -M      "BEGIN{print $a^3+$b^3==$c^3}"  # 0（反例ではない）
```

次のような，怪しい計算もできます．

```bash
export a=0.1 b=0.2 c=0.3
/usr/bin/original-awk       "BEGIN{print($a+$b==$c)}" # 0（等しくない）
/usr/bin/mawk               "BEGIN{print $a+$b==$c}"  # 0（等しくない）
/usr/bin/gawk               "BEGIN{print $a+$b==$c}"  # 0（等しくない）
/usr/bin/gawk -M -v PREC=16 "BEGIN{print $a+$b==$c}"  # 1（等しい）
```

## 第2版日本語版への不満

『プログラミング言語AWK』第2版日本語版には，不満が三つあります．

第1に，第1版日本語版や第2版原書の図はベクター形式でくっきりきれいなのに，第2版日本語版の図は早すぎるラスタライズが行われたようで，品質がとても悪いです．

第2に，コード中のコメントの訳が読みにくいです．p.16から引用します．

```awk
# interest1 - compute compound interest
#   input:  amount  rate  years
#   output: compounded value at the end of each year

{   i = 1
    while (i <= $3) {
        printf("\t%.2f\n", $1 * (1 + $2) ^ i)
        i++
    }
}
(コメント訳)
interest1－複利計算
入力：元金 利率 年数
出力：年末時点の複利累積額
```

このように，コード中のコメントは翻訳されず，コードの後に翻訳されたコメントが掲載されています．コメントが20個くらいあるコードでもこういう調子なので，とても読みにくいです．コードを置き換えてテストに影響するのを避けたかったのかとも思いましたが，「探索的データ分析」の例を見ると，そもそもテストはしてなさそうなので，不思議です．（方針を途中で変えた？）

第3に，公開されいるコードが扱いにくいです．コードや演習の模範解答が一つのアーカイブ[https://www.awk.dev/programs.tar](https://www.awk.dev/programs.tar)で公開されています．このアーカイブを展開すると，フォルダに分けられていない400個のファイルになります．そこから目的のファイルを探し出すのがとても大変です．本に掲載されているコードならgrepでいいのですが，演習の模範解答の場合，それがあるのかどうかもわからないところで探さなければなりません．因みに，第1版では演習の模範解答は紙面（付録B 演習問題解答）に掲載されていました．

<blockquote class="twitter-tweet"><p lang="ja" dir="ltr">『プログラミング言語AWK』が35年ぶりの改訂とのこと<br><br>左は，私が初めて買った，大人向けのIT本，税込3400円（K&amp;Rの次だったかも）<br><br>右は，私が2000冊目くらいに買った，大人向けのIT本，税込3630円 <a href="https://t.co/8AOMsE8rSb">pic.twitter.com/8AOMsE8rSb</a></p>&mdash; Taro Yabuki (@yabuki) <a href="https://twitter.com/yabuki/status/1804128347435471013?ref_src=twsrc%5Etfw">June 21, 2024</a></blockquote> <script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script>