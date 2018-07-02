# なぜ Lisp なのか

## Quote...Unquote

<ul>
  <li><a href="https://www.amazon.co.jp/exec/obidos/ASIN/4826901267/inquisitor-22/">ダグラス・R. ホフスタッター著, 竹内郁雄ほか訳『メタマジック・ゲーム』</a>
    <blockquote><p>ミンスキー「ゲーデルは Lisp を思いついておくべきだった。もし彼が Lisp を思いついていたならば彼の不完全性定理の証明はもっと簡単なものになっていただろう」</p></blockquote>
    <blockquote><p>ゲーデルの証明の一番難しいところは、数学的体系に自分自身を語らせるところにある。天才のひらめきが何段階か必要になる。しかし、Lisp は、少なくともゲーデルが必要としていた意味で、まさに自分自身を直接語ることができる。</p></blockquote>
    <blockquote><p>ゲーデルは Lisp を発明した！</p></blockquote>
  </li>
  <li><a href="http://www.unixuser.org/~euske/doc/dijkstra-ja/thehumbleprogrammer.html">ダイクストラ「謙虚なるプログラマ」</a>
    <blockquote>LISPは冗談まじりに「コンピュータを誤用するための、もっとも知的な方法」と言われることがありますが、私はこれはすばらしい褒め言葉だと思います。なぜならこれはあらゆる種類の自由を可能にしているからです。LISPは私たち人類のうちもっとも才能ある人々が、それまでは不可能だったような方法で思考することを支援してきました。</blockquote>
  </li>
  <li><a href="http://jp.franz.com/base/seminar/2004-06-10/SeminarJune2004-WadaSensei.pdf">和田英一「Lisp へのこだわり」</a>
    <blockquote><p>Lisp に夢中なのは一部の変人だけなのかもしれない。</p></blockquote>
  </li>
  <li><a href="http://cruel.org/freeware/hacker.html">Eric S. Raymond 著, 山形浩生訳「ハッカーになろう」</a><blockquote><p>LISP は、それをモノにしたときのすばらしい悟り体験のために勉強しましょう。この体験は、その後の人生でよりよいプログラマーとなる手助けとなるはずです。たとえ、実際には LISP そのものをあまり使わなくても。</p></blockquote></li>
  <li><a href='http://www.itmedia.co.jp/enterprise/articles/0801/29/news010.html'>まつもとゆきひろ「Let's Talk Lisp」</a><blockquote><p>Lisp はいつも時代の最先端に位置している</p></blockquote></li>
  <li>Paul Graham
    <ul>
    <li><a href="http://www.shiro.dreamhost.com/scheme/trans/iflisp-j.html">Lisp がそんなにすごいなら</a><blockquote><p>Lisp がそんなにすごいなら、どうしてもっとたくさんの人が使わないんだろう。最近の講演で、こんな質問を聴衆の学生から受けた。もちろんそれが初めてじゃない。</p></blockquote></li>
    <li><a href="http://www.shiro.dreamhost.com/scheme/trans/beating-the-averages-j.html">普通のやつらの上を行け</a>（<a href='https://www.amazon.co.jp/exec/obidos/ASIN/4274065979/inquisitor-22/'>『ハッカーと画家』</a>に収録）<blockquote><p>Lisp のプログラムコードは Lisp のデータオブジェクトから出来ている。それは、ソースコードは文字列で出来ていて、文字列は言語でサポートされている、というようなつまらない意味じゃない。</p></blockquote></li>
  </ul></li>
  <li><a href="http://www.shiro.dreamhost.com/scheme/wiliki/wiliki.cgi/Lisp:GreenspunsTenthRule">Greenspunの第10規則</a><blockquote><p>全ての十分に複雑な C もしくは Fortran プログラムは、後付けの、不完全な仕様とバグを持ち、遅い、Common Lisp の半分の実装を含んでいる。</p></blockquote></li>
   <li><a href="http://www.franz.com/success/customer_apps/artificial_intelligence/kurzweil.lhtml">Exploring the Code of Creativity</a>（絵を描くプログラム <a href='http://www.kurzweilcyberart.com/'>AARON</a> については、<a href='https://www.amazon.co.jp/exec/obidos/ASIN/431400830X/inquisitor-22'>『コンピュータ画家アーロンの誕生』</a>を参照）<blockquote><p>（絵を描こうとする場合に用いる膨大なルールは）C のような言語では表現不可能だった。</p></blockquote></li>
