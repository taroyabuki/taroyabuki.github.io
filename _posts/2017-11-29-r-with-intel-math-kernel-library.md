---
date: 2017-11-29 14:07:17+00:00
title: RでIntel Math Kernel Libraryを使う方法
category: code
tags: r
---

Intel Math Kernel Library（以下MKL。フリーソフトウェアではないが無料）を使うようにRをビルドして高速化する方法です。[R benchmarks](http://r.research.att.com/benchmarks/)で公開されているR benchmark 2.5なら，3倍くらい速くなります。こんな手間をかけなくても，Microsoft R Openを入れるだけで同じ効果を得られるのですが，「`-O3 -march=native`」でビルドしたら速いのかな？などと思う方のためのメモです。

[https://software.intel.com/en-us/mkl](https://software.intel.com/en-us/mkl)で「Free Download」をクリックします。サインアップあるいはサインインして，Intel Math Kernel Libraryをクリックすると，l_mkl_2018.1.163.tgzのようなファイルがダウンロードされます。

ダウンロードしたファイルのあるディレクトリで，以下を実行します。

```bash
tar zxf l_mkl_2018.1.163.tgz
cd l_mkl_2018.1.163
sed 's/ACCEPT_EULA=decline/ACCEPT_EULA=accept/' silent.cfg > s.cfg
sudo ./install.sh --silent ./s.cfg
cd ..
```    

Rのソースをダウンロードし，展開します。

```bash
wget https://cloud.r-project.org/src/base/R-3/R-3.4.2.tar.gz
tar zxf R-3.4.2.tar.gz
cd R-3.4.2
```

ビルドに必要なパッケージを入れます。

```bash
sudo apt install r-base xorg-dev libcurl4-openssl-dev -y
```    

MKLのための環境変数を設定します。

```bash
source /opt/intel/mkl/bin/mklvars.sh intel64

MKL="-Wl,--no-as-needed -lmkl_gf_lp64 -Wl,--start-group -lmkl_gnu_thread  -lmkl_core  -Wl,--end-group -fopenmp  -ldl -lpthread -lm"
````    

configure! （Anacondaをふつうに入れていると，システムのcurlが隠されているせいで失敗します。この作業の間だけのことなので，AnacondaをPATHからはずすか，$HOME/anaconda3の名前を変えておきましょう。）

```bash
CFLAGS="-O3 -march=native" CXXFLAGS="$CFLAGS" FFLAGS="$CFLAGS" FCFLAGS="$CFLAGS" ./configure --with-blas="$MKL" --with-lapack
```    

ビルドします。

```bash
make -j $(nproc)
```    

`bin/R`で実行します。`make install`でインストールできますが，そこまでしなくてもいいでしょう。

参考：[R でインテル® MKL を使用する](https://www.isus.jp/products/mkl/using-mkl-with-r/)
