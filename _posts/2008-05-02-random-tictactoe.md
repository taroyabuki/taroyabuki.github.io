---
date: 2008-05-02 14:42:23+00:00
title: ランダム・マルバツ
---

マルバツについて調べる。まずは，相手がランダムに手を決めてくる場合。

[Doukaku? マルバツゲーム](https://web.archive.org/web/20080929134848/http://ja.doukaku.org:80/173/)

>「毎ターン乱数を使って手を決めるランダムプレイヤー同士を対戦させる」というのが今回のお題です。１万回対戦させ、勝ち・負け・引き分けの数を表示してください。そして先手が有利であることを確かめてください。

以下が勝ちのパターンになるようにマス目に番号を振っておく。

```
wins={ {1,2,3},{4,5,6},{7,8,9},{1,4,7},{2,5,8},{3,6,9},{1,5,9},{3,5,7} };
```

マルがある場所（あるいはバツがある場所）をリストで表現すると、次のように勝ちを判定できる。

```
isWinner[player_] := MemberQ[Length[Intersection[player, #]]& /@ wins, 3]
```    

ゲームの結果は、マルの勝ちなら1、バツの勝ちなら-1、引き分けなら0とする。

```
judge[p1_, p2_] := 
 If[isWinner@p1, 1, If[isWinner@p2, -1, If[Length@p1 == 5, 0, Null]]]
judge[state_] := judge[Sequence @@ state]
```

同じ局面を何度も評価するのが気になるなら、`judge[{p1_, p2_}] := judge[{p1, p2}] = ...`としておくといいだろう。

盤面の状態を{マルのリスト, バツのリスト}で表すことにする。状態を与えると、可能な場所のリストを返す関数は以下のとおり。

```
operators[p1_, p2_] := Complement[Range@9, p1, p2]
operators[state_] := operators[Sequence @@ state]
```    

手を決める関数を2つ（マル用とバツ用）与えると、ゲームが行われる。

```
game[decision1_, decision2_] := game[decision1, decision2, {}, {}]
game[decision1_, decision2_, p1_, p2_] :=
 With[{result = judge[p1, p2]},
  If[result =!= Null, result,
   If[Length@p1 == Length@p2,
    game[decision1, decision2, Append[p1, decision1[p1, p2]], p2],
    game[decision1, decision2, p1, Append[p2, decision2[p1, p2]]]]]]
```    

ランダム・プレーヤーが用いる関数は次のとおり。

```
randomDecision[p1_, p2_] := RandomChoice@operators[p1, p2]
```    

対戦させると、**マルの5763勝2900敗1337分**。

```
AbsoluteTiming[
 Tally[Table[game[randomDecision, randomDecision], {10000}]]]

{7.688, { {1, 5763}, {-1, 2900}, {0, 1337} }}
```    

[負けないマルバツ](/2008/05/02/neverlose-tictactor/)に続く
