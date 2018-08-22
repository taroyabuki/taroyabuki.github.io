---
date: 2016-05-03 13:50:08+00:00
title: 塗り分けに五色必要な地図
---

四色あれば，地図上の隣り合う領域の色が同じにならないように塗り分けられるという「四色定理」は，1800年代後半に予想され，1976年にコンピュータを使って「証明」された。

[![4102184619](https://images-fe.ssl-images-amazon.com/images/P/4102184619.09.jpg)](http://www.amazon.co.jp/dp/4102184619?tag=inquisitor-22)

定理が「証明」される前の1975年に，マーチン・ガードナーが塗り分けに五色必要だとして発表した次の絵が話題になったという。（参考：[Martin Gardner's April Fool's Map](http://mathforum.org/wagon/fall97/p840.html)）

![](/images/2016-05-03-4color-1.png)

これはエイプリルフールのネタだったのだが，四色で塗り分けたという手紙が数百通届いたらしい。（[ロビン・ウィルソン『四色問題』（新潮社, 2013）](http://www.amazon.co.jp/dp/4102184619?tag=inquisitor-22)p.38）

この大人の塗り絵をやってみたい。

[![0387753664](https://images-fe.ssl-images-amazon.com/images/P/0387753664.09.jpg)](http://www.amazon.co.jp/dp/0387753664?tag=inquisitor-22)

[Mathematica in Action](http://www.amazon.co.jp/dp/0387753664?tag=inquisitor-22) で，塗り分ける方法が紹介されているのだが，[http://extras.springer.com/](http://extras.springer.com/)からダウンロードできるコードは，最近のMathematicaでは動かない。（Mathematicaの言語仕様は後方互換性を保持しながら進化しているのだが，外部パッケージが本体に取り込まれた場合は，大抵うまくいかない。）

そこで，簡易版を作る。領域の境界線が垂直または水平の2pxの黒い実線の場合にのみ対応するという意味で「簡易」である。

`Import`で画像を読み込み，`MorphologicalComponents`で領域に分割する（`Colorize[matrix]`で描画）。

![](/images/2016-05-03-4color-2.png)

四色で塗り分ける。（参考：[ヨーロッパの地図の4色を求める](https://www.wolfram.com/mathematica/new-in-10/entity-based-geocomputation/find-a-four-coloring-of-a-map-of-europe.html)）

![](/images/2016-05-03-4color-3.png)

<script src="https://gist.github.com/taroyabuki/a906b537b1230e3bb29fba4f8f71845c.js"></script>

色を1組の真偽値で表し，色が同じでないという条件を連言標準形で記述することで，高速化している。

細かい注意：上の結果は周りが海に囲まれていても大丈夫なように，条件を追加して求めたもので，このコードの結果とはちょっと違うものになっている。

最後の描画は`Colorize[matrix, ColorRules -> cTable]`でもいいのだが，この関数にはバグがあり，Mathematica 10.4.1では正しく動作しない。（製造元には報告済み。Ver.11で修正された。）

<blockquote class="twitter-tweet" data-lang="ja"><p lang="en" dir="ltr">Code submitted by <a href="https://twitter.com/yabuki">@yabuki</a> <a href="https://twitter.com/hashtag/wolframlang?src=hash">#wolframlang</a> Source: <a href="https://t.co/1kAhicTjVR">https://t.co/1kAhicTjVR</a> <a href="https://t.co/baH0wwxQqC">pic.twitter.com/baH0wwxQqC</a></p>&mdash; Tweet-a-Program (@wolframtap) <a href="https://twitter.com/wolframtap/status/726864543519895552">2016年5月1日</a></blockquote>
<script async src="//platform.twitter.com/widgets.js" charset="utf-8"></script>


