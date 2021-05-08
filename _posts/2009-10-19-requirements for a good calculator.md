---
date: 2009-10-19 14:58:54+00:00
title: 電卓に求められるコト
---

<blockquote class="twitter-tweet"><p lang="ja" dir="ltr">ファインマンがギャフンとなったという「10の100乗のタンジェント」。Windows 7, 8のcalc.exeでは計算できたが，Windows 10の電卓ではできない。Androidの電卓はOK。OS XとiOSの計算機は当然ながらNG。</p>&mdash; Taro Yabuki (@yabuki) <a href="https://twitter.com/yabuki/status/721341400382504961?ref_src=twsrc%5Etfw">April 16, 2016</a></blockquote> <script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script>

使っているのは整数の加算と乗算だけで、オーバーフローしているわけでもないのに正しく計算できない例を先日紹介しました（[フェルマーの最終定理の「反例」（電卓編）](http://www.unfindable.net/~yabuki/blog/2009/10/post_356.html)）。

文明が発達しすぎた果てに電卓を作れなくなってしまうというのはSFでよくありそうな話ですが、時代の先を行っているというAppleの主張は確かに正しいのかもしれません。（参考：[ロストテクノロジー(Wikipedia)](http://ja.wikipedia.org/wiki/%E3%83%AD%E3%82%B9%E3%83%88%E3%83%86%E3%82%AF%E3%83%8E%E3%83%AD%E3%82%B8%E3%83%BC)）

小数が出てくればいろいろ問題があるのは知っていても、電卓を使った整数の計算を疑う人は少ないのではないでしょうか。しかし実際には、WindowsやMac OS Xの標準的な電卓は、つい最近まで整数の計算を正しく行えませんでした。

1992年に発売されたWindows 3.1の電卓には、下のような普通の電卓と関数電卓の2種類のモードがありました（下はWindows 3.1の電卓を、Windows 7上、「プロパティ」で「互換モードWindows 95」を指定して実行した様子です）。

![](/images/calc/calc-win31a.png)

![](/images/calc/calc-win31b.png)

この、普通の電卓と関数電卓という構成は、2007年に発売されたWindows Vistaまで、基本的には変わりません（下はWindows Vistaの電卓。Windows 3.1のものと違って、Windows 7上では実行できないというのは皮肉なことです）。

![](/images/calc/calc-winvista-a.png)

![](/images/calc/calc-winvista-b.png)

Windows 7では、電卓のUIが変更され機能も大幅に拡張されるということで、ちょっと話題になっていました。

> Windows 95以来、スタートメニューのアクセサリには、電卓が含まれていた。その機能はVistaまでほとんど変わることなく続いてきた。（[Windows 7ソフトウェアレビュー － 大きく進化した電卓編](https://web.archive.org/web/20110907042551/http://journal.mycom.co.jp:80/articles/2009/10/02/windows7soft/index.html)）

これは大間違いです。まあ、見た目がほとんど変わっていないので、そう思われるのもしかたがないのですが。私が試した限りでは、Windowsの電卓は、1995年に発売されたWindows 95と1999年に発売されたWindows 98SEの間では、その機能が大きく変わっています。計算の精度を高めることによって、[フェルマーの最終定理の「反例」（電卓編）](http://www.unfindable.net/~yabuki/blog/2009/10/post_356.html)のような問題が起こらなくなったのです（参考：[When you change the insides, nobody notices](http://blogs.msdn.com/oldnewthing/archive/2004/05/25/141253.aspx)）。

一方Macはと言えば、1996年に発売されたMac OS 7.5.5の電卓は、下のような感じでした。1992年のWindows 3.1にかなり後れを取っている感じです。

![](/images/calc/calc-macos7.5.5-2.png)

Mac OS Xの電卓は、下のような基本・科学計算・プログラマの3つのモードを備えており、Windows Vistaのそれより優れているように見えます。

![](/images/calc/calc-macos10.6-a.png)

![](/images/calc/calc-macos10.6-b.png)

![](/images/calc/calc-macos10.6-c.png)

しかし、先に述べたようなMicrosoftが遅くとも1999年には修正できていた問題が、Macで修正されたのは2007年のMac OS X 10.5でのことでした（PowerPC版Mac OS X 10.4では、基本モードと科学計算モードで結果が異なり、しかも、両方とも間違っていました）。

同じくApple社のiPhoneの電卓はもっと大変で、Ver 2.0のバグはかなり大きく取り上げられましたし（参考：[iPhone 2.0 の「計算機」アプリにバグが発見される](http://ipodtouchlab.com/2008/08/iphone-20-se.html)）、本稿で扱っているような、大きな整数の計算を正しくできないという問題は、バージョンが3.1になった現在でも残っています。端末を回転させるとモードが切り替わるのもかっこよくていいのですが、その前にやるべきことがたくさんありそうです。

見た目をよくするのは大事なことです。しかし、電卓で最も大切なことは「正しく計算できること」であるということに疑問の余地はありません。

「正しく計算できること」を完璧に実現するのはもちろん不可能です。どのあたりで妥協するかが重要なわけですが、フリーソフトウェアの[GNU bc](http://www.gnu.org/software/bc/manual/bc.html)の、「メモリが許す限りの桁数を確保する」というのは一つの目安になるでしょう。

やってはいけないのは、「整数はいわゆるintで計算し、その範囲を超えたり1未満の数が必要になったらいわゆるdoubleを使う」というような実装です。こういうことをしてしまうと、[フェルマーの最終定理の「反例」（Perl, awk, JavaScript, PHP編）](http://www.unfindable.net/~yabuki/blog/2008/09/perl_awk_javascript_php.html)も発生してしまいます。

もっとも、電卓をちゃんと作るのは以外と難しいようで、2008年にはGoogleも計算ミスをしていました（参考：[グーグルの電卓機能が計算ミス](http://japan.cnet.com/news/media/story/0,2000056023,20379457,00.htm)）。そのときには次のような解説がありましたが、その筆者が考える電卓の実装は、先に述べた「やってはいけないこと」そのままのようです。

> コンピュータでの正確な計算は、コンピュータが一般的に0または1の数字しかない2進数で計算をしていることに基づいている。一方、人は0から9までの数字を使った10進数計算を行う。正確性に問題が生じるのは、コンピュータが数字を2進数に変換して処理し、結果を10進数に戻して表示するためだ。

このようなことが、電卓において問題になるはずはありません。

プログラミングの入門書などで、「電卓を作ってみよう！」なんていうのをたまに見かけますが、入門者に電卓はあまりいい題材ではありません（RubyやPythonなら問題ありません。他の言語でも、[GMP](http://gmplib.org/)を紹介するなら大賛成です）。

電卓を作るのもなかなか大変です。

### メモ

電卓で整数の計算がある程度正確にできるようになった時期

* Unix (GNU bc): 1994年（遅くとも）
* Windows: 1999年（遅くとも）
* Mac: 2007年

bcに関しては、もう少し歴史をさかのぼるべきかもしれません。[bc (UNIX)](http://ja.wikipedia.org/wiki/Bc_%28UNIX%29)

### 追記

* [電卓アプリのフォントを変えると解答が変わる？　「Xperia」の一部機種で発生か](http://internet.watch.impress.co.jp/docs/yajiuma/1017975.html)
* [最新「Xperia」電卓アプリで「計算ミス」相次ぐ　ソニーモバイル「コメントを差し控えさせて頂きます」](http://www.j-cast.com/2016/08/31276485.html?p=all)
