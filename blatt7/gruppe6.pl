% (Mann,Ziege,Wolf,Kohl)

ufer(links).
ufer(rechts).

mirror(links, rechts).
mirror(rechts, links).

erlaubt((X, X, Y, Z)) :- ufer(X), ufer(Y), ufer(Z), !.
erlaubt((X, Y, X, X)) :- ufer(X), ufer(Y).


fahrt((X, Ziege, Wolf, Kohl), leer, (X2, Ziege, Wolf, Kohl)) :- mirror(X, X2).
fahrt((X, X, Wolf, Kohl), ziege, (X2, X2, Wolf, Kohl)) :- mirror(X, X2).
fahrt((X, Ziege, X, Kohl), wolf, (X2, Ziege, X2, Kohl)) :- mirror(X, X2).
fahrt((X, Ziege, Wolf, X), kohl, (X2, Ziege, Wolf, X2)) :- mirror(X, X2).

lösung(Fahrten) :- start(S), ziel(Z), erreichbar(S,[],Fahrten,Z).
start((links,links,links,links)).
ziel((rechts,rechts,rechts,rechts)).

erreichbar(Z, _, [], Z).
erreichbar(S, Besucht, [Fahrt|Fahrten],Z) :-
    BesuchtMitS = [S|Besucht],
    fahrt(S, Fahrt, Tmp),
    erlaubt(Tmp),
    not(member(Tmp, BesuchtMitS)),
    erreichbar(Tmp, BesuchtMitS, Fahrten, Z).




% node(A, B)
% leaf(3)
% node(leaf(3), node(leaf(2), leaf(1)))

gleich(node(A, A)).

mirrorTree(leaf(Z), leaf(Z)).
mirrorTree(node(A, B), node(C, D)) :-
    mirrorTree(A, D),
    mirrorTree(B, C).

height(leaf(_), 0).
height(node(A, B), X) :-
    height(A, HA),
    height(B, HB),
    X is 1 + max(HA, HB).

summe(leaf(A), A).
summe(node(A, B), Res) :-
    summe(A, ResA),
    summe(B, ResB),
    Res is ResA + ResB.


matches (u(A, B), S) :- matches