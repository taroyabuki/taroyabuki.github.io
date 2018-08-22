---
date: 2018-08-19 00:00:00+09:00
title: WordPressからJekyllへの移行手順
---

ココログ→Movable Type（自前サーバ）→WordPress（自前サーバ）と移ってきたブログを，静的なものにしてGitHub Pagesに置くことにしました。

参考：[https://jekyllrb.com/](https://jekyllrb.com/)（[日本語訳](https://jekyllrb-ja.github.io/)）

## 移行の理由

ブログを静的にする理由は以下の通りです。

* WordPressの更新が面倒
* WordPressのプラグインの更新が面倒
* WordPressのためのソフトウェア（ApacheやMySQL，PHP）の更新が面倒
* OSの更新が面倒
* HTTPS対応が面倒
* ブログサービスを使いたくない


## 具体的な手順

Dockerが動くことを前提に進めます。

Jekyllの[https://import.jekyllrb.com/docs/wordpressdotcom/](https://import.jekyllrb.com/docs/wordpressdotcom/)で紹介されている[Exitwp](https://github.com/thomasf/exitwp)を使って移行します。

作業用のフォルダを作ります。

```bash
mkdir $HOME/jekyll
```

Docker run!

```bash
docker run --rm -it -v $HOME/jekyll:/jekyll ubuntu bash
```

コンテナのシェルで，以下を実行します。

```bash
apt update
apt install -y git python-pip
pip install PyYAML bs4

cd /jekyll
git clone https://github.com/thomasf/exitwp.git
```

（ホスト側で）$HOME/jekyll/exitwp/wordpress-xmlに，WordPressからエクスポートしたファイル（例：wordpress.2018-08-19.xml）を置きます。

コンテナのシェルで，変換を実行します。

```bash
cd /jekyll/exitwp
python exitwp.py
```

これで，ブログの1記事が1ファイルになって，ホストの$HOME/jekyll/exitwp/build/jekyll/{ブログのBase URL}/_posts に格納されます。この_postだけホストの$HOME/jekyllに移動して，あとは消しておきましょう。コンテナのシェルで，以下を実行します。

```bash
mv build/jekyll/{ブログのBase URL}/_posts /jekyll
rm -r exitwp
exit
```

## Jekyllの準備（1回だけ）

WindowsでDocker Toolboxを使う場合は，4000番ポートを使えるようにしておきます。

```bash
VBoxManage controlvm "default" natpf1 "jekyll,tcp,127.0.0.1,4000,,4000"
```

JekyllはDockerのイメージjekyll/jekyllを使って実行します。

```bash
docker run -it -p 4000:4000 -v $HOME/jekyll:/srv/jekyll --name jekyll jekyll/jekyll bash
```

コンテナのシェルで，以下を実行します。Jekyllのテーマはとりあえずminimaということにします（[GitHubでサポートされるテーマ](https://pages.github.com/themes/)）。設定ファイル（_config.yml）は https://github.com/jekyll/minima を参考に書くといいでしょう。GitHubでの公開用には，https://jekyllrb.com/docs/github-pages/ も参考になります。

```bash
touch README.md

cat << EOS > _config.yml
title: ブログのタイトル
author: 著者
description: 概要
theme: minima
EOS

cat << EOS > Gemfile
source "https://rubygems.org"
gem "github-pages", group: :jekyll_plugins
EOS

bundle update
exit
```

## Jekyllの実行

コンテナを起動します。

```bash
docker start jekyll
```

ブログを生成します（Ctrl-Cで停止）。

```bash
docker exec -it jekyll jekyll serve
```

http://localhost:4000 にアクセスして，変換後のブログを閲覧します。

<p>記事中に&#x7b;&#x7b;x, y&#x7d;のような文字列があるとエラーになるので，何らかの方法で直す必要があります。（解決策の例：p要素の中で<code>&amp;#x7b;&amp;#x7b;x, y&amp;#x7d;</code>のように文字参照を使う。）</p>

Ctrl-Cで止めてから，上のコマンドを実行すると，ブログを再生成します。

## 掃除

```bash
docker stop jekyll
docker rm jekyll
```

## GitHubでの公開

$HOME/jekyll全体をプッシュすればいいのですが，詳細は割愛します。