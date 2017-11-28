
true = \a.\b.a
false = \a.\b.b

if = \cond.\then.\else. cond then else

and = \a.\b.a b false
or = \a.\b.a true b
not = \a.a false true

c0 = \f.\a.a
c1 = \f.\a.f a
c2 = \f.\a.f (f a)
c3 = \f.\a.f (f (f a))

succ = \n.\f.\a.f (n f a)
isZero = \n.n (\a.false) true

pair = \a.\b.\f.f a b

fst = \p.p (\a.\b.a)
snd = \p.p (\a.\b.b)

next = \p.pair (snd p) (succ (snd p))

pred = \n.fst (n next (pair c0 c0))

add = \n.\m.n succ m
sub = \n.\m.m pred n
mul = \n.\m.m (add n) c0
exp = \n.\m.\s.\z.m n s z

equals = \n.\m.and (isZero (sub n m)) (isZero (sub m n))

equals c3 c3

