---
date: 2015-05-01 16:53:14+00:00
title: HTMLとCSSだけで作るマルバツ
---

[マルバツを題材にしたプログラミング入門書](/2015/04/30/introduction-to-programs-that-change-the-worldview/)を紹介したときに，「自分ならどうするか」を考えると楽しいと書きました。

私の場合，HTML+CSS+JavaScriptで作ることを最初に考えますが，HTMLとCSSだけ，つまりJavaScriptなしというのも面白いですね。根性があれば，プログラミングは不要です。（CSSも本質的には不要ですが，見た目があまり貧弱だとイヤなので・・・）

<ul>
  <li><a href='/tictactoe/static/board/1.html'>人間 vs 人間</a></li>
  <li><a href='/tictactoe/static/play/1.html'>人間 vs COM</a></li>
  <li>COM vs 人間
    <ul>
      <li><a href='/tictactoe/static/play1/1.html'>パターン1</a></li>
      <li><a href='/tictactoe/static/play2/1.html'>パターン2</a></li>
      <li><a href='/tictactoe/static/play3/1.html'>パターン3</a></li>
    </ul>
  </li>
</ul>

COMは[負けないマルバツ](/2008/05/02/neverlose-tictactor/)＋αのつもりです。

Manifold JSで別のプラットフォーム用に変換できます。

[![表紙](https://images-fe.ssl-images-amazon.com/images/P/1594746877.09.jpg)](https://www.amazon.co.jp/dp/1594746877/)

[_Tic Tac Tome: The Autonomous Tic Tac Toe Playing Book_](https://www.amazon.co.jp/dp/1594746877/)は，この記事でやっているのとと同じことを紙で試みているすごい本です（1ページ1局面，一部両面印刷で約700枚！）。残念なことに，先手が人で後手が最善の場合は網羅されているようですが，後手が人の場合は，先手が真ん中を選ぶケースしか扱っていないようです。つまり，上述の「COMが先手」のパターン1と2は扱われていません（上の実装なら，1ページ1局面，一部両面印刷で約230枚程度で足りるはずです）。

この本がマルバツを理解しているかというと，そういうわけではないと思いますが，そういうことを考えるのも楽しいものです。

参考：[1人で「三目並べ」を遊ぶことができるゲームブック「Tic Tac Tome: The Autonomous Tic Tac Toe Playing Book」](https://dailynewsagency.com/2014/06/18/a-book-that-plays-tic-u8j/)
