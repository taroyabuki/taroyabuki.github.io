---
date: 2016-01-23 14:52:26+00:00
title: 三角関数は不要
---

インテルとAMDのCPUでの話です。

インテルとAMDのCPUには，三角関数を数値的に計算するための命令が備えられていますが，その結果は正しくありません。たとえば，CPUの命令FCOSの結果とstd::cosでソフトウェア的に計算した結果を比べると，後者の方が真の値に近くなります。

再現のためのコード（FCOSを使う部分だけインラインアセンブラを使っています。）

<script src="https://gist.github.com/taroyabuki/a1435c9be2416223c7c5.js"></script>

Core i5 6600とg++ 4.8.4で試した結果はこんな感じです。

```
x = 0.98233490762409514385
c1 = fcos(x)     = 0.55508189550073283591
c2 = std::cos(x) = 0.55508189550073294694
c  = mp::cos(x)  = 0.555081895500732891425063460345544265145531916268
abs(c - c1) = 5.5512e-17
abs(c - c2) = 5.551e-17

c1 is better: 0
c2 is better: 25
total: 100000
```    

（その正しさをどうやって確かめるのかという問題はありますが）Boost.Multiprecisionで計算した結果と比較すると，命令`FCOS`より`std::cos`の方が正確なようです（Visual Studioの場合はまた違った結果になるようです）。

Pentiumが割り算を間違うといって大騒ぎになったことがありましたが（[Pentium FDIV バグ](https://ja.wikipedia.org/wiki/Pentium_FDIV_%E3%83%90%E3%82%B0)），数値計算はどうせ近似値だからでしょうか，この問題は，ドキュメントに注意書きが追記されただけで，根本的な解決はされないままのようです。

参考：[サイン、コサインをインテルの CPU で計算すると少しバグっているらしい](http://tomeapp.jp/archives/1282)

<blockquote class="twitter-tweet" data-lang="ja"><p lang="ja" dir="ltr">三角関数には、三角関数に実用性を見出せない人を見出せるという実用性があります。重力場の方程式ではおそらくこうはいきません。</p>&mdash; Taro YABUKI (@yabuki) <a href="https://twitter.com/yabuki/status/637288726167617536">2015, 8月 28</a></blockquote>
<script async src="//platform.twitter.com/widgets.js" charset="utf-8"></script>

<!---
おまけ：
<ul>
<li><a href="https://gist.github.com/taroyabuki/0058611b305bbd6c28e3">gccのlong double, __float128の結果も見てみる</a></li>
<li><a href="https://gist.github.com/taroyabuki/5463e68911615d34f4b3">CUDAのcosとstd::cosの比較</a></li>
<li><a href="https://gist.github.com/taroyabuki/851e417dd187023f8468">CUDAのcosとFCOSの比較</a></li>
</ul>

<strong>結論：CUDAのcos，FCOS，gccのstd::cos(double)の順に正確になります。もちろん，gccのcos(long double), gccのcos(__float128), boost/multiprecisionは，さらに正確です。</strong>

<a href="https://gist.github.com/taroyabuki/200a620ceb44622bb081">JavaではMath.cosとStrictMath.cosがどっちもどっちだったり</a>，数値計算は難しいです。
--->