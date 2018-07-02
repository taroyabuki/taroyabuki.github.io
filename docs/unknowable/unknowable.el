;�s���S���藝��Lisp�ɂ��L�q


;����

;;Emacs Lisp�̂��߂̏����idescribe��xyzzy�ɂ��K�v�B�������ȉ��̘b�ł�functionp�������Ɠ����Ȃ��j

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

;;Common Lisp�̂��߂̏���

(defun fset (a b) (setf (symbol-function a) b))


;�����̃R�[�h���o�͂���v���O�����̗�

(setq f '(lambda (x) `(,x ',x)))

(describe 'f)

(fset 'f f)

(describe 'f)

(f 'x)

(f f)

(eval (f f))

(equal (f f) (eval (f f)))


;�Q�[�f���̒藝

;;�G�s���j�f�X�̃p���h�b�N�X�F�u���̕��͋U�ł���v

;;�Q�[�f���̒藝�F�u���̖���͏ؖ��ł��Ȃ��v�i�^�Ȃ̂ɏؖ��ł��Ȃ����肪����j

(setq g '(lambda (x) `(is-unprovable (value-of (,x ',x)))))

(fset 'g g)

(g 'x)

(g g)

(eval (cadr (cadr (g g))))

(equal (g g) (eval (cadr (cadr (g g)))))


;��~���̉����s�\���ɂ��Ẵ`���[�����O�̏ؖ�

;;���b�Z���̃p���h�b�N�X�F�����̕E����Ȃ��������ׂĂ̒j�̕E���鏰��

;;�`���[�����O�̒藝�F�C�ӂ̃v���O��������~���邩�ǂ����𔻒f�ł���悤�ȃA���S���Y���͑��݂��Ȃ�

;;�~�܂�Ȃ��Ƃ����ꍇ

(setq turing
      '(lambda (x)
         ((lambda (haltsQ)
           ((lambda (z) (if (funcall haltsQ z) (eval z) nil))
            (print `(,x ',x))))
          '(lambda (sExp) nil))))

(fset 'turing turing)

(turing turing)

;;�~�܂�Ƃ����ꍇ

(setq turing
      '(lambda (x)
         ((lambda (haltsQ)
           ((lambda (z) (if (funcall haltsQ z) (eval z) nil))
            `(,x ',x)))
          '(lambda (sExp) t))))

(fset 'turing turing)

(turing turing)

;;���̂悤�ɏ����Ύ��Ȍ���I

((lambda (x) ((lambda (haltsQ) ((lambda (z) (if (funcall haltsQ z) (eval z) nil)) (print (\` ((\, x) (quote (\, x))))))) (quote (lambda (sExp) nil)))) (quote (lambda (x) ((lambda (haltsQ) ((lambda (z) (if (funcall haltsQ z) (eval z) nil)) (print (\` ((\, x) (quote (\, x))))))) (quote (lambda (sExp) nil))))))


;LISP�����G���K���g�ł��邱�Ƃ��ؖ��ł��Ȃ��Ƃ����`���C�e�B���̏ؖ�

;;�x���[�̃p���h�b�N�X�i���b�Z���Łj�F20�����ȉ��ŋL�q�ł��Ȃ��ŏ��̎��R���i19���j
;;�`���C�e�B���̒藝�F�����̌v�Z��+N�����傫���v�Z�ʂ�����Lisp�̎��̃G���K���g���͏ؖ��ł��Ȃ�

(defun size (s) (length (prin1-to-string s)))

;;�p���h�b�N�X�Ȃ�

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

;;�x���[�̃p���h�b�N�X

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
