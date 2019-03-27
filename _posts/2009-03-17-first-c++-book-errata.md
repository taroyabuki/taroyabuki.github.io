---
date: 2009-03-17 06:47:13+00:00
title: 『Microsoft Visual C++入門』の誤り
---

拙著[『Microsoft Visual C++入門』](http://www.unfindable.net/~yabuki/blog/2009/03/microsoft_visual_c.html)に誤りがありました。申し訳ありません。

P. 196の09-vector3.cppの最後から4行目（塩澤さん、ありがとう）

    誤：for (it=vec.begin(); it!=vec.end(); ++it) cout<<it<<' ';
    正：for (it=vec.begin(); it!=vec.end(); ++it) cout<<*it<<' ';

P. 233で紹介している関数ポインタの型名の定義

    誤：typedef 引数の型 (*関数ポインタの型名) (関数のパラメータリスト);
    正：typedef 戻り値の型 (*関数ポインタの型名) (関数のパラメータリスト);