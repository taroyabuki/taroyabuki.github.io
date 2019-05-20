---
date: 2012-06-28 14:02:56+00:00
title: 吉田武『オイラーの贈物』
---

吉田武『オイラーの贈物』は、オイラーの公式「exp(i x) = cos x + sin x」を理解することを目標に掲げた数学の入門書です。対象年齢は10代後半くらいかと思います。

1. 1993年に[ハードカバー](https://www.amazon.co.jp/dp/487525153X?tag=inquisitor-22)（456ページ）で出版され、
1. 2001年に[文庫化](https://www.amazon.co.jp/dp/4480086757?tag=inquisitor-22)（516ページ）、
1. 2010年に[ソフトカバーの新装版](https://www.amazon.co.jp/dp/448601863X?tag=inquisitor-22)（516ページ）が出ました。

![表紙](https://images-fe.ssl-images-amazon.com/images/P/487525153X.09.jpg)

新装版あとがきによれば、これだけ版が変わっているのにはいろいろと大人の事情があるようなのですが、約1000ページの大著[『虚数の情緒』](https://www.amazon.co.jp/dp/4486014855?tag=inquisitor-22)で全方位独学法を提唱している著者にすれば、数学の不変性と出版業界の変化の激しさを同時に見せているこの状況は、まさに「してやったり」というところなのかもしれません。

高校生だった当時は、気になっていた数学教育上の（細かい）問題がなおざりだったのでスルーしましたが、高校合格祝いに弟に贈った思い出の本です。プレゼント用には、最初のハードカバー版が一番いいです。

一冊の本としては分厚いほうではありますが、高校生が本当にこれで「オイラーの公式」までの数学を楽しんで身につけられるかというと、よくわかりません。私自身、「数学をちゃんと勉強してから物理をやろう」というマジメな塾に通っていたので、「オイラーの公式」までの数学は高校時代に理解していたと思いますが、そこにいたるまでのトレーニングを全部あわせると、この本の分量を遙かに超えています。ですからこの本は、高校生が数学を勉強するためのものではなく、すでに一度学んだ人が一冊で気分良く復習する（懐かしむ）ためのものだと考えた方がいいでしょう。

「オイラーの公式」を前面に押し出している本書ですが、この公式の証明自体は516ページある新装版の236ページで済んでいて、残りは発展的話題と数表、問題解答にあてられています。発展的話題とオイラーの公式との関連が薄いのが残念なところで、オイラーの公式を目指してせっかく頂上まで登ったのに、その眺望を楽しませてはもらえません。眺望を楽しむには、似たような企画である、[ナーイン『オイラー博士の素敵な数式』（日本評論社）](https://www.amazon.co.jp/dp/4535784779?tag=inquisitor-22)が向いてそうですが、こちらで扱われる数学は、『オイラーの贈物』よりずいぶん高度なものなっています（大学後半レベル）。

そもそも、一つの目標だけを掲げて数学を勉強するのは、冷静に考えればけっこう空しいことのように思いますが、「売れる企画」であったことは間違いありません。この形式を思いついたのはすごいと思います。新装版あとがきで著者自身が言うほどの「名著」かどうかは議論のあるところかもしれませんが、この本を出発点にして、いろいろ楽しめることは確かです。一例を挙げます。

『新装版』第1版第7刷の付録A p. 314 には、モンテカルロ法を用いて円周率の近似値を求める、次のようなUBASICのコードが掲載されています（参照：[UBASICの動かし方](/2012/07/12/ubasic/)）。

```basic
10   randomize
20   input "10^n=";n
30   for i=1 to n
40   k=0:j=0
50   for j=1 to 10^i-1
60   x=rnd:y=rnd:if x*x+y*y<=1 then k=k+1
70   next j
80   print 10^i;"点中";k;"個命中";"Pi=";4*k/j
90   next i
```

このコードには、1つのバグといくつかの問題があります。

## バグ

50行目のfor文で、`j`は`1`から`(10^i-1)`まで動くので、モンテカルロシミュレーションの試行回数は`(10^i-1)`回になります。しかし、80行目では試行回数を`10^i`あるいは`j`（ループが終了した時点で`j==10^i`）と、1回多く数えています。これにあわせるためには、50行目を次のように修正しなければなりません。

```basic
50   for j=0 to 10^i-1
```

## 問題

プロンプトがおかしい、とかいう細かい話はおくとして、

### 問題（無駄な計算）

50行目のfor文が終わると（80行目の直前）`j`の値は`10^i`になっているので、80行目で`10^i`を再度計算する必要はありません。80行目の末尾では、`10^i`の意味で`j`を使っているので、先頭でもそれを採用するといいでしょう。つまり、80行目は次のように書いた方がいいでしょう。

```basic
80   print j;"点中";k;"個命中";"Pi=";4*k/j
```

### 問題（無駄な代入）

40行目の「`:j=0`」は、その直後に「`j=1`」があるので無駄です。しかし、次の問題の解決に必要なので、そのままにしておいてもいいでしょう。

### 問題（for文の範囲）

UBASICの仕様はよくわかりませんが、手元のバージョン8.8fでは、for文の範囲（上限ではない）が2^32を超えると「数値が大き過ぎます」というエラーが出ます。ですから、せっかく大きな桁数をサポートするUBASICを使っても、このプログラムでは試行回数`10^9`回までしかシミュレートできません。本文のコラム（p. 226）で紹介されている実行結果もそうなっています。これではもったいないので、for文ではなくwhile文を使うようにするといいでしょう。

```basic
45   jMax=10^i
50   while j<jMax
60   x=rnd:y=rnd:if x*x+y*y<=1 then k=k+1
65   j=j+1
70   wend
```

### 問題というか要望（プログラミング言語の選択）

1993年に初版が出版されて以来、本書は売れ続け、つまり生き続けているわけですが、利用されている言語UBASICのほうは瀕死の状態、64bit版Windows 7では動きません。ですから、長生きしそうなプログラミング言語を採用し、改訂していただきたいものです。大きな桁の整数のサポートが必要です。C言語＋GMPなら寿命は長そうですがハードルが高いので、サポートが組み込まれているJava, Ruby, Python, Lispなどがいいでしょう。Javaに翻訳すると、下のようになります（まず紹介すべきはBigIntegerではなくintを使うバージョンですが、ここでは省略します）。


```java
import java.util.*;
import java.math.*;

public class MonteCarlo {

  public static void main(String[] args) {
    BigInteger one = new BigInteger("1");

    System.out.print("10^n=");
    int n = new Scanner(System.in).nextInt();

    for (int i = 1; i <= n; i++) {
      BigInteger jMax = new BigInteger("10").pow(i);
      BigInteger k = new BigInteger("0");
      for (BigInteger j = k; j.compareTo(jMax) < 0; j = j.add(one)) {
        double x = Math.random();
        double y = Math.random();
        if (x * x + y * y <= 1) {
          k = k.add(one);
        }
      }
      System.out.printf("%s 点中 %s 個命中 Pi= %f\n",
          jMax, k, 4. * k.doubleValue() / jMax.doubleValue());
    }
  }
}
```

Javaで書くとなんかごちゃごちゃしていやですね。言語仕様が安定していない印象が強いRubyとPythonは採用しにくいので、残るのはLispですか。それなら、[『素数夜曲 女王陛下のLISP』](https://www.amazon.co.jp/dp/4486019245?tag=inquisitor-22)がちょうどいいですね（モンテカルロ法も扱われているようですし）。

Mathematicaなら↓のとおり。

<blockquote class="twitter-tweet" data-lang="ja"><p lang="en" dir="ltr">(More info: <a href="https://t.co/HBUuaZOB3w">https://t.co/HBUuaZOB3w</a>) <a href="https://twitter.com/hashtag/wolframlang?src=hash&amp;ref_src=twsrc%5Etfw">#wolframlang</a> <a href="https://t.co/Beh5KZ3wAV">pic.twitter.com/Beh5KZ3wAV</a></p>&mdash; Tweet-a-Program (@wolframtap) <a href="https://twitter.com/wolframtap/status/971020837695475712?ref_src=twsrc%5Etfw">2018年3月6日</a></blockquote>
<script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script>