---
date: 2009-06-18 14:54:30+00:00
title: SimCity、MicropolisからMegalopolisへ
---

追記：Ubuntuでは`sudo apt-get install micropolis`でインストール、`micropolis &`で遊べます。

追記：[https://github.com/SimHacker/micropolis](https://github.com/SimHacker/micropolis) で開発が継続されています（Javaへの移植など）。

[2008年1月10日にソースコードが公開された](http://weblogs.asp.net/bsimser/archive/2008/01/10/simcity-source-code-released-to-the-wild-let-the-ports-begin.aspx)シムシティあらためマイクロポリス（ライセンスはGPL）。

ソースコードは[Micropolis Downloads](http://www.donhopkins.com/home/micropolis/)からダウンロードできるが、そのままではビルドできない。

[Slashdotの記事](http://developers.slashdot.org/article.pl?sid=08/01/12/1846256)についた[コメント](http://slashdot.org/comments.pl?sid=416222&cid=22020626)で、[パッチ](http://rmdir.de/~michael/micropolis_mac-osx.patch)が発表されている。このパッチは、Mac用として配布されているが、他の環境でも利用できる。（「パッチを当てたソースの最新版が[http://git.zerfleddert.de/cgi-bin/gitweb.cgi/micropolis?a=snapshot;h=HEAD;sf=tgz](http://git.zerfleddert.de/cgi-bin/gitweb.cgi/micropolis?a=snapshot;h=HEAD;sf=tgz)にある」とパッチに書いてある。）

初代SimCityを試してみたいというだけなら、ブラウザで遊べる[シムシティクラシック](https://web.archive.org/web/20130627163858/http://www.japan.ea.com:80/simcity4/playonline.html)が簡単だが（追記：後にサービス終了．画像参照）、せっかくコードが公開されたのだから、自分でビルドしてみたい。

![シムシティクラシック](https://taroyabuki.github.io/images/simcity/simcity-classics.png)

## GNU Linux (Ubuntu)の場合

Windowsの人も、仮想マシンや[andLinux](http://www.andlinux.org/index.php)を導入すれば、以下の方法が有効になる。

まず、必要なパッケージをインストールする。

```{bash}
sudo apt-get update
sudo apt-get install libxext-dev libxpm-dev bison
#andLinuxの場合
#sudo apt-get update
#sudo apt-get install libxext-dev libxpm-dev bison mplayer
```

404エラーが出て`sudo apt-get update`が失敗するときは、/etc/apt/sources.list に書かれているドメインを、すべて`old-releases.ubuntu.com`に変更してみる。

次に、ソースコードをダウンロードする。

```{bash}
wget -O micropolis.tar.gz "http://git.zerfleddert.de/cgi-bin/gitweb.cgi/micropolis?a=snapshot;h=HEAD;sf=tgz"
```

## Mac OS Xの場合

まず、[Xcodeをインストール](http://developer.apple.com/xcode/)し、<del>音が出るように、[play](http://www.hieper.nl/html/play.html)をPATHの通った場所に置く。</del>

次に、ソースコードをダウンロードする。

```{bash}
curl -o micropolis.tar.gz "http://git.zerfleddert.de/cgi-bin/gitweb.cgi/micropolis?a=snapshot;h=HEAD;sf=tgz"
```

## ビルド・実行

```{bash}
tar zxf micropolis.tar.gz
cd micropolis-HEAD-ハッシュ値
make
./Micropolis
```

![動作画面](https://taroyabuki.github.io/images/simcity/simcity.png)

画面サイズが大きすぎる場合には、res/whead.tclとres/wscen.tcl、res/wsplash.tclを修正する。

## MicropolisからMegalopolisへ

人口が50万人を超えるとメガロポリスになる。クリックで拡大。（1920x1600より少し大きいディスプレイがあれば、マップ全体を表示したまま遊べるのかな。）

[![メガロポリス](https://taroyabuki.github.io/images/simcity/megalopolis-th.jpg)](https://taroyabuki.github.io/images/simcity/megalopolis.png)

祝福メッセージを読みたい人は、実際に作ってみたらいいと思う。よく知られたテクニック（チート）は以下の通り。

1. 道路・火力発電所は建設しない
2. 中心部と沿岸部が重複しない地形を選ぶ
3. 工場・港・空港はマップの端に建設する（港についてはチート）
4. 災害はオフにする（チート）
5. 税率は7%。人口が45万人を超えたら0%にする（チート）
6. 消防署は一つだけ（チート）
7. 地価が高い場所（マップの中心部と緑や公園の近く）は住宅地と商業地のみにする。警察署も不要（チート）
8. 地価の高い場所の教会と病院は解体する（チート）
9. 建物は、線路に1ブロックでも接していればよい（チート）
10. 線路は繋がっていなくてもよい（チート）
