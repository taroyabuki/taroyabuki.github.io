---
date: 2009-01-14 14:07:24+00:00
title: Windows上のLaTeXでヒラギノを使う方法
---

TeX Liveを前提にしています。

### 和文フォントを使うだけの場合

1. `kanji-config-updmap status`としてヒラギノがインストールされていることを確認してから、
2. `kanji-config-updmap -user hiragino-pron`（フォントを埋め込まない場合は`kanji-config-updmap nofont`）

### ヒラギノ従属フォントを欧文フォントとして使う場合

上の作業に加えて，コンソールで以下を実行します。

```bash
tlmgr update --self
tlmgr repository add http://texlive.texjp.org/current/tltexjp tltexjp
tlmgr pinning add tltexjp hiraprop
tlmgr install hiraprop
```

`\hmrfamily`（明朝），`\hsffamily`（ゴシック），`\hmgfamily`（丸ゴシック）で欧文がヒラギノ従属フォントになります（`\usepackage{hiraprop}`が必要）。

後は[「ヒラギノを使う際に注意すべきこと」](http://blog.unfindable.net/archives/348)のとおり。
