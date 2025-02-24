---
date: 2025-02-24 00:00:00+09:00
title: 未だ生成AIでは解けないという2023年東大数学（理科）第6問について
image: /images/2025-02-utokyo/2.png
---

生成AIの成長はめざましく，日本の大学入試の筆記試験なら，余裕で合格するレベルに達しているようです。プロジェクト「ロボットは東大に入れるか」で，何らかのブレイクスルーがない限りは東大合格は不可能とされてからまだ10年も経っていないのに，驚くべき進歩です。しかし，未だ解けない大学入試問題もあるようです。ここでは，そのような問題の一つである，2023年東大数学（理科）第6問を，[『コンピュータでとく数学』（オーム社）](https://www.hanmoto.com/bd/isbn/9784274231797)のアプローチで解いてみます。

<script type="text/x-mathjax-config" defer>MathJax.Hub.Config({tex2jax:{inlineMath:[['\$','\$'],['\\(','\\)']],processEscapes:true},CommonHTML: {matchFontHeight:false}});</script>
<script type="text/javascript" async src="https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.1/MathJax.js?config=TeX-MML-AM_CHTML"></script>

<a href="https://www.hanmoto.com/bd/isbn/9784274231797"><img src="https://images-fe.ssl-images-amazon.com/images/P/4274231798.09.LZZZZZZZ" style="float:right; height:200px" alt="書影"></a>

Mathematica（Wolfram言語）を使います。[Wolfram Cloud](https://www.wolframcloud.com/)や[Wolfram Engine](https://www.wolfram.com/engine/index.php.ja)，[Raspberry Pi](https://www.wolfram.com/raspberry-pi/index.php.ja)でも動きます（全て無料）。

## 問題

---
$\mathrm{O}$ を原点とする座標空間において，不等式 $\|x\|\le 1$，$\|y\|\le 1$，$\|z\|\le 1$ の表す立方体を考える。その立方体の表面のうち，$z<1$ を満たす部分を $\mathrm{S}$ とする。

以下，座標空間内の $2$ 点 $\mathrm{A}$，$\mathrm{B}$ が一致するとき，線分 $\mathrm{AB}$ は点 $\mathrm{A}$ を表すものとし，その長さを $0$ と定める。

(1) 座標空間内の点 $\mathrm{P}$ が次の条件(i)，(ii)をともに満たすとき，点 $\mathrm{P}$ が動きうる範囲 $\mathrm{V}$ の体積を求めよ．
<ol style="list-style: none;">
  <li>(i) $\mathrm{OP}\le\sqrt{3}$</li>
  <li>(ii) 線分 $\mathrm{OP}$ と $\mathrm{S}$ は，共有点を持たないか，点 $\mathrm{P}$ のみを共有点に持つ。</li>
</ol>

(2) 座標空間内の点 $\mathrm{N}$ と点 $\mathrm{P}$ が次の条件(iii)，(iv)，(v)をすべて満たすとき，点 $\mathrm{P}$ が動きうる範囲 $\mathrm{W}$ の体積を求めよ。必要ならば，$\sin\alpha=\dfrac{1}{\sqrt 3}$ を満たす実数 $\alpha$ （$0<\alpha<\dfrac{\pi}{2}$）を用いてよい。
<ol style="list-style: none;">
  <li>(iii) $\mathrm{ON}+\mathrm{NP}\le\sqrt{3}$</li>
  <li>(iv) 線分 $\mathrm{ON}$ と $\mathrm{S}$ は共有点を持たない。</li>
  <li>(v) 線分 $\mathrm{NP}$ と $\mathrm{S}$ は，共有点を持たないか，点 $\mathrm{P}$ のみを共有点に持つ。</li>
</ol>
---

## (1)

### 解法1：論理式

任意（∀）や存在（∃）を含む条件から，それらを含まない同値な条件を求める手法を使います（[『コンピュータでとく数学』](https://www.hanmoto.com/bd/isbn/9784274231797)の第5章を参照）。この手法を**量化記号消去法**といいます。万能ではありませんが，とても強力です。(1)は，与えられた条件を論理式で書くだけで解けます。

まず，擬似コードで書いてみます。

```
S(x, y, z) := (x, y, z)はS上にある。
P := (x, y, z)
記述1 := OPの2乗は3以下
記述2 := (任意のaに対して，0≦a<1ならばS(aP)でない) または S(P)
条件 := 「任意の」を含まない，(記述1 かつ 記述2)と同値な条件
V := 条件で定義される領域
vol1 := Vの体積
```

これをMathematicaに翻訳します。

```
S[{x_, y_, z_}] := Or[
  And[Or[x == 1, x == -1], -1 <= y <= 1, -1 <= z < 1],
  And[-1 <= x <= 1, Or[y == 1, y == -1], -1 <= z < 1],
  And[-1 <= x <= 1, -1 <= y <= 1, z == -1]]
p = {x, y, z};
expr1 = p . p <= 3;
expr2 = Or[ForAll[a, 0 <= a < 1, Not[S[a p]]], S[p]];
cond1 = Reduce[And[expr1, expr2], Reals];
V = ImplicitRegion[cond1, {x, y, z}];
vol1 = Volume[V]
```

結果は $\dfrac{2}{3} \left(10+\sqrt{3} \pi \right)$ です（約10.294）。

### 解法2：天然知能

計算時間は解法1に比べて圧倒的に短いのですが，思い付くのは圧倒的に難しそうな解法です。

求めたいのは，上面だけ空いた一辺の長さが $2$ の立方体の箱の中心から距離 $\sqrt{3}$ 以内で，まっすぐ進んで到達できる点の集合 $\mathrm{V}$ の体積です。$\mathrm{V}$ は左の図のような立体で，それを中央と右のように分けて，体積を別々に求めます。中央の図は球（ball）から立方体（cube）をくり抜いて $6$ 分割したもの，右の図は立方体です。

![](/images/2025-02-utokyo/1.png)

まず，擬似コードで書いてみます。

```
(半径√3の球から一辺の長さ2の立方体をくり抜いたものの体積) / 6 +
(1辺の長さ2の立方体の体積)
```

これをMathematicaに翻訳します。

```
vol1 = Volume[RegionDifference[Ball[{0, 0, 0}, Sqrt[3]], Cube[2]]]/6 +
   Volume[Cube[2]] // Simplify
```

結果は $\dfrac{2}{3} \left(10+\sqrt{3} \pi \right)$ です（約10.294）。

## (2)

### 解法1：論理式（失敗）

コードは次のように簡単ですが，計算が終わりません。(iii)のような，多項式で表せない条件があると，うまく行かないことが多いです（量化記号消去法であっさり解かれてしまうことへの対策としてお勧め）。

```
n = {xn, yn, zn};
expr3 = Sqrt[n . n] + Sqrt[(p - n) . (p - n)] <= Sqrt[3];
expr4 = ForAll[a, 0 <= a <= 1, Not[S[a n]]];
expr5 = Or[ForAll[b, 0 <= b < 1, Not[S[n + b (p - n)]]], S[p]];
cond2 = Reduce[Exists[{xn, yn, zn}, And[expr3, expr4, expr5]], Reals];
W = ImplicitRegion[cond2, {x, y, z}];
vol2 = Volume[W]
(* 失敗 *)
```

### 解法2：天然知能

紙とペンで解くのとあまり変わりませんが，最後の積分をコンピュータに任せられると思うと，気が軽くなります。

求めたいのは，(1)の「まっすぐ進んで」を「点 $\mathrm{N}$ で折れ曲がる以外はまっすぐ進んで」に変えて到達できる点の集合 $\mathrm{W}$ の体積です。

![](/images/2025-02-utokyo/2.png)

左図は(1)で扱った立体 $\mathrm{V}$ です。$\mathrm{V}$ に，1回折れ曲がって到達できる点の集合を追加すると，右図のようになります。これが $\mathrm{W}$ です。

追加するのは，同じ形の立体 $4$ 個です（右図に描かれているのはそのうちの2個）。そのうちの一つ，$\mathrm{N}$ が $\mathrm{A}\,(-1, 1, 1)$ と $\mathrm{B}\,(1, 1, 1)$ を結ぶ線分上にある場合の追加領域 $\mathrm{W}_1$ の体積 $g$ を求め，それを $4$ 倍することにします。$\mathrm{W}$ の体積は $(\mathrm{V}\text{の体積}+4g)$です。

線分 $\mathrm{AB}$ 上の点 $\mathrm{H}\,(x, 1, 1)$ を通り，$x$ 軸に垂直な平面 $\alpha$ で，$\mathrm{W}_1$ を切ります。平面 $\alpha$ と $x$ 軸の交点を $\mathrm{R}$ とします。

<img src="/images/2025-02-utokyo/3.png" alt="" style="width:48%;">
<img src="/images/2025-02-utokyo/4.png" alt="" style="width:48%;">

左図は$\mathrm{OAB}$ を通る平面のようすです。この平面の，$\mathrm{W}_1$ の断面上の点で，線分 $\mathrm{AB}$ から最も遠い点を $\mathrm{Q}$ とします。$\mathrm{OQ}=\sqrt{3}$ です（これより遠くには到達できません）。

$\mathrm{P}$ が $\mathrm{Q}$ と一致するとき，$\mathrm{N}$ は $\mathrm{OQ}$ と $\mathrm{AB}$ の交点です。この $\mathrm{N}$ で折れ曲がるとき，$\alpha$ 上で $\mathrm{H}$ を中心とする，半径 $\mathrm{HQ}$ の円盤上のすべての点に到達できます。$\mathrm{OR}=x$，$\mathrm{HR}=\sqrt{2}$ なので，円盤の半径 $\mathrm{HQ}$ は，$\mathrm{QR}-\mathrm{HR}=\sqrt{\mathrm{OQ}^2-\mathrm{OR}^2}-\mathrm{HR}=\sqrt{3-x^2}-\sqrt{2}$ です。

円盤の，$z\ge y$ の部分と $y\le 1$ の部分は $\mathrm{V}$ の一部でもあるので，重複を避けるために除外します。残るのは，右図（平面 $\alpha$）の網掛けの部分なので，断面の面積は円盤全体の面積の $\dfrac{3}{8}$ 倍，つまり$\dfrac{3}{8}\pi\mathrm{HQ}^2$ です。

$\displaystyle g=\int_{-1}^1\frac{3}{8}\pi\mathrm{HQ}^2\,\mathrm{d}x$ を使って，$\mathrm{W}$ の体積を求めます。

```
hq = Sqrt[3 - x^2] - Sqrt[2];
g = Integrate[(3/8) Pi hq^2, {x, -1, 1}];
vol2 = vol1 + 4 g
```

結果は $\displaystyle\frac{2}{3}\left(10+\sqrt{3}\pi\right)+\pi\left(8-9\sqrt{2}\tan^{-1}\left(\frac{1}{\sqrt{2}}\right)\right)$ です（約10.816）。

最後の積分「`Integrate[(3/8)Pi(Sqrt[3-x^2]-Sqrt[2])^2,{x,-1,1}]`」を紙とペンで行う方法は，ChatGPTの「推論」（無料）を使うとわかります。（Raspberry Pi版を含む）Mathematicaで「`=`」を使ってWolframAlphaに問い合わせた結果の，「ステップごとの解説」でもわかります。
