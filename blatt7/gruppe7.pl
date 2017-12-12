%(Mann,Ziege,Wolf,Kohl)

ufer(links).
ufer(rechts).

erlaubt((M, M, _, _)) :- !.
erlaubt((M, _, M, M)).

opposite(rechts, links).
opposite(links, rechts).

fahrt((X, Ziege, Wolf, Kohl), leer, (X2, Ziege, Wolf, Kohl)) :- opposite(X, X2).
fahrt((X, X, Wolf, Kohl), ziege, (X2, X2, Wolf, Kohl)) :- opposite(X, X2).
fahrt((X, Ziege, X, Kohl), wolf, (X2, Ziege, X2, Kohl)) :- opposite(X, X2).
fahrt((X, Ziege, Wolf, X), kohl, (X2, Ziege, Wolf, X2)) :- opposite(X, X2).

lösung(Fahrten) :- start(S), ziel(Z), erreichbar(S,[],Fahrten,Z).
start((links,links,links,links)).
ziel((rechts,rechts,rechts,rechts)).

erreichbar(S, _, [], S).
erreichbar(S,Besucht, FahrtenRes, Z) :-
    fahrt(S, Fahrt, Temp),
    erlaubt(Temp),
    not(member(Temp, Besucht)),
    erreichbar(Temp, [S | Besucht], Fahrten, Z),
    append([Fahrt], Fahrten, FahrtenRes).



% node(A, B)
% leaf(4)
% node(leaf(1), node(leaf(2), leaf(3)))

gleich(node(X, X)).



mirror(X, X) :- X = leaf(_).
mirror(node(A, B), node(C, D)) :-
    mirror(A, D),
    mirror(B, C).



height(leaf(_), 0).
height(node(A, B), Res) :-
    height(A, HA),
    height(B, HB),
    Res is 1 + max(HA, HB).

tree(0, leaf(X)).
tree(H, T) :-
    SubH is H - 1,
    SubH >= 0,
    tree(SubH, T).
tree(H, node(SubTreeA, SubTreeB)) :-
    SubH is H - 1,
    SubH >= 0,
    tree(SubH, SubTreeA),
    tree(SubH, SubTreeB).
