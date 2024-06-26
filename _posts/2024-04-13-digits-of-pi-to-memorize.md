---
date: 2024-04-13 00:00:00+09:00
title: 円周率は何桁まで覚えておくとよいのか
image: https://taroyabuki.github.io/images/2024-04-08-comath.png
twitter:
  card: summary_large_image
---

昭和60年代前半，当時小学生だったタロウ少年は，円周率を**17桁**，**3.1415926535897932**まで覚えました．覚える桁数はそれでよかったのか，という話です．

[![書影](https://www.ohmsha.co.jp/Portals/0/book/small/978-4-274-23179-7.jpg)](https://github.com/taroyabuki/comath)

[拙著『コンピュータでとく数学』](https://github.com/taroyabuki/comath)について，こんな質問がありました．

> p.29に倍精度でのπの近似値は 884279719003555/281474976710656 とありますが，245850922/78256779 でよいのではないでしょうか．

良い質問です．というか，そんなに丁寧に読んでもらえるとは思わなかったというのが正直なところです．

因みに，分子の245850922は，[Numerators of convergents to Pi](https://oeis.org/A002485/list)のa(16)ですね．

## 短い回答

次の二種類の近似があるせいで，わかりにくいのでしょう．

1. 数を浮動小数点数で近似すること
2. πを有理数で近似すること

『コンピュータでとく数学』で重要なのは1です．数xを浮動小数点数で表すときの，式(2.10)の意味での厳密値をf(x)とします．

- A:=884279719003555/281474976710656
- B:=245850922/78256779

とすると<br>
f(A)=f(B)=f(π)=A≠B<br>
です．つまり，A, B, πは浮動小数点数にすると全て同じ値になりますが，その値と等しいのはAだということです．

<hr>

上記の2「πを有理数で近似すること」についての話は長くなります．『コンピュータでとく数学』とはほとんど関係のない話です．

## 長い回答（話）

πを有理数で近似する場合に，最もよく使われるのは**3.14** (=314/100)でしょう．**3.1415** (=31415/10000)くらいまでなら，覚えている人は多いかも知れません．

もっと覚えるなら，**3.1415926535897932**（**17桁**）がお勧めです．

**理由1**：浮動小数点数（IEEE 754の**binary64**）で表現できる円周率の「最良」，つまりπとの差の絶対値が最小の近似値は884279719003555/281474976710656（約**3.141592653589793**12）です．正しいのは16桁までですが，次のA, B, Cのうちで最良の近似値に最も近いのはB（17桁）です．

- A:=**3.141592653589793**（16桁）
- B:=**3.1415926535897932**（17桁）
- C:=**3.14159265358979323**（18桁）

**理由2**：10進小数での入力`3.1415926535897932`（17桁）がπのbinary64での最良の近似値になります．1桁少ない`3.141592653589793`（16桁）も最良の近似値になりますが，**理由1**があるため`3.1415926535897932`がよいでしょう．

近似に使う有理数の分母は，10のベキ乗の場合に限りません．例えば，**22/7** （約**3.14**2）は314/100より簡潔で正確です．**355/113**（約**3.141592**9）も覚えやすくてよいです．

昭和の時代に近所の図書館で借りて読んだ，[木村良夫『パソコンで遊ぶ数学: BASICの基礎からグラフィックスまで』（ブルーバックス, 1986）](https://ndlsearch.ndl.go.jp/books/R100000002-I000001835898)（以下，**木村本**）に，πを有理数で近似する，次のようなプログラムが載っていました[^1]．

[^1]:オリジナルでは`PRINT`は`LPRINT`．木村本の通りに`105  DEFDBL A-Z`を補いました．また，πの近似値との差が0になったら終了するように，`185`を追加しました．

```basic
100 REM ***** Fraction.1986.8.29
105  DEFDBL A-Z
110  A=4*ATN(1)
120  N=1:D=1
130  L=A*N
140  M=INT(L)
150  IF M+1-L<L-M THEN M=M+1
160  E=ABS(A-M/N)
170  IF E>=D GOTO 200
180  PRINT M;"/";N,M/N
185  IF E=0 THEN END
190  D=E
200  N=N+1
210  GOTO 130
```

問題を整理しましょう．MとNを整数とします．

- 問題P1：πを，M/Nで近似する．
- 問題P2：πを近似する浮動小数点数Aを，浮動小数点数M/Nで近似する．

ここで解こうとしているのは問題P2です．昭和のパソコンでは，Aに近づけるのが大変でしたが，現代では，（倍精度なら）Aとの差を0にできてしまうので，Aが本当にπの最良の近似値になっているかが重要になります．

P1とP2は同じ問題のように見えます．実際，後で示すIEEE 754での解とMBFでのP2の解はP1の解と同じです．しかし，BCDでのP2の解はP1の解と異なります．P2の解としては，`5419351/1725033=3.1415926535897`よりも，`10838702/3450066=3.1415926535898`が（πに近くて）良いのですが，P1の解としては，10838702/3450066よりも，これを約分した5419351/1725033が（単純で）良いのです．

### 結果の概要

いくつかのシステムで試した結果を表にまとめます．規格による精度の違いが結果に表れています．

時代|システム|規格|最終結果|10進小数表示
--|--|--|--|--
昭和|MSX-BASIC|BCD|`10838702/3450066`|約**3.141592653589**81
現代|C言語|IEEE 754|`245850922/78256779`|約**3.141592653589793**16
現代|C言語|x87の80ビット拡張形式|`8717442233/2774848045`|約**3.141592653589793238**5
現代|bwBASIC|IEEE 754|`245850922/78256779`|約**3.141592653589793**16
昭和|N-BASIC|MBF|`657408909/209259755`|約**3.1415926535897932**2
現代|PC-BASIC|MBF|`657408909/209259755`|約**3.1415926535897932**2

### （昭和）PC-8001 N-BASIC

> お前の前にいるのは，四十年以上生きたプログラマだ．

家にあったマイコン（**PC-8001**，**N-BASIC**）での計算を，実機の32倍速で動くという[PC-8001のエミュレータ](http://upd780c1.g1.xrea.com/pc-8001/)で再現します（コードを入力して`RUN`で実行．STOPキーで停止）．

![PC-8001で実行している様子](/images/2024-04-pi-pc-8001.png)

![PC-8001のVirtual Keyboard](/images/2024-04-pi-pc-8001-keyboard.png)

#### 失敗1（`ATN`は単精度）

355/113の次が235387/74926になっているのは，木村本に書かれているとおり，誤りです．誤りの原因は，`ATN`が単精度であることです．`110  A=4*ATN(1)`で得るπの近似値が倍精度では不正確（約**3.141592**9）なので，計算を進める意味がありません．

#### 失敗2（πの10進小数表示）

とりあえず，覚えていた17桁を使って`110  A=3.1415926535897932`として計算します．実機を何日も動かしていたら，プログラムは`742972117 / 236495370    3.141592653589793`を出力して停止したはずです．

ここに罠があります．

N-BASICの浮動小数点数は[Microsoft Binary Format (MBF)](https://en.wikipedia.org/wiki/Microsoft_Binary_Format)です．`A=3.1415926535897932`は，MBFの倍精度で表現できるπの最良の近似値ではありません．バイト列（リトルエンディアン）を比べると次のようになります（1バイト目が違います）．

- `C4 68 21 A2 DA 0F 49 82`（`A=3.1415926535897932`）．
- `C2 68 21 A2 DA 0F 49 82`（πの最良の近似値．補足1を参照）

ですから，`A=3.1415926535897932`を使うのは誤りなのですが，`PRINT A`の結果は`3.141592653589793`なので，小学生はこの罠に気付かないでしょう．

因みに，`A`の1バイト目は`PRINT HEX$(PEEK(VARPTR(A)))`で確認できます[^2]．

[^2]: Aのメモリ上での表現は，`PRINT HEX$(VARPTR(A))`の結果を`XXXX`として，`MON`としてマシン語モニタに入って，`DXXXX`としても確認できます（Ctrl-Bで終了）．マシン語モニタで`TM`とした画面を約40年ぶりに見ました．

#### 成功

Aの1バイト目を`C2`に書き換えます．

```basic
110  A=3.1415926535897932:POKE VARPTR(A),&HC2
```

2GHzで動くという[M88 emulator](http://retropc.net/cisc/m88/)で試すと，プログラムは`657408909 / 209259755    3.141592653589793`を出力して停止します（**πの最良の近似値との差は0**）．

#### 罠の一覧

バイト列が`?? 68 21 A2 DA 0F 49 82`（`??`は`B4`から`C7`）になる20個の数は全て，`PRINT`で表示させた結果は`3.141592653589793`で，それでは区別できません．

```basic
10 DEFDBL A
20 A=3.141592653589793#
30 FOR B=&HB3 TO &HC8
40   POKE VARPTR(A),B:PRINT A;
50 NEXT B
```

πのMBFでの近似値の1バイト目を表にまとめます（マチンの公式については補足2を参照）．

1バイト目|入力方法
--|--
`C2`（最良）|`A=3.1415926535897932:POKE VARPTR(A),&HC2`
`C2`（最良）|N88-BASICでマチンの公式（修正2, 3）
`C2`（最良）|PC-BASICで19桁入力
`C2`（最良）|PC-BASICでマチンの公式（修正1）
`C3`|N88-BASICでマチンの公式（修正3）|
`C4`|N(88)-BASICで17桁入力
`C5`|N88-BASICでマチンの公式（修正2）|
`C6`|N-BASICでマチンの公式（修正1, 2, 3）|
`C6`|N88-BASICでマチンの公式（修正前）|
`C7`|N-BASICでマチンの公式（修正1, 3）|
`CB`|N-BASICでマチンの公式（修正1, 2）|
`CC`|N-BASICでマチンの公式（修正1）|

### （昭和）MSX-BASIC

当時，少しでも先の結果を知りたいと思って，高性能なMSX2を持っていた友達に，プログラムを**電話で読み上げて伝えて**，実行してもらおうとしました（付き合ってくれる友達がすごいですね．何て頼んだんだろう）．プログラムが誤って伝わり，正しく実行できなかったのを，その友達のお父さんが直してくれて，結果をプリンタに出力してくれたことに驚愕しました．（インターネットのメールはなく，家庭用のプリンタも珍しかった時代です．）

> 子供には心の支えになる大人の存在が必要ですから．

実機の10倍速で動くという[MSXのエミュレータ](http://bluemsx.msxblue.com/jindex.htm)で試します（コードは最初のまま．Ctrl-STOPで停止．参考：[プログラムのロード方法](https://bps-basic.blogspot.com/2016/10/windows10msx-basic.html)）[^msx]．

[^msx]: [WebMSX](https://webmsx.org/)（ブラウザで動くエミュレータ）も便利です．

![MSXで実行している様子](/images/2024-04-pi-msx.png)

実機を動かし続けていたら，プログラムは`10838702 / 3450066    3.1415926535898`を出力して停止したはずです（**πの最良の近似値との差は0**）．

少年達は，N-BASICとMSX-BASICで結果が異なることに気付いたでしょうか．

結果が異なるのは，採用している浮動小数点数の規格が異なるからです．

N-BASICの浮動小数点数がMBFだったのに対して，MSX-BASICの浮動小数点数は[BCD](https://ja.wikipedia.org/wiki/%E4%BA%8C%E9%80%B2%E5%8C%96%E5%8D%81%E9%80%B2%E8%A1%A8%E7%8F%BE)です．MSX-BASICの倍精度では，数の10進小数表示14桁を，各桁4ビット，全56ビットで表します．

この形式で表せる，πの最良の近似値は**3.141592653589**8です．`4*ATN(1)`はこれと等しいです．

N-BASICでは，`PRINT`の結果が3.141592653589793になる浮動小数点数が20個もありました．MSX-BASICにはそういうことはありません．性能はともかく，小学生にとってわかりやすかったのは間違いないです．

### 現代

Dockerで試します．

```bash
docker run --rm -it ubuntu
apt update
cd
```

#### （現代）C言語

C言語を試します．まずはdoubleの場合．

```bash
apt install -y gcc

cat << "EOF" > pi.c
#include <stdio.h>
#include <math.h>

int main() {
    double A, D, E, L, M, N;
    A = 4 * atan(1);
    N = 1, D = 1;
    while (1) {
        L = A * N;
        M = floor(L);
        if (M + 1 - L < L - M) M = M + 1;
        E = fabs(A - M / N);
        if (E < D) {
            printf("%.0f / %.0f\t %.17f\n", M, N, M / N);
            if (E == 0) break;
            D = E;
        }
        N = N + 1;
    }
    return 0;
}
EOF

gcc -O3 -Wall pi.c -lm && ./a.out
```

プログラムが`245850922 / 78256779     3.14159265358979312`を出力して停止するまでは一瞬です（**πの最良の近似値との差は0**）．

次にUbuntu x86_64上のGCCのlong double（x87の80ビット拡張形式）の場合．

```bash
cat << "EOF" > pi-longdouble.c
#include <stdio.h>
#include <math.h>

int main() {
    long double A, D, E, L, M, N;
    A = 4 * atanl(1);
    N = 1, D = 1;
    while (1) {
        L = A * N;
        M = floorl(L);
        if (M + 1 - L < L - M) M = M + 1;
        E = fabsl(A - M / N);
        if (E < D) {
            printf("%.0Lf / %.0Lf\t%.19Lf\n", M, N, M / N);
            if (E == 0) break;
            D = E;
        }
        N = N + 1;
    }
    return 0;
}
EOF

gcc -O3 -Wall pi-longdouble.c -lm && ./a.out
```

プログラムは`8717442233 / 2774848045 3.1415926535897932385`を出力して停止します（**πの最良の近似値14488038916154245685/4611686018427387904との差は0**）．

4倍精度（メモ）

```bash
cat << "EOF" > pi-quadmath.c
#include <stdio.h>
#include <quadmath.h>

int main() {
    char b1[128], b2[128], b3[128];
    __float128 A, D, E, L, M, N;
    A = 4 * atanq(1);
    N = 1, D = 1;
    while (1) {
        L = A * N;
        M = floorq(L);
        if (M + 1 - L < L - M) M = M + 1;
        E = fabsq(A - M / N);
        if (E < D) {
            quadmath_snprintf(b1, sizeof(b1), "%.0Qf", M);
            quadmath_snprintf(b2, sizeof(b2), "%.0Qf", N);
            quadmath_snprintf(b3, sizeof(b3), "%.37Qg", M / N);
            printf("%s / %s\t%s\n", b1, b2, b3);
            if (E == 0) break;
            D = E;
        }
        N = N + 1;
    }
    return 0;
}
EOF

gcc -O3 -Wall pi-quadmath.c -lquadmath && ./a.out
```

#### （現代）bwBASIC

[bwBASIC](https://manpages.ubuntu.com/manpages/noble/man1/bwbasic.1.html)を試します．（`180`の正しい書き方がわかりません．）

```
apt install -y bwbasic

cat << "EOF" > bw.bas
100 REM ***** Fraction.1986.8.29
110  A=4*ATN(1)
120  N=1:D=1
130  L=A*N
140  M=INT(L)
150  IF M+1-L<L-M THEN M=M+1
160  E=ABS(A-M/N)
170  IF E>=D THEN GOTO 200
180  PRINT USING "# / #    #.###############";M;N;M/N
185  IF E=0 THEN END
190  D=E
200  N=N+1
210  GOTO 130
EOF

bwbasic bw.bas
```

しばらく待つと，プログラムは`245850922 / 78256779    3.14159265358979312`を出力して停止します（`SYSTEM`で終了．**πの最良の近似値との差は0**）．

#### （現代）PC-BASIC

[PC-BASIC](https://robhagemans.github.io/pcbasic/)を試します．高速化のために，PyPyを使います．

`A=3.141592653589793238`（19桁）とすると，Aの1バイト目が`C2`になります（[POKEは使えないようです](https://github.com/robhagemans/pcbasic/issues/108)）．タロウ少年は，**19桁**まで覚えるべきだったかもしれません．

```
apt install -y git pypy3
git clone https://github.com/robhagemans/pcbasic.git

cat << "EOF" > pc.bas
100 REM ***** Fraction.1986.8.29
105  DEFDBL A-Z
110  A=3.141592653589793238
115  PRINT HEX$(PEEK(VARPTR(A)))
120  N=1:D=1
130  L=A*N
140  M=INT(L)
150  IF M+1-L<L-M THEN M=M+1
160  E=ABS(A-M/N)
170  IF E>=D GOTO 200
180  PRINT M;"/";N,M/N
185  IF E=0 THEN END
190  D=E
200  N=N+1
210  GOTO 130
EOF

pypy3 pcbasic/run-pcbasic.py -n pc.bas
```

しばらく待つと，プログラムは`657408909 / 209259755    3.141592653589793`を出力して停止します（`SYSTEM`で終了．**πの最良の近似値との差は0**）．

### 補足1：MBFの倍精度で表現できるπの最良の近似値

MBFの倍精度で表現できるπの最良の近似値は

- s:=0<sub>2</sub>
- e:=10000010<sub>2</sub>
- f:=1001001000011111101101010100010001000010110100011000010<sub>2</sub>

として，(-1)<sup>s</sup>×(1+f×2<sup>-55</sup>)×2<sup>(e-129)</sup>=28296951008113761/9007199254740992（約**3.1415926535897932**27）です．

「e, s, f」をまとめて16進数で表すと，`82 49 0F DA A2 21 68 C2`です．メモリ上では`C2 68 21 A2 DA 0F 49 82`（リトルエンディアン）となります（1バイト目は`C2`）．

```wolfram
(* Mathematica *)
s = 0;
e = 2^^10000010;
f = 2^^1001001000011111101101010100010001000010110100011000010;
x = (-1)^s (1 + f 2^(-55)) 2^(e - 129)
N[x, 20]
BaseForm[2^56 e + 2^55 s + f, 16]
```

### 補足2：マチンの公式＋arctanのマクローリン展開

木村本では，`4*ATN(1)`でπの近似値を求めるのに失敗した後で，マチンの公式とarctanのマクローリン展開を使う方法が試されています．しかし，小学生に理解できたとは思えません．実行しても良い結果は得られなかったでしょう．

πの近似値を求める部分は次のとおりです[^3]．

[^3]: オリジナルでは，`130`と`160`は10進小数表示でしたが，`#`を付けるなら分数で書いても大丈夫です．結果の確認用に，`291`と`292`を追加しました．

```basic
100 REM ***** FRACT2 1986.9.1
110  DEFDBL A,D,E,L,M,N,X,Y,Z
120  K=11
130  X=1/5#
140  GOSUB 200
150  Z=Y
160  X=1/239#
170  GOSUB 200
180  A=16*Z-4*Y
190  GOTO 290
200 REM *** arctan
210  Y=0
220  FOR I=1 TO K
230    J=2*I-1
240    IF I MOD 2=0 GOTO 260
250    Y=Y+X^J/J:GOTO 270
260    Y=Y-X^J/J
270  NEXT I
280  RETURN
290 REM ***** Fraction
291  PRINT A
292  PRINT HEX$(PEEK(VARPTR(A)))
```

`250`と`260`で使うベキ乗「`^`」は，N-BASICでは単精度なので，このままではうまく行きません．

次のように修正して実行します．

1. ベキ乗をFOR文で計算します．
2. マクローリン展開の次数`K`を12にします．（13以上にしても改善しません．）
3. マクローリン展開を，次数の高い方から計算します．

```basic
120  K=12
220  FOR I=K TO 1 STEP -1
231    DEFDBL P:P=1
232    FOR R=1 TO J
233      P=P*X
234    NEXT R
240    IF I MOD 2=0 GOTO 260
250    Y=Y+P/J:GOTO 270
260    Y=Y-P/J
```

`PRINT A`の結果は**3.141592653589793**になりますが，`A`の1バイト目は`C6`なので，これはπの最良の近似値ではありません（最良は`C2`）[^4]．

[^4]: N88-BASICのベキ乗「`^`」は倍精度なので，最初のコードのまま計算できますが，その結果の`A`の1バイト目は`C6`です．それを有理数で近似するプログラムは，`914098533 / 290966600    3.141592653589793`を出力して停止します．修正2（`120  K=12`），修正3（`220  FOR I=K TO 1 STEP -1`）を施すと，`A`の1バイト目は`C2`になります（最良）．

## 蛇足

木村本は，私にとって初めての，エレガントな証明がわからない定理が載っていた，大事な本でもあります．（引用中の数は1以上9以下の整数のこと）

> 4つの数が0を含まず，しかもすべて互いに異なっているなら，この4つの数から四則で10をつくることができる．

この本を借りた図書館のOPACを調べると，残念なことに除籍になってしまったようなので，古書で入手しました．やはり，大事な本は買わなければなりません．
