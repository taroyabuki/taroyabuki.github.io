---
date: 2017-08-16 12:23:24+00:00
title: インテルによる「ポーカーの手の判別の並列化」
---

インテル デベロッパー・ゾーンで公開されている[「C++ 11 でマルチスレッド・コードを記述する」](https://www.isus.jp/products/c-compilers/writing-multi-threading-code-in-c-11/)という記事の，「ポーカーの手の判別は並列化しても速くならない」という主張について調べた結果，私の環境で**4.9秒**かかる元記事の処理が，**0.68秒**で終わるようになりました．（[コード](https://github.com/taroyabuki/PokerEval/blob/master/pokereval/pokereval.cpp)）

> 計測すべし。計測するまでは速度のための調整をしてはならない。（ロブ・パイク）

オリジナルの[pokereval/pokereval.cpp](https://github.com/David-H-Bolton/PokerEval/commit/83ab0591dfecad95a73d5f91c30cde2024f7cdaa#diff-9800a3ef08c7b25b779c719732e0b61e)を見ながら作業しましょう．

## Step 1：実験の意義

タスクの中でスリープするのはやめましょう．そういうことをすればマルチスレッド版がシングルスレッド版より速くなるのは当然ですが，意味がありません．105行目を書き換えます．

```c++
    //Sleep(1);
```    

## Step 2：データセット

2万件（正確には19981件）ではすぐに終わってしまうので，100万件のデータを使いましょう．207行目を書き換えます．

```c++
    std::ifstream filein("hands1million.txt");
```    

## Step 3：シングルスレッド版の実行時間

シングルスレッド版はこれで動くので，この実行時間を基準にチューニングします．

筆者のPC（i7-4930K，Visual Studio Community 2017，Win32 Releaseモード）では，**4.9秒**でした（シングルスレッド版・オリジナル）．

## Step 4：バグ修正

マルチスレッドのコードにバグが2個あるので修正します．（補足：この修正はpull requestしました．）

(1) 入力の終わりの判定が間違っていて，hands1million.txtの最後の空行をカウントしています．283, 284行目の，
   
```c++
    if (filein.eof()) break;
    std::getline(filein, str);
```    

を次で置き換えます．

```c++
    if (!std::getline(filein, str)) break;
```    

(2) スレッド数の倍数だけ処理したところで出力していますが，最後に余った分を出力し忘れています．299行目の`#else`の前に，次を挿入しましょう．

```c++
    if (count != MaxThreads - 1) {
      for (int i = 0; i < count; ++i) {
        fileout << futures[i].get() << '\n';
      }
    }
```    

## Step 5：マルチスレッド版の実行時間

269行目の`#define Multi 1`を有効にして，マルチスレッド版の実行時間を計ります．

筆者のPCでは**5.7秒**でした（マルチスレッド版・オリジナル）．

マルチスレッド版がシングルスレッド版（4.9秒）より遅いと悔しいのは確かです．

## Step 6：計測

Visual Studioの，デバッグ→パフォーマンス プロファイラーで，関数ごとのCPU使用率を計測します．

測定結果の上位は次のようなものでした．

![プロファイル結果](/images/2017-08-16-a-million-poker-hands-profile.png)

`std::basic_istream`に一番時間がかかっています．もう少しよく見ると，その大部分は，`std::endl`で費やされているようです．

## Step 7：パフォーマンスチューニング

[![表紙](https://images-fe.ssl-images-amazon.com/images/P/4822298930.09.jpg)](https://www.amazon.co.jp/dp/4822298930?tag=inquisitor-22)

[『C++の教科書』](https://www.amazon.co.jp/dp/4822298930?tag=inquisitor-22)の8.2.3項に，`endl`はバッファをフラッシュさせると書いてあります．100万件のデータの出力する際に，1件ごとにフラッシュするのはまずいでしょう．294行目の`std::endl`を`'\n'`に置き換えます．

```c++
    fileout << e.get() << '\n';
```    

筆者のPCでは**2.5秒**でした（マルチスレッド版・修正後）．

残念ながら，これでシングルスレッド版（4.9秒）を上回ったわけではありません．シングルスレッド版のコードでも`std::endl`を`'\n'`に置き換えて（256行目），実行時間を計ります．

筆者のPCでは**1.4秒**でした（シングルスレッド版・修正後）．

依然として，マルチスレッド版よりシングルスレッド版の方が高速です．

とはいえ，元記事の「評価の実行時間が短すぎて、タスクを非同期に呼び出すオーバーヘッドが原因である」という考察は疑問です．ここで解いているのはいわゆる _embarrassingly parallel_ というやつで，並列化の効果が，「あって当然」だからです．

## Step 8：OpenMP

OpenMPを使いましょう．『C++の教科書』の12.4節に使い方が載っています．

マクロで場合分けしてもかまいませんが，シングルスレッド版のコード（300から306行目）を書き換えます．データをメモリに読み込んで，OpenMPで並列に判別し，結果を出力します．

```c++
    std::vector<std::string> hands;
    while (std::getline(filein, str)) {
      hands.push_back(str);
    }
    rowCount = hands.size();
    
    std::vector<std::string> results(rowCount);
    #pragma omp parallel for
    for (int i = 0; i < rowCount; ++i) {
      PokerHand pokerhand(hands[i]);
      auto result = EvaluateHand(pokerhand);
      results[i] = pokerhand.GetResult(result);
    }
    
    for (int i = 0; i < rowCount; ++i) {
      fileout << results[i] << '\n';
    }
```    

筆者のPCでは**0.68秒**でした（OpenMP版）．これでシングルスレッド版・修正版（**1.4秒**）より速くなりました．シングルスレッド版・オリジナル（**4.9秒**）よりはかなり速いです．ちなみに，OpenMPを有効にしない状態でのこのコードの実行時間は1.7秒でした．

まとめ：ポーカーの手の判別は並列化で速くなります．
