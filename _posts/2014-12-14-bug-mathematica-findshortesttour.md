---
date: 2014-12-14 14:44:42+00:00
title: MathematicaのFindShortestTourのバグ
---

先日CodeIQで，巡回セールスパースン問題を[出題](https://github.com/taroyabuki/codeiq1103)しました．

Mathematicaには，指定した点をすべて通る最短の巡回路を求める関数`FindShortestTour`があるので，これを使えば簡単なはずでしたが，実はそこにはトラップがあったかもしれません．

## 未解決

### 問題1（Wolfram|Alpahで未解決）

[WolframAlpha](https://www.wolframalpha.com/input/?i=FindShortestTour%5B%7B%7B0%2C+0%7D%2C+%7B1%2C+0%7D%2C+%7B0%2C+1%7D%2C+%7B1%2C+1%7D%2C+%7B0%2C+536870913%7D%7D%5D)（2023/5）では評価が終わりません．

**Mathematicaは13.2では解決しました．**

```
TimeConstrained[
 FindShortestTour[{ {0, 0}, {1, 0}, {0, 1}, {1, 1}, {0, 536870913} }],
 10]
```

### 問題2（未解決）

カーネルが落ちます．

```
FindShortestTour[{
  {6116822349097741, 5873203518310113},
  {2701673778654019, 1291535066125623},
  {392560345300883, 4963106019249771},
  {1529795864075473, 7759422650313613},
  {7199254742147, 8014398509483463},
  {7199254742149, 8014398509483461}}]
```

一応，`Method -> "IntegerLinearProgramming"`を付けると正解が得られるのですが，このオプションは[ドキュメント](http://reference.wolfram.com/language/ref/FindShortestTour.html)に記載されていません．

## 解決

### 問題3（解決）

**Mathematica 13.2で解決しました．**

```
pts = { {0, 0}, {1, 0}, {0, 1}, {1, 1}, {0, 30} };
tour = FindShortestTour[pts]

> {30 + 2*Sqrt[2] + Sqrt[842], {1, 2, 3, 5, 4, 1}}
```

もっと短い巡回路があります．

```
ArcLength[Line[pts[[{1, 3, 5, 4, 2, 1}]]]]

> 32 + Sqrt[842]
```

### 問題4（解決）

**Mathematica 13.2で解決しました．**

カーネルが落ちます．

```
FindShortestTour[{ {1., 0}, {0, 1}, {6421482390570520, 4284269602932036},
  {239817909316376, 7744567430237013}, {2528914430818969, 5966759469595075} }]
```

### 問題5（解決）

**Mathematica 12.0で解決しました．**

11.3の結果は最短巡回路`{1, 4, 7, 10, 9, 8, 5, 3, 2, 6, 1}`ではありませんでした．

```
cities = {
   {12581820340729273, 10017935966728831}, {12754218452664193, 14539145895971681},
   {14822745302277607, 14565274414261943}, {11873373307008371, 9781014188323403},
   {16116822349097741, 15873203518310113}, {12701673778654019, 11291535066125623},
   {9392560345300883, 14963106019249771}, {11529795864075473, 17759422650313613},
   {9007199254742147, 18014398509483463}, {9007199254742149, 18014398509483461}};
tour = FindShortestTour[cities, Method -> "IntegerLinearProgramming"];
tour[[2]]

> {1, 4, 7, 9, 10, 8, 5, 3, 2, 6, 1}
```

### 問題6（解決）

**Mathematica 10.0.2 for Windowsで解決しました．**

Mathematica 10.0.1 for Windowsでは，`{ {6, 2}, {4, 6}, {3, 4}, {6, 7} }`という4点を通る最短巡回路を求められませんでした．

<blockquote class="twitter-tweet"><p lang="en" dir="ltr"><a href="https://twitter.com/yabuki?ref_src=twsrc%5Etfw">@yabuki</a> (More info: <a href="http://t.co/HBUub0ForI">http://t.co/HBUub0ForI</a>) <a href="https://twitter.com/hashtag/wolframlang?src=hash&amp;ref_src=twsrc%5Etfw">#wolframlang</a> <a href="http://t.co/mxTwMsc0Nk">pic.twitter.com/mxTwMsc0Nk</a></p>&mdash; Tweet-a-Program (@wolframtap) <a href="https://twitter.com/wolframtap/status/535256832902975489?ref_src=twsrc%5Etfw">November 20, 2014</a></blockquote> <script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script> 

### 問題7（解決）

**10.0 for Linux ARM (32-bit) (August 4, 2014)で解決しました．**

10.0 for Linux ARM (32-bit) (January 29, 2014)の`FindShortestTour`は，仕様がドキュメントと違っていました．
マニュアルによれば，巡回路の最初と最後は同じ（この例では1）はずでした．

```
pts = { {1, 1}, {1, 2}, {1, 3}, {1, 4}, {1, 5}, {2, 1}, {2, 3}, {2, 5}, {3, 1}, {3, 2}, {3, 4}, {3, 5}, {4, 1}, {4, 3}, {4, 5}, {5, 1}, {5, 2}, {5, 3}, {5, 4} };
FindShortestTour[%]

> {14 + 5 Sqrt[2], {1, 2, 7, 3, 4, 5, 8, 12, 11, 15, 19, 14, 18, 17, 16, 13, 9, 10, 6}}
```
