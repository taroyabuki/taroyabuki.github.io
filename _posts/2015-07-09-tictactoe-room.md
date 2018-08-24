---
date: 2015-07-09 16:00:45+00:00
title: マルバツの部屋
---

人工知能の話題に「**中国語の部屋**」というのがあって・・・（以下略） マルバツに負けないための手順がすべて書かれた**紙**を持った人が部屋にいて・・・（以下略） そういう**紙**は実際に用意できます。

**先手版用**（赤が先手・青が後手）

[![先手版用](/tictactoe/graph/firstmove.png)](/tictactoe/graph/firstmove.pdf)

**後手版用**（赤が先手・青が後手）

[![後手版用](/tictactoe/graph/secondmove.png)](/tictactoe/graph/firstmove.pdf)

同じ盤面に対応するノードが複数あるのは，手順をわかりやすくするためです。[HTMLとCSSだけで作るマルバツ](/2015/05/01/static-tictactoe/)のページ数は，上の絵のノード数より少なくなっています。

この絵の読み方を知ってさえいれば，マルバツの素人でもゲームに負けることはありません。

この絵を描ける私は，マルバツを理解しているつもりだったのですが，マーティン・ガードナー数学ゲーム全集の第1巻，[マーティン・ガードナー『ガードナーの数学パズル・ゲーム』（日本評論社, 2015）](https://www.amazon.co.jp/dp/4535604215?tag=inquisitor-22)を読んで，甘かったことを思い知らされました。楽しみな全集が出たものです。

[![表紙](https://images-fe.ssl-images-amazon.com/images/P/4535604215.09.jpg)](https://www.amazon.co.jp/dp/4535604215?tag=inquisitor-22)

「プレーヤーが最善を尽くせば引き分けになる」というのがマルバツの一応の結論ですが，それを知っていることは，マルバツのすべてを知っていることを意味しません。

たとえば，両者が最善を尽くせば引き分けという局面でも，相手が素人なら，勝率が高くなる手があります。

これは，人間がコンピュータ向けの手を打てば勝てるという囲碁の現状に似ています（[電聖戦第2回大会](https://ja.wikipedia.org/wiki/%E9%9B%BB%E8%81%96%E6%88%A6#%E7%AC%AC2%E5%9B%9E%E5%A4%A7%E4%BC%9A)）。将棋はあと10年もすれば，ソフトウェアが準最善手と人間に合わせた手を使い分けられるようになるかもしれません。

というわけで，少なくとも上述の**紙**を持った人がいるだけの**マルバツの部屋**は，マルバツのことはわかっていません。