</ul>





## 処理系

### Common Lisp

<ul>
 <li><a href="http://www.cons.org/cmucl/">CMUCL</a></li>
 <li><a href="http://www.gnu.org/software/gcl/">GCL (GNU Common Lisp)</a></li>
 <li><a href="http://www.jsdlab.co.jp/~kamei/">xyzzy</a></li>
 <li><a href="http://jp.franz.com/">Allegro Common Lisp</a></li>
</ul>

### Scheme

<ul>
 <li><a href='http://practical-scheme.net/gauche/index-j.html'>Gauche</a></li>
 <li><a href='http://www.gnu.org/software/guile/guile.html'>Guile</a></li>
 <li><a href='http://groups.csail.mit.edu/mac/projects/scheme/'>MIT/GNU Scheme</a></li>
</ul>

### Emacs Lisp

<ul>
 <li><a href='http://www.gnu.org/software/emacs/'>GNU Emacs</a></li>
 <li><a href="http://www.meadowy.org/meadow/wiki/">Meadow</a></li>
</ul>





## Reference

<ul>
 <li><a href="http://www.lispworks.com/documentation/common-lisp.html">Common Lisp HyperSpec</a></li>
 <li><a href="http://www-2.cs.cmu.edu/afs/cs.cmu.edu/project/ai-repository/ai/html/cltl/cltl2.html">Guy L. Steele Jr. <em>Common Lisp the Language, 2nd Edition</em></a>（<a href='https://www.amazon.co.jp/exec/obidos/ASIN/4320025881/inquisitor-22/'>日本語訳</a>）</li>
 <li><a href="http://www.franz.com/support/documentation/">Franz Inc Product Documentation</a></li>
 <li><a href="http://flex.ee.uec.ac.jp/texi/emacs-jp/emacs-jp_toc.html">GNU Emacs Manual</a>（<a href="https://www.amazon.co.jp/exec/obidos/ASIN/4756134130/inquisitor-22/">書籍版</a>。Info でも読める）</li>
 <li><a href="http://flex.ee.uec.ac.jp/texi/eljman/eljman_toc.html">GNU Emacs Lisp Reference Manual</a>（<a href="https://www.amazon.co.jp/exec/obidos/ASIN/4756134149/inquisitor-22/">書籍版</a>。Info でも読める）</li>
 <li><a href="http://emacs-20.ki.nu/refcard.shtml">Emacs-20 Reference Card</a></li>
 <li><a href="http://www.math.u-toyama.ac.jp/~iwao/Scheme/r5rsj/html/r5rsj_toc.html">アルゴリズム言語Schemeに関する第五改訂報告書</a></li>
</ul>





## SICP

<p><a href="https://www.amazon.co.jp/exec/obidos/ASIN/489471163X/inquisitor-22"></a><a href="http://www.norvig.com/Lisp-retro.html">Peter Norvigによれば</a>、<q>コンピューター・サイエンスの最高の入門書</q>。</p>

<ul>
  <li><a href="https://www.amazon.co.jp/exec/obidos/ASIN/0262510871/inquisitor-22">Harold Abelson and Gerald Jay Sussman with Julie Sussman. <em>Structure and Interpretation of Computer Programs second edition</em></a></li>
  <li><a href="http://mitpress.mit.edu/sicp/full-text/book/book.html">オンライン版</a></li>
  <li><a href="http://ocw.mit.edu/OcwWeb/Electrical-Engineering-and-Computer-Science/6-001Spring-2005/CourseHome/">6.001 Structure and Interpretation of Computer Programs</a></li>
  <li><a href="https://www.amazon.co.jp/exec/obidos/ASIN/489471163X/inquisitor-22">日本語訳</a>（<a href="http://www.ipl.t.u-tokyo.ac.jp/sicp/">サポートサイト</a>）</li>
