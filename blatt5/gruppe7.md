
true = \t.\f.t
false = \t.\f.f

if = \cond.\then.\else.cond then else

and = \a.\b.a b false
or = \a.\b.a true b
not = \a.a false true

c0 = \f.\s.s
c1 = \f.\s.f s
c2 = \f.\s.f (f s)
c3 = \f.\s.f (f (f s))

succ = \n.\f.\s.(n f (f s))
isEven = \n.n not true
isZero = \n.n (\_.false) true

pair = \a.\b.\f.f a b

fst = \p.p (\a.\b.a)
snd = \p.p (\a.\b.b)

next = \p.pair (snd p) (succ (snd p))
pred = \n.fst (n next (pair c0 c0))

add = \n.\m.n succ m
sub = \n.\m.m pred n
mul = \n.\m.n (add m) c0
exp = \n.\m.\s.\z.m n s z

equals = \n.\m.and (isZero (sub n m)) (isZero (sub m n))

equals c1 c2
