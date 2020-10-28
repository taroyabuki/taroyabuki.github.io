---
date: 2019-07-11 00:00:00+09:00
title: 三体問題を解く難しさ
image: https://taroyabuki.github.io/images/pythagoras3body.png
twitter:
  card: summary
---

三体問題の例として，ピラゴラス三体問題を試します。三体問題を解くのは難しいということを知っていると，小説[『三体』](https://www.amazon.co.jp/dp/4152098708?tag=inquisitor-22)をもっと楽しめるかもしれません。

[![三体](https://cover.openbd.jp/9784152098702.jpg)](https://www.amazon.co.jp/dp/4152098708?tag=inquisitor-22)

ピラゴラス三体問題は，図のように，質量3, 4, 5の質点が，辺の長さが3, 4, 5の三角形の頂点で静止している状態の，その後の変化を調べる問題です。

![初期配置](/images/pythagoras3body.png)

こういう状況をシミュレートするMathematicaの関数[NBodySimulation](https://reference.wolfram.com/language/ref/NBodySimulation.html)を使います。

```
tmax = 70;
data = NBodySimulation["InverseSquare", {
    <|"Mass" -> 3, "Position" -> { 1,  3}, "Velocity" -> {0, 0}|>,
    <|"Mass" -> 4, "Position" -> {-2, -1}, "Velocity" -> {0, 0}|>,
    <|"Mass" -> 5, "Position" -> { 1, -1}, "Velocity" -> {0, 0}|>},
   tmax];

ParametricPlot[Evaluate[data[All, "Position", t]], {t, 0, tmax}]
```

![三体の軌跡](/images/pythagoras3body-result.png)

シミュレートできているように見えますが，（保存されるはずの）全エネルギーの時間変化を見ると，うまく行ってないことがわかります。

```
Plot[Evaluate[data["TotalEnergy", t]], {t, 0, tmax}]
```

![全エネルギーの時間変化](/images/pythagoras3body-energy.png)

これは，`data["HamiltonEquations"]`として得られるの運動方程式をそのままNDSolveで解いた結果のようです。この問題では少なくとも，全エネルギー，運動量，角運動量が保存されなければならないのですが，NBodySimulationにそういう制約を入れる方法がよくわかりません。

三体問題を解くのは難しいです。

参照：[NDSolveで保存量を指定する方法](/2009/06/26/burraus-problem-of-three-bodies/)

![アニメーション](/images/pythagoras3body.gif)