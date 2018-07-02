;不完全性定理のLispによる記述


;準備

;;Emacs Lispのための準備（describeはxyzzyにも必要。しかし以下の話ではfunctionpがちゃんと動かない）

(defun describe (x)
  (progn 
    (princ "name: ")
    (prin1 (symbol-name x))
    (princ "\nvalue: ")
    (prin1 (if (boundp x) (symbol-value x) 'void))
    (princ "\nplist: ")
    (prin1 (symbol-plist x))
    (princ "\nfunction: ")
    (prin1 (if (functionp x) (symbol-function x) 'void))
    (print (symbolp x))
))

;;Common Lispのための準備

(defun fset (a b) (setf (symbol-function a) b))


;自分のコードを出力するプログラムの例

(setq f '(lambda (x) `(,x ',x)))

(describe 'f)

(fset 'f f)

(describe 'f)

(f 'x)

(f f)

(eval (f f))

(equal (f f) (eval (f f)))


;ゲーデルの定理

;;エピメニデスのパラドックス：「この文は偽である」

;;ゲーデルの定理：「この命題は証明できない」（真なのに証明できない命題がある）

(setq g '(lambda (x) `(is-unprovable (value-of (,x ',x)))))

(fset 'g g)

(g 'x)

(g g)

(eval (cadr (cadr (g g))))

(equal (g g) (eval (cadr (cadr (g g)))))


;停止問題の解決不可能性についてのチューリングの証明

;;ラッセルのパラドックス：自分の髭を剃らない村中すべての男の髭を剃る床屋

;;チューリングの定理：任意のプログラムが停止するかどうかを判断できるようなアルゴリズムは存在しない

;;止まらないとした場合

(setq turing
      '(lambda (x)
         ((lambda (haltsQ)
           ((lambda (z) (if (funcall haltsQ z) (eval z) nil))
            (print `(,x ',x))))
          '(lambda (sExp) nil))))

(fset 'turing turing)

(turing turing)

;;止まるとした場合

(setq turing
      '(lambda (x)
         ((lambda (haltsQ)
           ((lambda (z) (if (funcall haltsQ z) (eval z) nil))
            `(,x ',x)))
          '(lambda (sExp) t))))

(fset 'turing turing)

(turing turing)

;;次のように書けば自己限定的

((lambda (x) ((lambda (haltsQ) ((lambda (z) (if (funcall haltsQ z) (eval z) nil)) (print (\` ((\, x) (quote (\, x))))))) (quote (lambda (sExp) nil)))) (quote (lambda (x) ((lambda (haltsQ) ((lambda (z) (if (funcall haltsQ z) (eval z) nil)) (print (\` ((\, x) (quote (\, x))))))) (quote (lambda (sExp) nil))))))


;LISP式がエレガントであることを証明できないというチャイティンの証明

;;ベリーのパラドックス（ラッセル版）：20文字以下で記述できない最初の自然数（19字）
;;チャイティンの定理：公理の計算量+Nよりも大きい計算量を持つLispの式のエレガント性は証明できない

(defun size (s) (length (prin1-to-string s)))

;;パラドックスなし

(setq ex '(lambda ()
  (progn
    (setq fas '(lambda (n)
                 (cond ((= n 1) '(is-elegant x))
                       ((= n 2) nil)
                       ((= n 3) '(is-elegant yyy))
                       (t 'stop))))
    (fset 'fas fas)
    (defun repeat (n)
     (let ((theorem (print (fas n))))
       (progn
         (if (equal nil theorem) (repeat (+ n 1))
           (if (equal theorem 'stop) 'fas-has-stopped
             (if (equal (car theorem) 'is-elegant)
                 (if (> (print (size (cadr theorem))) (print (size ex)))
                     (eval (cadr theorem))
                   (repeat (+ n 1)))
               (repeat (+ n 1))))))))
    (repeat 1))))

(funcall ex)

(defun long-string (n) (concat (string ?1) (make-string n ?0)))

;;ベリーのパラドックス

(setq ex '(lambda ()
  (progn
    (setq fas '(lambda (n)
                 (cond ((= n 1) '(is-elegant x))
                       ((= n 2) nil)
                       ((= n 3) '(is-elegant yyy))
                       ((= n 4) (cons 'is-elegant (cons (long-string 575) nil)))
                       (t 'stop))))
    (fset 'fas fas)
    (defun repeat (n)
     (let ((theorem (print (fas n))))
       (progn
         (if (equal nil theorem) (repeat (+ n 1))
           (if (equal theorem 'stop) 'fas-has-stopped
             (if (equal (car theorem) 'is-elegant)
                 (if (> (print (size (cadr theorem))) (print (size ex)))
                     (eval (cadr theorem))
                   (repeat (+ n 1)))
               (repeat (+ n 1))))))))
    (repeat 1))))

(funcall ex)
