---
date: 2014-12-14 14:44:42+00:00
title: MathematicaのFindShortestTourのバグ
---

先日CodeIQで，巡回セールスパースン問題を[出題](https://github.com/taroyabuki/codeiq1103)しました．

Mathematicaには，指定した点をすべて通る最短の巡回路を求める関数`FindShortestTour`があるので，これを使えば簡単なはずでしたが，実はそこにはトラップがあったかもしれません．

## 12.2で未解決

### 問題1

評価が終わりません．`TimeConstrained`も機能しません．

```
TimeConstrained[
 FindShortestTour[{ {0, 0}, {1, 0}, {0, 1}, {1, 1}, {0, 536870913} }],
 10]
```

[WolframAlpha](https://www.wolframalpha.com/input/?i=FindShortestTour%5B%7B%7B0%2C+0%7D%2C+%7B1%2C+0%7D%2C+%7B0%2C+1%7D%2C+%7B1%2C+1%7D%2C+%7B0%2C+536870913%7D%7D%5D)でも結果が返りません．

一応，`Method -> "IntegerLinearProgramming"`を付けると正解が得られるのですが，このオプションは[ドキュメント](http://reference.wolfram.com/language/ref/FindShortestTour.html)に記載されていません．

### 問題2

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

これも一応，`Method -> "IntegerLinearProgramming"`を付けると正解が得られます．

```
FindShortestTour[pts, Method -> "IntegerLinearProgramming"]

> {32 + Sqrt[842], {1, 3, 5, 4, 2, 1}}
```

### 問題3

カーネルが落ちます．

```
FindShortestTour[{ {1., 0}, {0, 1}, {6421482390570520, 4284269602932036},
  {239817909316376, 7744567430237013}, {2528914430818969, 5966759469595075} }]
```

```
cities = {
   {12581820340729273, 10017935966728831}, {12754218452664193, 14539145895971681},
   {14822745302277607, 14565274414261943}, {11873373307008371, 9781014188323403},
   {16116822349097741, 15873203518310113}, {12701673778654019, 11291535066125623},
   {9392560345300883, 14963106019249771}, {11529795864075473, 17759422650313613},
   {9007199254742147, 18014398509483463}, {9007199254742149, 18014398509483461}};
tour = FindShortestTour[cities]
```

これらも一応，`Method -> "IntegerLinearProgramming"`を付けると正解が得られます．

## 解決済み

### 12.0で解決

11.3までは，最短巡回路ではありませんでした．
12.0以降では正解`{1, 4, 7, 10, 9, 8, 5, 3, 2, 6, 1}`が得られます．

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

### 10.0.2 for Windowsで解決

Mathematica 10.0.1 for Windowsでは，`{ {6, 2}, {4, 6}, {3, 4}, {6, 7} }`という4点を通る最短巡回路を求められませんでした．

<blockquote>[@yabuki](https://twitter.com/yabuki) (More info: [http://t.co/HBUub0ForI](http://t.co/HBUub0ForI)) [#wolframlang](https://twitter.com/hashtag/wolframlang?src=hash) [pic.twitter.com/mxTwMsc0Nk](http://t.co/mxTwMsc0Nk)
> 
> -- Tweet-a-Program (@wolframtap) [2014, 11月 20](https://twitter.com/wolframtap/status/535256832902975489)</blockquote>

### 10.0 for Linux ARM (32-bit) (August 4, 2014)で解決

10.0 for Linux ARM (32-bit) (January 29, 2014)の`FindShortestTour`は，仕様がドキュメントと違っていました．
マニュアルによれば，巡回路の最初と最後は同じ（この例では1）はずでした．

```
pts = { {1, 1}, {1, 2}, {1, 3}, {1, 4}, {1, 5}, {2, 1}, {2, 3}, {2, 5}, {3, 1}, {3, 2}, {3, 4}, {3, 5}, {4, 1}, {4, 3}, {4, 5}, {5, 1}, {5, 2}, {5, 3}, {5, 4} };
FindShortestTour[%]

> {14 + 5 Sqrt[2], {1, 2, 7, 3, 4, 5, 8, 12, 11, 15, 19, 14, 18, 17, 16, 13, 9, 10, 6}}
```
