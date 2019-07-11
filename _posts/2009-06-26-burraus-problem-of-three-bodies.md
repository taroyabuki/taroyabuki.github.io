---
date: 2009-06-26 14:54:21+00:00
title: ピタゴラス三体問題（Burrau’s problem of three bodies）
---

追記：[Mathematica 12で追加されたNBodySimulationを試す。](/2019/07/10/3body-simulation-with-nbodysimulation/)

「三体問題」の有名なものに、ピタゴラス三体問題があります。図のような3:4:5の直角三角形の頂点上に静止させた、質量3, 4, 5の質点の運動を調べるというものです。

![初期配置](/images/pythagoras3body.png)

> コンピュータサイエンスは重要だがコンピュータそのものが重要なわけではない、とミンスキー氏は強調した。それは解けない方程式があっても、コンピュータを使うことで何が起こるかを見る事ができるからだ。それがコンピュータがなく数学しかなかった時代との違いだとミンスキー氏はいわゆる「3体問題」など力学の問題を例に出して説明した。（[機械で「心」を作る～「AIの父」ミンスキー氏が早稲田大学で講演](https://robot.watch.impress.co.jp/docs/news/296271.html)）

![表紙](https://images-fe.ssl-images-amazon.com/images/P/4431711147.09.jpg)

この問題は1893年には知られていましたが、数値的にでさえ、解決は1966年になってからです。[ディアク，ホームズ『天体力学のパイオニアたち』](https://www.amazon.co.jp/dp/4431711147?tag=inquisitor-22)によれば、粒子の2つが連星を形成し、第3体が高速度ではじき飛ばされるという結論は、驚くべきものだったそうです。

これを数値的に解くのは、コンピュータ無しではまあ無理ですよね。「この問題をちゃんと解くのは大変ですよ」と言っていた天体力学の教授の言葉が思い出されますが、ちょうど、[Mathematicaならケプラー問題の数値解を簡単に求められる](http://blog.unfindable.net/archives/704)という記事に対して、「でも、計算精度は大丈夫でしょうか」と訊かれたところだったので、コンピュータがあれば簡単だということを確認してみましょう。

必要な精度は状況によるので、絶対大丈夫とは言えませんが、たいていの場合には、十分よい精度で計算すると思います。有名なピタゴラス三体問題で調べてみましょう。

運動エネルギーとポテンシャルエネルギーから運動方程式を導き、数値的解きます。解く時は、保存量（全エネルギーと運動量、角運動量）が保存されるようにします。（[コード](https://gist.github.com/taroyabuki/fb7bb0b56c07c2716f54)は最後に掲載）

最初の70単位時間までの軌跡です．

![三体の軌跡](/images/pythagoras3body-result2.png)

計算の正しさを証明するものではありませんが、この結果は[Burrau's problem of three bodies](https://www.ucolick.org/~laugh/oxide/projects/burrau.html)で紹介されているものとだいたいあっています。（リンク先にはこの問題を数値的に解いたSzebehelyの論文があります。）

最初の70時間単位までのアニメーションです．

![アニメーション](/images/pythagoras3body.gif)

保存量が実際に保存されているかどうかを見ておきましょう。上から，エネルギー，運動量，角運動量です．

![保存量の変化](/images/pythagoras3body-constant.png)

<script src="https://gist.github.com/taroyabuki/fb7bb0b56c07c2716f54.js"></script>