</ul>





## 重要人物

### <a href="https://www.amazon.co.jp/exec/obidos/ASIN/0262130114/inquisitor-22/"></a><a href="http://www-formal.stanford.edu/jmc/frames.html">John McCarthy</a>

<ul>
  <li><a href="http://www-formal.stanford.edu/jmc/recursive.html">Recursive Functions of Symbolic Expressions and their Computation by Machine (Part I). 1960.</a> 原典！</li>
  <li><a href="http://www-formal.stanford.edu/jmc/basis1/basis1.html">A Basis for a Mathematical Theory of Computation. 1961.</a></li>
  <li><a href="https://www.amazon.co.jp/exec/obidos/ASIN/0262130114/inquisitor-22/"><em>LISP 1.5 Programmer's Manual</em>. 1962.</a>（<a href='http://www.google.com/search?q=LISP+1.5+Programmer%27s+Manual+filetype%3Apdf'>Google</a>）</li>
</ul>





### Peter Norvig

<ul>
  <li><a href="https://www.amazon.co.jp/exec/obidos/ASIN/4798118907/inquisitor-22/"></a><a href="https://www.amazon.co.jp/exec/obidos/ASIN/1558601910/inquisitor-22"><em>Paradigms of Artificial Intelligence Programming: Case Studies in Common Lisp</em></a>（<a href="https://github.com/norvig/paip-lisp">GitHub</a>，<a href="https://www.amazon.co.jp/exec/obidos/ASIN/4798118907/inquisitor-22/">日本語訳</a>）</li>
  <li><a href="https://www.amazon.co.jp/exec/obidos/ASIN/0136042597/inquisitor-22/"><em>Artificial Intelligence: A Modern Approach</em></a>（<a href="http://aima.cs.berkeley.edu/">サポートサイト</a>。<a href="https://www.amazon.co.jp/exec/obidos/ASIN/0132071487/inquisitor-22/">ペーパーバック</a>，<a href="https://www.amazon.co.jp/exec/obidos/ASIN/4320122151/inquisitor-22/">日本語訳（第2版）</a>）</li>
  <li><a href="http://www.norvig.com/java-lisp.html">Lisp as an Alternative to Java</a></li>
  <li><a href="http://www.norvig.com/luv-slides.ps">Tutorial on Good Lisp Programming Style</a></li>
  <li><a href="http://www.norvig.com/design-patterns">Design Patterns in Dynamic Languages</a></li>
  <li><a href='http://www010.upp.so-net.ne.jp/okshirai/norvigfinal-ja.txt'>大規模なウェブサービス、そしてそれらを構築するプログラミング言語</a><blockquote><p>なぜLispは誰もが大好きな言語ではないのか? なぜ誰もが全てに対してLispを使うわけではないのか? なぜLispはGoogleでは使われていないのか?</p></blockquote></li>
</ul>





### <a href="http://www.cs.auckland.ac.nz/CDMTCS/chaitin/index.html">Gregory. J. Chaitin</a>

<ul>
  <li><a href="http://www.cs.auckland.ac.nz/CDMTCS/chaitin/unknowable/index.html"><em>The Unknowable</em></a> （Lisp によるゲーデル・チューリング・チャイティンの不完全性定理の記述。<a href="https://www.amazon.co.jp/exec/obidos/ASIN/443401238X/inquisitor-22/">日本語訳</a>）</li>
  <li><a href="http://www.cs.auckland.ac.nz/CDMTCS/chaitin/lm.html"><em>The Limits of Mathematics</em></a> （<a href="https://www.amazon.co.jp/exec/obidos/ASIN/4434011189/inquisitor-22/">日本語訳</a>）</li>
</ul>





