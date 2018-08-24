---
date: 2015-05-17 02:48:34+00:00
title: MathematicaのTeXFormのバグ
---

Mathematicaには，表現をTeX形式に変換する`TeXForm`があります。たとえば，`TeXForm[Integrate[f[x], x]]`とすると「`\int f(x) \, dx`」を返してくれます。便利です。

しかし，バグがあります。（Mathematica 10.1, 10.2, 10.3, 10.4.1, 11.2, 11.3）

`TeXForm[Abs[r + 1/2]]`の結果が「`\left\left| r+\frac{1}{2}\right\right|`」になりますが，これはTeXで処理できません。正しくは「`\left| r+\frac{1}{2}\right|`」です。`TeXForm[Abs[r]]`なら「`\left| r\right|`」という正しい結果が返ります。

開発元には報告済みですが，今のところ手で直すしかありません。計算結果から自動的に論文を書かせるようなどと思うと困ったことになるでしょう。

<blockquote class="twitter-tweet" data-conversation="none" data-lang="ja"><p lang="en" dir="ltr">(More info: <a href="https://t.co/HBUuaZOB3w">https://t.co/HBUuaZOB3w</a>) <a href="https://twitter.com/hashtag/wolframlang?src=hash&amp;ref_src=twsrc%5Etfw">#wolframlang</a> <a href="https://t.co/GO2Xw9gOzP">pic.twitter.com/GO2Xw9gOzP</a></p>&mdash; Tweet-a-Program (@wolframtap) <a href="https://twitter.com/wolframtap/status/973746694910304258?ref_src=twsrc%5Etfw">2018年3月14日</a></blockquote>
<script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script>
