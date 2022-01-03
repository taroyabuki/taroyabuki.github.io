---
date: 2012-11-05 13:59:26+00:00
title: Mac OS X 10.8 Mountain Lionで失われた類語辞典を求めて
---

「日本語に類語辞典はいらない」とAppleが判断したということなのでしょうか。

Mac OS X 10.7 Lionの辞書アプリには次の日本語関係辞書が含まれていました。

* 大辞泉（小学館）
* プログレッシブ英和・和英中辞典（小学館）
* **類語例解辞典（小学館）**

Mac OS X 10.8 Mountain Lionの辞書アプリに含まれている日本語関係辞書は次の通りです。

* スーパー大辞林（三省堂）
* ウィズダム英和・和英辞典（三省堂）

大辞泉が大辞林に、プログレッシブがウィズダムになったのはいいのですが、類語辞典が無くなったのは明らかに劣化です。大辞林が類語辞典を兼ねるわけでもありませんし。（皮肉なことに、大辞泉の語釈には類語も記載されているので、類語辞典を兼ねられます。）

10.7 Lionが手元にあれば、/Library/Dictionariesのファイルを~/Library/Dictionariesにコピーすればいいようですが（参考：[最高の辞書環境を目指して](https://zariganitosh.hatenablog.jp/entry/20121023/best_dictionary)）、そうでない場合はインストールメディアからサルベージするのでしょうか。

1. LionをApp Storeからダウンロードし直す（これができない人にこの記事は無意味です）
2. アプリケーションの「Mac OS X Lion インストール」をCtrl+クリック、「パッケージ内容の表示」をクリックする
3. Contents/SharedSupport/InstallESD.dmgをダブルクリックする
4. ターミナルで次を実行する

```bash
cd ~/Desktop #別の場所でもいい
pkgutil --expand "/Volumes/Mac OS X Install ESD/Packages/Essentials.pkg" ./tmp
ditto -x --bom ./tmp/Bom ./tmp/Payload ./

5. ライブラリ/Dictionariesの中に小学館の辞書がある

参考：[Mac OS Xのパッケージファイルを操作する](https://www.niw.at/archives/blog/16690761384)

![](/images/2012-11-05-dictionary.png)
