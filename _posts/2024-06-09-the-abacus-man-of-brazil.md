---
date: 2024-06-09 00:00:00+09:00
title: ファインマンと対決した，ブラジルのそろばん男
---

[『ご冗談でしょう，ファインマンさん』](https://www.hanmoto.com/bd/isbn/9784006030063)に，ブラジルでそろばんを売り歩く日本人の男がファインマンにやっつけられる話が出てくる．
異国でそろばんを売り，ファインマンと算術で対決し，ファインマンの自伝に載るのだから，凡庸な人生ではない．

<a href="https://www.hanmoto.com/bd/isbn/9784006030063"><img src="https://images-fe.ssl-images-amazon.com/images/P/4006030061.09.LZZZZZZZ" style="float:right; height:200px" alt="書影"></a>

ただ，自伝での描かれ方は少し不名誉なものである．
ファインマン曰く

> 彼は数というものの内容を理解はしていないのである．

「理解」とは何かはここでは問わない．

<a href="https://www.hanmoto.com/bd/isbn/9784274231797"><img src="https://images-fe.ssl-images-amazon.com/images/P/4274231798.09.LZZZZZZZ" style="float:right; height:200px" alt="書影"></a>

そろばん男の名誉挽回のための道具として，**[黄緑本『コンピュータでとく数学』](https://www.hanmoto.com/bd/isbn/9784274231797)**と**計算尺**を贈れたらと思う．

問題は「$1729.03$の$3$乗根」だった．

ファインマンは$1$立方フィートが$1728$立方インチであることをたまたま知っていた．
$1$フィートは$12$インチだから，$12^3=1728$である．
よって
\begin{equation}
1729.03^{1/3}
=(1728+1.03)^{1/3}
=\left(1728\left(1+\frac{1.03}{1728}\right)\right)^{1/3}
=\left(12^3(1+a)\right)^{1/3}
=12(1+a)^{1/3}
=12f(a)
\end{equation}
と変形できる．
ここで，$f(x):=(1+x)^{1/3}, a:=\dfrac{1.03}{1728}$である．

$a$が小さければ
\begin{equation}
f(a)=f(0+a)\simeq f(0)+f'(0)a=1+\frac{1}{3}(1+0)^{-2/3}a=1+\frac{1}{3}a
\end{equation}
と近似できそうだ．
近似できるなら，答えは次のとおり．
\begin{equation}
12f(a)\simeq 12\left(1+\dfrac{a}{3}\right)=12+4a\simeq 12.002384.
\end{equation}

『ご冗談でしょう，ファインマンさん』での説明はここまでだが，ファインマン自身が

> しばらくしてやっと頭をあげて「$12.0$」と言ったころには，僕の方はまた小数点以下五つ数字を並べていた．

と書いているのだから，この近似が小数第$5$位まで正確なことは確認していたのだろう．
確認方法を想像するに

$f(x)$のマクローリン級数を求めて，次を得る（[WolframAlpha](https://www.wolframalpha.com/input?i=series+%281%2Bx%29%5E%281%2F3%29+to+order+2&lang=ja)）．
\begin{equation}
f(x)=1+\dfrac{x}{3}-\dfrac{x^2}{9}+\cdots.
\end{equation}

**黄緑本**なら，この級数は$-1\le x\le 1$のときに$f(x)$に収束することが簡単にわかるし，10進小数表示はコンピュータでいくらでも計算できる．

$12.0023837856917181230573816699504404075068512205089275360288130733950242127679446563430201096808203230\dots$

算術対決ではそうはいかない．
高次の項の計算は，できれば避けて通りたい．

先の級数は交代級数だから，次が成り立つ（[『解析概論』の§45](https://linesegment.web.fc2.com/books/mathematics/zouteikaisekigairon/zouteikaisekigairon_045.html)．[WolframAlpha](https://www.wolframalpha.com/input?i=%7C%281%2Bx%2F3%29-%281%2Bx%29%5E%281%2F3%29%7C%3Cx%5E2%2F9&lang=ja)）．
\begin{equation}
\left|\left(1+\dfrac{x}{3}\right)-f(x)\right|<\dfrac{x^2}{9}.
\end{equation}

$12f(a)\simeq 12\left(1+\dfrac{a}{3}\right)$という近似の誤差は
\begin{equation}
\left|12\left(1+\frac{a}{3}\right)-12f(a)\right|=12\left|\left(1+\frac{a}{3}\right)-f(a)\right|<12\times\frac{a^2}{9}<\frac{4}{3}\times 10^{-6}<0.0000014
\end{equation}
となる．
\begin{equation}
12\left(1+\frac{a}{3}\right)-0.0000014<12f(a)<12\left(1+\frac{a}{3}\right)+0.0000014
\end{equation}
つまり
\begin{equation}
12.002384\dots-0.0000014<12f(a)<12.002384\dots+0.0000014.
\end{equation}

以上で，$12f(a)\simeq 12.002384$は小数第$5$位までは正しいことが確かめられた．

この方法でうまく行った理由：

1. $1728^{1/3}=12$を知っていた．
1. $a$が比較的小さかった．

1は**計算尺**があれば簡単にわかる．
そろばんのほかに，計算尺と大学教養レベルの数学の知識（**黄緑本！**）を持っていたら，あるいは，題材が$1729.03$でなかったら，そろばん男はファインマンに勝てたかもしれない．
しかしその場合，生きた証をかの自伝に残すことはなかっただろう．

<blockquote class="twitter-tweet"><p lang="ja" dir="ltr">計算のほぼ全てをコンピュータで行うという『コンピュータでとく数学』（オーム社）の読者で，電気が使えないときが心配なかた向けの装備<br><br>この装備で読破しようとすると，長大な時間がかかるでしょう．<br><br>コンサイスの円形計算尺は，新品が手に入ります（写真は270N）． <a href="https://t.co/RvsPqgt1t4">pic.twitter.com/RvsPqgt1t4</a></p>&mdash; Taro Yabuki (@yabuki) <a href="https://twitter.com/yabuki/status/1797221119092572588?ref_src=twsrc%5Etfw">June 2, 2024</a></blockquote> <script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script>

<script type="text/x-mathjax-config">MathJax.Hub.Config({tex2jax:{inlineMath:[['\$','\$'],['\\(','\\)']],processEscapes:true},CommonHTML: {matchFontHeight:false}});</script>
<script type="text/javascript" async src="https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.1/MathJax.js?config=TeX-MML-AM_CHTML"></script>
