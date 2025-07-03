---
date: 2015-02-02 00:00:00+09:00
title: フェルマーの最終定理の「反例」
---

「3以上の整数nについて、aのn乗+bのn乗=cのn乗を満たすような整数a, b, cは存在しない」というフェルマーの最終定理（フェルマー・ワイルズの定理）は、1995年にアンドリュー・ワイルズによって証明されました。「証明された」とは言っても、その証明を読んで納得できるのは、ごく一部の数学が得意な人に限られる残念なことです。

数学は苦手だからちょっと計算して雰囲気を味わおうと思っても、今度は計算が得意じゃななければなりません。たとえばこんなことになります。

* a=139, b=954, c=2115, n=3
    * [C言語](http://codepad.org/bw5KJTvo)
    * [Java](https://ideone.com/HK3l7z)
* a=25165824, b=33554432, c=37748736, n=3
    * [Bash](https://ideone.com/zn8lbh)
* a=5999856, b=99992800, c=100000000, n=3
    * [Google](https://www.google.co.jp/search?q=5999856%5E3%2B99992800%5E3-100000000%5E3)
    * ([WolframAlpha](https://www.wolframalpha.com/input/?i=5999856%5E3%2B99992800%5E3-100000000%5E3))
    * [Perl](http://codepad.org/Od1ERpyn)
    * [AWK](https://ideone.com/sGeqjE)
    * [JavaScript](http://ideone.com/Q4E3y0)
    * ([Scheme](http://codepad.org/XR7f2AjL))
    * ([Python](http://codepad.org/wFm0r60H))
    * ([Ruby](http://codepad.org/ODqOK5Bq))
    * MySQL
    * 電卓
    * Excel

## a=139, b=954, c=2115, n=3

```c
//C言語
#include <stdio.h>
int main ()
{
  int a, b, c;
  for (c=1; ; c++) {
    for (a=1; a<c; a++) {
      for (b=a; b<c; b++) {
        if (a*a*a+b*b*b==c*c*c) goto found;
      }
    }
  }
  found:
    printf("Found!\n");
    printf("a=%d, b=%d, c=%d\n", a, b, c);
    return 0;
}
```

```java
//Java
public class Fermat {

  public static void main(String[] args) {
    int a, b, c;

    search:
    for (c = 1;; c++) {
      for (a = 1; a < c; a++) {
        for (b = a; b < c; b++) {
          if (a * a * a + b * b * b == c * c * c) {
            break search;
          }
        }
      }
    }
    System.out.println("Found!");
    System.out.printf("a=%d, b=%d, c=%d\n", a, b, c);
  }
}
```

## a=25165824, b=33554432, c=37748736, n=3

```bash
#Bash
a=25165824; b=33554432; c=37748736; echo $((a**3+b**3-c**3))
```

## a=5999856, b=99992800, c=100000000, n=3

```perl
#Perl
$a=5999856;
$b=99992800;
$c=100000000;
print $a*$a*$a+$b*$b*$b-$c*$c*$c;
```

```bash
#AWK
echo '5999856 99992800 100000000' | gawk '{print $1**3 + $2**3 - $3**3}'
# 0
```

```javascript
//JavaScript
a=5999856;
b=99992800;
c=100000000;
print(a*a*a+b*b*b-c*c*c);
```

```scheme
;Scheme
(display (- (+ (expt 5999856 3.0) (expt 99992800 3.0)) (expt 100000000 3.0)))
```

```python
#Python
a=5999856;
b=99992800;
c=100000000;
print a**3.0+b**3.0-c**3.0;
```

```ruby
#Ruby
a=5999856;
b=99992800;
c=100000000;
print a**3.0+b**3.0-c**3.0;
```

### 補足

AWKでは、`-M`を付けて起動すれば、「反例」にはなりません。

```bash
echo '5999856 99992800 100000000' | gawk -M '{print $1**3 + $2**3 - $3**3}'
# -2985984
```

参考：[GNU AWKはまだまだ成長中！ ユーザーの声をもとに作成された拡張機能を組み込んでみよう](https://codezine.jp/article/detail/8324)

### MySQL

```
SELECT
  5999856E0*5999856E0*5999856E0
 +99992800E0*99992800E0*99992800E0
 -100000000E0*100000000E0*100000000E0\G
*************************** 1. row ***************************
5999856E0*5999856E0*5999856E0
 +99992800E0*99992800E0*99992800E0
 -100000000E0*100000000E0*100000000E0: 0
1 row in set (0.00 sec)
```

#### 補足

`5999856E0`を`5999856.0`に変えると、「反例」ではなくなります。

```
SELECT
  5999856.0*5999856.0*5999856.0
 +99992800.0*99992800.0*99992800.0
 -100000000.0*100000000.0*100000000.0\G
*************************** 1. row ***************************
5999856.0*5999856.0*5999856.0
 +99992800.0*99992800.0*99992800.0
 -100000000.0*100000000.0*100000000.0: -2985984.000
1 row in set (0.00 sec)
```

`5999856`、`5999856E0`、`5999856.0`は、

* 算数や数学では、5999856＝5999856E0＝5999856.0（すべて同じ）、
* 自然科学では、5999856＝5999856E0≠5999856.0（有効数字の意味で）、
* 多くのプログラミング言語では、5999856≠5999856E0＝5999856.0（整数と浮動小数点数）。

多くのプログラミング言語と異なり、MySQLではすべて別物です。次のように確認できます。

```
CREATE TEMPORARY TABLE t
  SELECT 5999856 AS a,5999856E0 AS b,5999856.0 AS c;

DESCRIBE t;

+-------+--------------+------+-----+---------+-------+
| Field | Type         | Null | Key | Default | Extra |
+-------+--------------+------+-----+---------+-------+
| a     | int(7)       | NO   |     | 0       |       |
| b     | double       | NO   |     | 0       |       |
| c     | decimal(8,1) | NO   |     | 0.0     |       |
+-------+--------------+------+-----+---------+-------+
3 rows in set (0.01 sec)
```

参考：[精密計算の例](https://web.archive.org/web/20140802154248/http://dev.mysql.com/doc/refman/5.1/ja/precision-math-examples.html)

### 電卓

#### Windows 95以前

[Windows 95以前の電卓](https://web.archive.org/web/20090919003424/http://support.microsoft.com/kb/124345)がWindows 7上でそのまま動くということには感心しますが、a=5999856, b=99992800, c=100000000として、a^3+b^3-c^3を計算すると答えは0になります。

#### Mac OS X 10.4以前

上で紹介した問題は、Mac OS Xでは10.4（2005年）の計算機 4.0.6までは修正されていませんでした（PowerPC版で確認。正確に言えば、基本モードと科学計算モードで結果が異なり、しかも、両方とも間違っていました）。常にWindowsの先を行っていると言われているらしいMac OS Xですが、基本的な計算についてはかなり遅れていたといわざるを得ません。

iPhoneでも3.1までこの問題が残っていました（正確に言えば、間違い方が違うので、「反例」にはなりません）。「端末を回転させると関数電卓になる」というのもネタとしてはいいのですが、そんなことよりはちゃんと計算できることを優先してほしいです。

電卓を作るのもなかなか大変です。

#### 補足

Windows 98SE以降の電卓ではこの間違いは起こりません。そう、Windowsの電卓はちゃんと進化しているんです。「Windows 95以来、スタートメニューのアクセサリには、電卓が含まれていた。その機能はVistaまでほとんど変わることなく続いてきた」（[マイコミジャーナル](https://web.archive.org/web/20110907042551/http://journal.mycom.co.jp:80/articles/2009/10/02/windows7soft/index.html)）というのは間違いです。

<blockquote class="twitter-tweet" data-lang="ja"><p lang="ja" dir="ltr">ファインマンがギャフンとなったという「10の100乗のタンジェント」。Windows 7, 8のcalc.exeでは計算できたが，Windows 10の電卓ではできない。Androidの電卓はOK。OS XとiOSの計算機は当然ながらNG。</p>&mdash; Taro YABUKI (@yabuki) <a href="https://twitter.com/yabuki/status/721341400382504961?ref_src=twsrc%5Etfw">2016年4月16日</a></blockquote>
<script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script>

ちなみに、Unixの標準的な電卓である[GNU bc](https://www.gnu.org/software/bc/manual/bc.html)は、最初からこんな間違いは犯しません。1994年に配布された[bc 1.03](https://ftp.gnu.org/pub/gnu/bc/)で試すと次のようになります（あえて古いバージョンで試しています）。

```bash
sudo apt-get install flex bison
wget https://ftp.gnu.org/pub/gnu/bc/bc-1.03.tar.gz
tar zxf bc-1.03.tar.gz
cd bc-1.03/
./configure
make
./bc
```

```
bc 1.03 (Nov 2, 1994)
Copyright (C) 1991, 1992, 1993, 1994 Free Software Foundation, Inc.
This is free software with ABSOLUTELY NO WARRANTY.
For details type `warranty'.
5999856^3+99992800^3-100000000^3
-2985984
```

同様に、UBASICも間違えません。（参考：[UBASICの動かし方](/2012/07/12/ubasic/)）

```
? 5999856^3+99992800^3-100000000^3
-2985984
OK
```

参考：[電卓の話](https://nyaruru.hatenablog.com/entry/20091004/p1)

追記：[電卓に求められるコト](https://taroyabuki.github.io/2009/10/19/requirements-for-a-good-calculator/)

### Excel

![](/images/2015-02-02-fermat-excel.png)

`=A1^3+B1^3-C1^3`の結果が0になっています。Excel 2007, 2008だけでなく、OpenOffice Calc 3.0RC1, gnumeric 1.8.2, KPsread 1.6.3, Google Docs BETA等でもこうなります。

表計算ソフトの利用者の大部分は、コンピュータが初等的な計算において間違いを犯すとは思ってはいないのではないでしょうか。

参考：[Excel 2007に乗算のバグが発見される](https://srad.jp/story/07/09/26/1218253/)（解決済み）