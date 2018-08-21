---
date: 2017-02-15 15:38:20+00:00
title: 本を書きました。『C++の教科書』
---

C++についての教科書を書きました。

[![4822298930](https://images-fe.ssl-images-amazon.com/images/P/4822298930.09.jpg)『C++の教科書』（日経BP社）](https://www.amazon.co.jp/dp/4822298930?tag=inquisitor-22)

サンプルコードなどは以下にあります。

* [サンプルコード（UTF-8）（GitHub）](https://github.com/taroyabuki/cppbook2)
* [サンプルコード（Shift_JIS）（日経BP）](http://ec.nikkeibp.co.jp/nsp/dl/09893/index.shtml)
* [正誤表（WINGS）](http://www.wings.msn.to/index.php/-/A-05/978-4-8222-9893-7/)
* [直したいところ](https://github.com/taroyabuki/cppbook2/wiki/%E7%9B%B4%E3%81%97%E3%81%9F%E3%81%84%E3%81%A8%E3%81%93%E3%82%8D)

プログラミング言語なんて，Python一択になるんじゃないの？という向きは，[TensorFlowのコード](https://github.com/tensorflow/tensorflow)や，[世界コンピュータ将棋選手権の参加チームの使用言語](http://www2.computer-shogi.org/wcsc27/team.html)をご覧になるといいでしょう。[「どうしてJavaやC++よりも遅いPythonが機械学習で使われているの？」](https://www.quora.com/Knowing-that-Python-is-very-slow-compared-to-Java-and-C%2B%2B-why-do-they-mostly-use-Python-for-fast-algorithmic-procedures-like-machine-learning/answer/Paulina-Jonu%C5%A1ait%C4%97)などという話もあります。

[『文法からはじめるプログラミング言語 MS Visual C++入門（マイクロソフト公式解説書）』（日経BP, 2009）](https://www.amazon.co.jp/dp/4891006269?tag=inquisitor-22)の改訂版という位置付けですが，かなりの部分を書き直しました。

新しい話

* C++11, C++14に対応しました。新しい話題は，型推論・ラムダ・ムーブ・新しい標準ライブラリ（ハッシュテーブル・並列処理・乱数・時間）などです。
* Visual C++に加えて，GNU C++とClangでも，サンプルコードの動作を確認しています。
* （実用的かどうかはともかく）C++の高速性が活きる例として，組み合わせパズルを解きます。（何を勘違いしたか，初刷では幅優先探索の英語が間違ってますな！）

なくしたもの

* GUI（GUIアプリを作るならC++でなくてもいいだろうと考えてのことです。）
* C++/CLI（旧版で，マネージ拡張という失敗例を紹介したわけですが・・・）

C++入門書の執筆は，プログラミング初心者からは「難しい」，C++のプロからは「いいかげん」と言われる，負けの決まった戦いです。（いいわけ）