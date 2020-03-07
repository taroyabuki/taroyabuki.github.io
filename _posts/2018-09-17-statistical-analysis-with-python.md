---
date: 2018-09-17 00:00:00+09:00
title: 谷合廣紀（著），辻真吾（監修）『Pythonで理解する統計解析の基礎』
---

追記：著者の谷合廣紀さんは，2020年3月に4段昇段を決めました。

[谷合廣紀（著），辻真吾（監修）『Pythonで理解する統計解析の基礎』（技術評論社，2018）](https://www.amazon.co.jp/exec/obidos/asin/4297100495/inquisitor-22/)は，統計学の勉強にPythonを活用することを提案する書籍です。「PYTHON × MATH SERIES」というシリーズの最初の1冊のようです。勉強に使う道具として紙と鉛筆を想定して書かれた教科書を読むのはなかなか大変なので，こうやってコンピュータを活用する教科書がどんどん出てくるといいと思います。

対象を統計に限定するなら（現時点では）PythonよりRが向いていると思いますし，実際，Rで統計を学ぶ教科書はたくさん出ていますが，統計以外も扱うシリーズを展開するならRより汎用的なPythonで，ということになるのでしょう。

Rubyを使ったシリーズになりそうでならなかった[『プログラミングのための確率統計』](https://www.amazon.co.jp/exec/obidos/asin/4274067750/inquisitor-22/)も良書でしたが，『Pythonで理解する統計解析の基礎』はほとんどすべての事例にPythonでの確認作業が付いているという点で，コンピュータを活用するという方針が徹底しています。

（個人的には，統計限定でもシリーズでも，RやPython，RubyよりMathematicaがいいと思いますが，それはまた別の話。）

[![表紙](https://images-fe.ssl-images-amazon.com/images/P/4297100495.09.jpg)](https://www.amazon.co.jp/exec/obidos/asin/4297100495/inquisitor-22/)

著者の谷合さんは，将棋の奨励会三段かつ東大大学院情報理工学研究科の院生だそうです。どちらもプロの一歩手前（？）とはいえ，こういう肩書を併せ持つというのはかなりすごいことです。

閑話休題。本書は，コンピュータ（Python）を活用することで効率よく統計を学ぶことを目指していて，その目標は達成されていると思います。Python自体やプログラミングを学ぶための本ではないので，そういうものを探している人は，他をあたった方がいいでしょう。

「SciPyで一発！」みたいなのが私の好みですが，そういう過激なことはせずに，定義どおりの計算をNumPyで地味にやってみせてから，実はSciPyなら一発という具合で説明されているので（例外あり），正統派（？）からも怒られないのではないかと思います。ただ，NumPyでの地味な計算とSciPyでのお手軽な計算が見分けにくいところがあるので，お手軽なところに気付かずに，「地味で大変だなあ」で終わってしまわないか心配です。（そういう人へ：よく読むと，正確で手軽な方法が後で出てきますよ。）

何というか，本書は将棋の指導対局のようで，著者（上手）はいろいろ読みを入れているにもかかわらず，それをぶちまけて学習者（下手）を溺れさせるようなことはせず，最も気持ちいい形で理解できるように配慮して書かれています。ただ，ちょっと親切すぎるというか，狙いすぎているところがあって，いざ読者が自分で試してみようと思ったときに，著者が見えないところで周到にしていた配慮に気付かずにハマってしまう，ということがあるかもしれません。（指導対局で理解したはずの手筋を実戦で試してうまく行かなかったからといって，指導対局が悪かったということにはなりません。）

例を挙げましょう。（読者が気にしなくてすむように著者が配慮したところをあえて蒸し返します。）

1. xが出る確率密度がf(x)=2x (0 ≦ x ≦ 1)であるような，（整数でなく実数を出力する）ルーレットがあるとします。（p.126）
1. ルーレットを2回まわして，出た数をA，Bとします。(p.138)
1. X = A + B，Y = Aとします（A = Y，B = X - A = X - Y）。
1. このとき，(X, Y)の確率密度f(x, y)は0 ≦ y ≦ 1かつ0 ≦ x - y ≦1のときは 4y(x - y)，それ以外のときは0となります。

私にはSciPyすら大変なので，Mathematicaで確認します。

![画面キャプチャ1](/images/2018-09-17-statistical-analysis-with-python-1.png)

キャプチャ中のコードは以下の通りです。クラウド（[こちら](https://develop.open.wolframcloud.com/app/view/newNotebook?ext=nb)あるいは[こちら](https://lab.open.wolframcloud.com/app/view/newNotebook?ext=nb)）でも動きます。

```
P = ProbabilityDistribution[2 x, {x, 0, 1}];(*ルーレットの確率分布*)

n = 10^4;
ListPlot[
 With[{A = RandomVariate[P, n], B = RandomVariate[P, n]},
  Transpose[{A + B, A}]]]

X = A + B;
Y = A;

f := TransformedDistribution[{X, Y}, {Distributed[A, P], Distributed[B, P]}];(*確率分布*)
pdf = PDF[f][{x, y}] // FullSimplify(*密度関数の数式*)

ListPlot[RandomVariate[f, n]]

DensityPlot[pdf, {x, 0, 2}, {y, 0, 1}, PlotPoints -> 50]
```

周辺確率密度関数を求めます。「2変数関数のうち1変数のみで積分するといった関数は実装されていない（p.141）」Pythonでは面倒ですが，Mathematicaでは，式をそのまま書くだけです（ここで扱っている例なら解析的に計算できます。結果は割愛）

```
tmp = Integrate[pdf, {y, -Infinity, Infinity}];
Plot[tmp, {x, 0, 2}](*図7.6左*)

tmp = Integrate[pdf, {x, -Infinity, Infinity}];
Plot[tmp, {y, 0, 1}](*図7.6右*)
```

ここまでは問題なく進められるのですが，先の手順4を，次のように誤解されるのが心配です。

(X, Y)の確率密度f(x, y)は0 ≦ A = y ≦ 1かつ0 ≦ B = x - y ≦1のときは **f(A)f(B) = f(y)f(x - y) =** 4y(x - y)，それ以外のときは0となります。**（誤解）**

こういう理解でX = A + B^2，Y = A （A = Y，B = sqrt(X - Y)）の場合を試すと失敗します。f(x, y) = 4y(x - y)だと誤解しているからです。正しくは，∬f(A)f(B)dAdB = ∬f(y)f(sqrt(x - y)))\|J\|dxdyなのでf(x, y) = f(y)f(sqrt(x - y))\|J\| = 2yです（J = 1 / 2 / sqrt(x - y)はヤコビアン）。

誤解の原因は，「誤解されても大丈夫なように，\|J\|が1になる変換（X = A + B，Y = A）を著者が選んだこと」です（素人読み）。

Mathematicaで確認します。

![画面キャプチャ2](/images/2018-09-17-statistical-analysis-with-python-2.png)

キャプチャ中のコードは以下の通りです。

```
ListPlot[
 With[{A = RandomVariate[P, n], B = RandomVariate[P, n]},
  Transpose[{A + B^2, A}]]]

X = A + B^2;
Y = A;

pdf = PDF[f][{x, y}] // FullSimplify

ListPlot[RandomVariate[f, n]]

DensityPlot[pdf, {x, 0, 2}, {y, 0, 1}, PlotPoints -> 50]
```

こういう具合に，指導対局の上手の工夫を探ろうと思うと，より一層楽しめる一冊です。

追記：「因果」って書いているところは全部「相関」に置き換えていいのではないかと思います。

<blockquote class="instagram-media" data-instgrm-captioned data-instgrm-permalink="https://www.instagram.com/p/Bn1LNKJhRSo/?utm_source=ig_embed_loading" data-instgrm-version="12" style=" background:#FFF; border:0; border-radius:3px; box-shadow:0 0 1px 0 rgba(0,0,0,0.5),0 1px 10px 0 rgba(0,0,0,0.15); margin: 1px; max-width:540px; min-width:326px; padding:0; width:99.375%; width:-webkit-calc(100% - 2px); width:calc(100% - 2px);"><div style="padding:16px;"> <a href="https://www.instagram.com/p/Bn1LNKJhRSo/?utm_source=ig_embed_loading" style=" background:#FFFFFF; line-height:0; padding:0 0; text-align:center; text-decoration:none; width:100%;" target="_blank"> <div style=" display: flex; flex-direction: row; align-items: center;"> <div style="background-color: #F4F4F4; border-radius: 50%; flex-grow: 0; height: 40px; margin-right: 14px; width: 40px;"></div> <div style="display: flex; flex-direction: column; flex-grow: 1; justify-content: center;"> <div style=" background-color: #F4F4F4; border-radius: 4px; flex-grow: 0; height: 14px; margin-bottom: 6px; width: 100px;"></div> <div style=" background-color: #F4F4F4; border-radius: 4px; flex-grow: 0; height: 14px; width: 60px;"></div></div></div><div style="padding: 19% 0;"></div><div style="display:block; height:50px; margin:0 auto 12px; width:50px;"><svg width="50px" height="50px" viewBox="0 0 60 60" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"><g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd"><g transform="translate(-511.000000, -20.000000)" fill="#000000"><g><path d="M556.869,30.41 C554.814,30.41 553.148,32.076 553.148,34.131 C553.148,36.186 554.814,37.852 556.869,37.852 C558.924,37.852 560.59,36.186 560.59,34.131 C560.59,32.076 558.924,30.41 556.869,30.41 M541,60.657 C535.114,60.657 530.342,55.887 530.342,50 C530.342,44.114 535.114,39.342 541,39.342 C546.887,39.342 551.658,44.114 551.658,50 C551.658,55.887 546.887,60.657 541,60.657 M541,33.886 C532.1,33.886 524.886,41.1 524.886,50 C524.886,58.899 532.1,66.113 541,66.113 C549.9,66.113 557.115,58.899 557.115,50 C557.115,41.1 549.9,33.886 541,33.886 M565.378,62.101 C565.244,65.022 564.756,66.606 564.346,67.663 C563.803,69.06 563.154,70.057 562.106,71.106 C561.058,72.155 560.06,72.803 558.662,73.347 C557.607,73.757 556.021,74.244 553.102,74.378 C549.944,74.521 548.997,74.552 541,74.552 C533.003,74.552 532.056,74.521 528.898,74.378 C525.979,74.244 524.393,73.757 523.338,73.347 C521.94,72.803 520.942,72.155 519.894,71.106 C518.846,70.057 518.197,69.06 517.654,67.663 C517.244,66.606 516.755,65.022 516.623,62.101 C516.479,58.943 516.448,57.996 516.448,50 C516.448,42.003 516.479,41.056 516.623,37.899 C516.755,34.978 517.244,33.391 517.654,32.338 C518.197,30.938 518.846,29.942 519.894,28.894 C520.942,27.846 521.94,27.196 523.338,26.654 C524.393,26.244 525.979,25.756 528.898,25.623 C532.057,25.479 533.004,25.448 541,25.448 C548.997,25.448 549.943,25.479 553.102,25.623 C556.021,25.756 557.607,26.244 558.662,26.654 C560.06,27.196 561.058,27.846 562.106,28.894 C563.154,29.942 563.803,30.938 564.346,32.338 C564.756,33.391 565.244,34.978 565.378,37.899 C565.522,41.056 565.552,42.003 565.552,50 C565.552,57.996 565.522,58.943 565.378,62.101 M570.82,37.631 C570.674,34.438 570.167,32.258 569.425,30.349 C568.659,28.377 567.633,26.702 565.965,25.035 C564.297,23.368 562.623,22.342 560.652,21.575 C558.743,20.834 556.562,20.326 553.369,20.18 C550.169,20.033 549.148,20 541,20 C532.853,20 531.831,20.033 528.631,20.18 C525.438,20.326 523.257,20.834 521.349,21.575 C519.376,22.342 517.703,23.368 516.035,25.035 C514.368,26.702 513.342,28.377 512.574,30.349 C511.834,32.258 511.326,34.438 511.181,37.631 C511.035,40.831 511,41.851 511,50 C511,58.147 511.035,59.17 511.181,62.369 C511.326,65.562 511.834,67.743 512.574,69.651 C513.342,71.625 514.368,73.296 516.035,74.965 C517.703,76.634 519.376,77.658 521.349,78.425 C523.257,79.167 525.438,79.673 528.631,79.82 C531.831,79.965 532.853,80.001 541,80.001 C549.148,80.001 550.169,79.965 553.369,79.82 C556.562,79.673 558.743,79.167 560.652,78.425 C562.623,77.658 564.297,76.634 565.965,74.965 C567.633,73.296 568.659,71.625 569.425,69.651 C570.167,67.743 570.674,65.562 570.82,62.369 C570.966,59.17 571,58.147 571,50 C571,41.851 570.966,40.831 570.82,37.631"></path></g></g></g></svg></div><div style="padding-top: 8px;"> <div style=" color:#3897f0; font-family:Arial,sans-serif; font-size:14px; font-style:normal; font-weight:550; line-height:18px;"> View this post on Instagram</div></div><div style="padding: 12.5% 0;"></div> <div style="display: flex; flex-direction: row; margin-bottom: 14px; align-items: center;"><div> <div style="background-color: #F4F4F4; border-radius: 50%; height: 12.5px; width: 12.5px; transform: translateX(0px) translateY(7px);"></div> <div style="background-color: #F4F4F4; height: 12.5px; transform: rotate(-45deg) translateX(3px) translateY(1px); width: 12.5px; flex-grow: 0; margin-right: 14px; margin-left: 2px;"></div> <div style="background-color: #F4F4F4; border-radius: 50%; height: 12.5px; width: 12.5px; transform: translateX(9px) translateY(-18px);"></div></div><div style="margin-left: 8px;"> <div style=" background-color: #F4F4F4; border-radius: 50%; flex-grow: 0; height: 20px; width: 20px;"></div> <div style=" width: 0; height: 0; border-top: 2px solid transparent; border-left: 6px solid #f4f4f4; border-bottom: 2px solid transparent; transform: translateX(16px) translateY(-4px) rotate(30deg)"></div></div><div style="margin-left: auto;"> <div style=" width: 0px; border-top: 8px solid #F4F4F4; border-right: 8px solid transparent; transform: translateY(16px);"></div> <div style=" background-color: #F4F4F4; flex-grow: 0; height: 12px; width: 16px; transform: translateY(-4px);"></div> <div style=" width: 0; height: 0; border-top: 8px solid #F4F4F4; border-left: 8px solid transparent; transform: translateY(-4px) translateX(8px);"></div></div></div></a> <p style=" margin:8px 0 0 0; padding:0 4px;"> <a href="https://www.instagram.com/p/Bn1LNKJhRSo/?utm_source=ig_embed_loading" style=" color:#000; font-family:Arial,sans-serif; font-size:14px; font-style:normal; font-weight:normal; line-height:17px; text-decoration:none; word-wrap:break-word;" target="_blank">御恵贈御礼</a></p> <p style=" color:#c9c8cd; font-family:Arial,sans-serif; font-size:14px; line-height:17px; margin-bottom:0; margin-top:8px; overflow:hidden; padding:8px 0 7px; text-align:center; text-overflow:ellipsis; white-space:nowrap;"><a href="https://www.instagram.com/taroyabuki/?utm_source=ig_embed_loading" style=" color:#c9c8cd; font-family:Arial,sans-serif; font-size:14px; font-style:normal; font-weight:normal; line-height:17px;" target="_blank"> Taro Yabuki</a>さん(@taroyabuki)がシェアした投稿 - <time style=" font-family:Arial,sans-serif; font-size:14px; line-height:17px;" datetime="2018-09-17T14:38:17+00:00">2018年 9月月17日午前7時38分PDT</time></p></div></blockquote> <script async defer src="//www.instagram.com/embed.js"></script>