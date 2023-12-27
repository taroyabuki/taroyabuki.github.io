---
date: 2017-02-15 15:38:20+00:00
title: 新刊のご案内：『C++の教科書』
image: https://cover.openbd.jp/9784822298937.jpg
twitter:
  card: summary
---

<img src="https://images-fe.ssl-images-amazon.com/images/P/4822298930.09.jpg
" alt="書影" style="height:150px;" /><br/>矢吹太朗『C++の教科書』（日経BP社, 2017）

[サポートサイト](https://github.com/taroyabuki/cppbook2)

- [honto](https://honto.jp/isbn/978-4822298937)
- [アマゾン](https://www.amazon.co.jp/dp/4822298930)

サンプルコードなどは以下にあります。

* [サンプルコード（UTF-8）](https://github.com/taroyabuki/cppbook2/tree/master/samples)
* [サンプルコード（Shift_JIS）](https://project.nikkeibp.co.jp/bnt/atcl/17/P98930/112800001/)
* [正誤表](https://project.nikkeibp.co.jp/bnt/atcl/17/P98930/061200002/)
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