### <a href="http://www.paulgraham.com/paulgraham/index.html">Paul Graham</a>
<p><a href="http://www.norvig.com/Lisp-retro.html">Peter Norvig によれば</a>、Lisp の本の中では、<q>Paul Graham の <a href="http://www.paulgraham.com/paulgraham/onlisp.html"><em>On Lisp</em></a>（<a href="https://www.amazon.co.jp/exec/obidos/ASIN/4274066371/inquisitor-22/">日本語訳</a>，<a href="http://www.komaba.utmc.or.jp/~flatline/">日本語の草稿</a>）と <a href="http://www.paulgraham.com/paulgraham/acl.html"><em>ANSI Common Lisp</em></a>（<a href="https://www.amazon.co.jp/exec/obidos/ASIN/4894714337/inquisitor-22/">日本語訳</a>）がベスト</q>。</p>

#### <a href="http://www.paulgraham.com/paulgraham/hackpaint.html"><em>Hackers &amp; Painters</em></a>（<a href="https://www.amazon.co.jp/exec/obidos/ASIN/4274065979/inquisitor-22">日本語訳</a>）

<p><a href="http://www.shiro.dreamhost.com/scheme/index-j.html">訳者のウェブサイト</a>で読めるものもある（本に収録されなかったエッセイも読める）。</p>

<p><a href="http://www.shiro.dreamhost.com/scheme/wiliki/wiliki.cgi/Shiro:HackersAndPainters">サポートページ</a></p>

<ol>
  <li value='0'>メイド・イン・USA<a href="http://www.paulgraham.com/paulgraham/usa.html">（原文）</a></li>
  <li>どうしてオタクはもてないか（別訳：<a href="http://www.blog.net/nerds-jp.htm">オタクが人気者になれない理由</a>）</li>
  <li><a href="http://www.shiro.dreamhost.com/scheme/trans/hp-j.html">ハッカーと画家</a></li>
  <li><a href="http://www.shiro.dreamhost.com/scheme/trans/say-j.html">口にできないこと</a></li>
  <li>天邪鬼の価値<a href="http://www.paulgraham.com/paulgraham/gba.html">（原文）</a></li>
  <li><a href="http://www.shiro.dreamhost.com/scheme/trans/road-j.html">もうひとつの未来への道</a></li>
  <li>富の創りかた</li>
  <li>格差を考える</li>
  <li><a href="http://www.shiro.dreamhost.com/scheme/trans/spam-j.html">スパムへの対策</a>
  <ul>
    <li>続編：<a href="http://www.shiro.dreamhost.com/scheme/trans/better-j.html">「ベイジアンフィルタの改善」</a></li>
    <li>続続：<a href="http://www.shiro.dreamhost.com/scheme/trans/ffb-j.html">「反撃するフィルタ」</a></li>
  </ul></li>
  <li><a href="http://www.shiro.dreamhost.com/scheme/trans/taste-j.html">ものつくりのセンス</a></li>
  <li>プログラミング言語入門</li>
  <li><a href="http://www.shiro.dreamhost.com/scheme/trans/hundred-j.html">百年の言語</a></li>
  <li><a href="http://www.shiro.dreamhost.com/scheme/trans/beating-the-averages-j.html">普通のやつらの上を行け</a></li>
  <li><a href="http://www.shiro.dreamhost.com/scheme/trans/icad-j.html">オタク野郎の復讐</a>
  <ul>
    <li><a href="http://www.shiro.dreamhost.com/scheme/trans/IsPythonLisp-j.html">Paul Prescod による反論「PythonとLispの関係について」</a></li>
    <li><a href="http://www.shiro.dreamhost.com/scheme/trans/icadmore-j.html">再反論：「技術野郎の復讐」への反響</a></li>
  </ul></li>
  <li>夢の言語</li>
  <li><a href="http://www.shiro.dreamhost.com/scheme/trans/desres-j.html">デザインとリサーチ</a></li>
  <li><a href="http://www.shiro.dreamhost.com/scheme/trans/gh-j.html">素晴らしきハッカー</a>
  <ul>
    <li>補足：<a href="http://www.shiro.dreamhost.com/scheme/trans/pypar-j.html">Python のパラドックス</a></li>
  </ul></li>
