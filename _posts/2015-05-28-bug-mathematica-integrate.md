---
date: 2015-05-28 13:53:23+00:00
title: MathematicaのIntegrateのバグ
---

Mathematica 10.1から11.3の積分（`Integrate`）には，**仮定の利用**に関して，9.0や10.0には無かったバグがあります。（製造元には報告済みです。）

例として，`Abs[(x + 1) (x - a) (x - 1)]`を`-1 <= x <= 1`で積分することを考えます。

`Abs`の中にパラメータが`a`がありますが，これは実数だと仮定します。`Integrate`には，そういう仮定を与えるための便利なオプション`Assumptions`があります。

```
Integrate[Abs[(x + 1) (x - a) (x - 1)], {x, -1, 1}, 
 Assumptions -> Element[a, Reals]]
```    

これで，`a <= -1`なら`-4a/3`，`a >= 1`なら`4a/3`，それ以外つまり`-1 < a < 1`なら`(-a^4 + 6a^2 + 3)/6`と，aの値で場合分けされた結果が得られます。すばらしい。

しかし，仮定がうまく取り入れられない，取り入れられないならまだしも，仮定を使ったせいで間違った答えが出てきてしまうことがあります。

```
Integrate[Abs[(x + 1) (x - 2 a + b) (x - 1)], {x, -1, 1}, 
 Assumptions -> And[-1 < a < 1, b == a]]
```    

Mathematicaの一部のバージョンでは`1/2`という結果になりますが，これは間違いです。`b == a`という仮定を考慮すると被積分関数は最初の例と同じになるので，積分結果は`(-a^4 + 6a^2 + 3)/6`にならなければなりません。

原因はよくわかりませんが，**`Assumptions`は怖くて使えません。**

この積分は，Mathematica 9.0（Windows）や10.0（Raspberry Pi）では正しく行えただけに，10.1でできなくなって残念です。機能追加と品質保持の優先順位が間違っている気がします。

>Mathematica の新バージョンには，常に多くの新しい機能が含まれている．しかし当初からの周到なデザインにより，すべてのバージョン間でほぼ完全な互換性が保たれている．その結果，例えば1988年のバージョン1用に書かれたほとんどすべてのプログラムは，Mathematica バージョン7でもそのまま変更なしで走り，かつ実行速度も大いに向上している．（[Mathematica バージョン1以降の非互換変更](http://reference.wolfram.com/language/tutorial/IncompatibleChanges.html)）

この理想を，バージョン8以降でも大切にしてほしいものです。

<blockquote class="twitter-tweet" lang="ja"><p lang="en" dir="ltr">Code submitted by <a href="https://twitter.com/yabuki">@yabuki</a> <a href="https://twitter.com/hashtag/wolframlang?src=hash">#wolframlang</a> Source: <a href="http://t.co/0Chk6I0jV3">http://t.co/0Chk6I0jV3</a>~ <a href="http://t.co/LMl3ylPLUL">pic.twitter.com/LMl3ylPLUL</a></p>&mdash; Tweet-a-Program (@wolframtap) <a href="https://twitter.com/wolframtap/status/603614261022203905">2015, 5月 27</a></blockquote>
<script async src="//platform.twitter.com/widgets.js" charset="utf-8"></script>