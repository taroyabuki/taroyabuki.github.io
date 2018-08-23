---
date: 2008-05-02 15:01:16+00:00
title: 負けないマルバツ
---

[前エントリ](/2008/05/02/random-tictactoe/)に続いてマルバツについて調べる。今回は，負けない戦略。

追記：プログラミングは無理，という方は[HTMLとCSSだけで作るマルバツ](/2015/05/01/static-tictactoe/)をどうぞ。

[Doukaku? マルバツゲーム：賢いプレイヤー](https://web.archive.org/web/20090827031407/http://ja.doukaku.org:80/174/)

>マルバツゲームで、賢いプレイヤーの思考ルーチンを実装してください。
>賢いといってもいろいろありますが、
>1. 負けない
>2. できるだけ勝つ
>という条件でいってみたいと思います。

手を決める関数だけ新たに用意すればいい。

盤面と可能な場所のリストを与えると、新しい盤面のリストを作る補助関数を用意する。

```
nextStates[p1_, p2_, ops_] := 
 Map[If[Length@p1 == 
    Length@p2, {Append[p1, #], p2} &, {p1, Append[p2, #]} &], ops]
nextStates[state_, ops_] := nextStates[Sequence @@ state, ops]
```    

ミニマックス戦略に基づいて手を決める関数を作る。ミニマックス戦略とは、「両者が最善の結果を目指すと仮定した上で、最善の選択肢を選ぶ」というもの。未来のすべての可能性を木で表現したとき、マルの選択肢の評価値はその子ノード（バツの選択肢）の評価値の最大値（マックス）、バツの選択肢の評価値はその子ノード（マルの選択肢）の評価値の最小値（ミニマム）になる（マル勝ちを1、バツ勝ちを-1としていることに注意）。

```
Remove[minimaxDecision];
minimaxDecision[p1_, p2_] := minimaxDecision[p1, p2] =
  With[{ops = operators[p1, p2], forMax = Length@p1 == Length@p2}, 
   With[{vals = 
      minimaxValue[#, Not@forMax] & /@ nextStates[p1, p2, ops]}, 
    ops[[Position[vals, If[forMax, Max, Min]@vals][[1, 1]]]]]]

minimaxValue[state_, forMax_] := 
 Module[{result = judge@state}, 
  If[result =!= Null, result, 
   If[forMax, Max, Min][
    minimaxValue[#, Not@forMax] & /@ 
     nextStates[state, operators@state]]]]
```

この計算は10試合くらいだと遅いが、繰り返すうちに速くなる（一度計算したら結果をおぼえておくようにしている）。1万試合で1分くらい（Athlon X2 3800+）。

**マルがミニマックス戦略、バツがランダム・プレーヤーのとき、マルの9960勝0敗40分。**

```
AbsoluteTiming[
 Tally[Table[game[minimaxDecision, randomDecision], {10000}]]]

{65.656, { {1, 9960}, {0, 40} }}
```

**マルがランダム・プレーヤー、バツがミニマックス戦略のとき、バツの8020勝0敗1980分。**

```
AbsoluteTiming[
 Tally[Table[game[randomDecision, minimaxDecision], {10000}]]]

{68.484, { {-1, 8020}, {0, 1980} }}
```

**マルバツともにミニマックス戦略のときは引き分け。**

```
game[minimaxDecision,minimaxDecision]

0
``` 

計算量が許すなら、ゲーム・プレーヤーを実装しようとして最初に試すのがミニマックス。詳しく知りたい人は、人工知能のバイブル[『エージェントアプローチ人工知能』](https://www.amazon.co.jp/exec/obidos/ASIN/4320122151/inquisitor-22/)や、ハッカーのバイブル[『実用 Common Lisp』](https://www.amazon.co.jp/exec/obidos/ASIN/4798118907/inquisitor-22/)をどうぞ。

[![表紙](https://images-fe.ssl-images-amazon.com/images/P/4320122151.09.jpg)](https://www.amazon.co.jp/exec/obidos/ASIN/4320122151/inquisitor-22/)
[![表紙](https://images-fe.ssl-images-amazon.com/images/P/4798118907.09.jpg)](https://www.amazon.co.jp/exec/obidos/ASIN/4798118907/inquisitor-22/)

さて、ミニマックスは「賢い」だろうか。お題の条件1「負けない」はいいとして、条件2「できるだけ勝つ」はどう考えたらいいのだろう。

少なくともここでの実装は、「できるだけ」ということは考えていない。単に「勝ちを目指す」だけ。じゃあ、「できるだけ」というのが可能かというと、これはちょっと難しいんじゃないかと思う。素直に解釈すれば、すべての可能性な戦略が平等に起こると仮定することになるわけだが、これはかなり面倒。盤面で表現したときとプログラムで表現したときでは、戦略の密度分布も違うし。

おまけ：勝ちになるケースが最も多い手を選ぶ戦略（相手も同じ戦略と仮定）は、ランダムと戦ったときの成績が、ミニマックスより悪い**（マルで158敗、バツで752敗）**。

```
Remove[cleverDecision];
cleverDecision[p1_, p2_] := cleverDecision[p1, p2] =
  With[{ops = operators[p1, p2], forMax = Length@p1 == Length@p2}, 
   With[{vals = 
      cleverValue[#, Not@forMax] & /@ nextStates[p1, p2, ops]}, 
    ops[[Position[vals, If[forMax, Max, Min]@vals][[1, 1]]]]]]

cleverValue[state_, forMax_] := 
 Module[{result = judge@state, vals}, 
  If[result =!= Null, result, 
   vals = cleverValue[#, Not@forMax] & /@ 
     nextStates[state, operators@state];
   If[forMax, 
     N@Length@Select[vals, Positive], -N@
       Length@Select[vals, Negative]]/Length@vals]]
Tally[Table[game[cleverDecision, randomDecision], {10000}]]

{ {1, 9254}, {-1, 158}, {0, 588} }
Tally[ParallelTable[game[randomDecision, cleverDecision], {10000}]]

{ {-1, 7245}, {0, 2003}, {1, 752} }
```