</ol>

<ul>
  <li><a href="http://www.shiro.dreamhost.com/scheme/trans/hs-j.html">知っておきたかったこと</a></li>
  <li><a href="http://www.shiro.dreamhost.com/scheme/trans/gigo-1997-03-j.html">ガベージ・イン／ガベージ・アウト：善き人々が悪しきプログラムに手を染める時</a></li>
  <li><a href="http://www.shiro.dreamhost.com/scheme/trans/being-popular-j.html">人気の言語を作るには</a></li>
  <li><a href="http://www.shiro.dreamhost.com/scheme/trans/noop-j.html">なぜ Arc はとりたててオブジェクト指向でないのか</a></li>
</ul>





## その他

<ul>
  <li><a href="https://www.amazon.co.jp/exec/obidos/ASIN/4873113482/inquisitor-22/">Kahua プロジェクト『プログラミングGauche』</a><a href=''></a></li>
  <li><a href='http://shibuya.lisp-users.org/'>Shibuya.lisp</a></li>
  <li><a href='http://www.aoky.net/articles/leon_bambrick/lisp_truth.htm'>Leon Bambrick「Lispの真実」</a></li>
  <li><a href="https://www.amazon.co.jp/exec/obidos/ASIN/4798119415/inquisitor-22/">竹内郁雄『初めての人のためのLISP』増補改訂版</a></li>
  <li><a href='http://portal.acm.org/citation.cfm?id=1057818'>Guy L. Steele Jr. and Richard P. Gabriel. The evolution of Lisp. 1996.</a>（<a href='http://www010.upp.so-net.ne.jp/okshirai/HOPL2-Uncut-j.txt'>日本語訳</a>）</li>
  <li><a href='http://www010.upp.so-net.ne.jp/okshirai/scheme-20070402222203.txt'>Guy L. Steele Jr. Scheme 過去◇現在◇未来 前編</a></li>
  <li><a href='http://cl-cookbook.sourceforge.net/'>The Common Lisp Cookbook</a></li>
  <li><a href='http://gigamonkeys.com/book/'>Peter Seibel. <em>Practical Common Lisp</em></a>（<a href='https://www.amazon.co.jp/exec/obidos/ASIN/4274067211/inquisitor-22/'>日本語訳</a>）</li>
  <li><a href="http://www.sampou.org/haskell/article/whyfp.html">John Hughes「なぜ関数プログラミングは重要か」</a></li>
  <li><a href="http://www.franz.com/resources/educational_resources/cooper.book.pdf">David Cooper, Jr. <em>Basic Lisp Techniques.</em></a></li>
  <li><a href="http://www4.ocn.ne.jp/~inukai/scheme_primer_j.html">犬飼大『入門Scheme』</a></li>
  <li><a href="http://infoshako.sk.tsukuba.ac.jp/ShakoDoc/Editors/NEmacs/EmacsGuideInJ_html/">亀井信義・舘野信行『GNU Emacs ガイドブック』</a></li>
  <li><a href="http://www.math.s.chiba-u.ac.jp/~matsu/lisp/emacs-lisp-intro-jp.html">Robert J. Chassell. Programming in Emacs Lisp（日本語）</a></li>
  <li><a href='http://wiki.livedoor.jp/kou1okada/d/LISP%20%be%f0%ca%f3%b8%bb'>LISP 情報源</a></li>
  <li><a href="http://www.math.u-toyama.ac.jp/~iwao/Scheme/scheme.html">プログラミング言語Scheme</a></li>
  <li><a href="http://www.shiro.dreamhost.com/scheme/index-j.html">Practical Scheme</a></li>
  <li><a href="https://www.amazon.co.jp/exec/obidos/ASIN/4434133632/inquisitor-22/">Doug Hoyte. <em>LET OVER LAMBDA Edition 1.0</em>（日本語訳）</a></li>
  <li><a href="unknowable/">拙文「不完全性定理の Lisp, Mathematica による記述」</a></li>
</ul>
