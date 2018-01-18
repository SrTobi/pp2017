
indexOf(E, [E|_], 1).
indexOf(E, [_|LS], N) :-
    indexOf(E, LS, NewN),
    N is NewN + 1.

solution(S):-
    S = [[_,_,_],[_,_,_],[_,_,_],[_,_,_],[_,_,_]],
    clue1(S),
    clue2(S),
    clue3(S),
    clue4(S),
    clue5(S),
    clue6(S),
    clue7(S),
    clue8(S),
    clue9(S),
    clue10(S),
	clue11(S),
	clue12(S).

clue1(S) :-
    indexOf([_, _, glr], S, GlrN),
    indexOf([_, csharp, _], S, CSharpN),
    2 =:= abs(GlrN - CSharpN).

clue2(S) :- 
    indexOf([jonas, _, _], S, JonasN),
    indexOf([_, pseudo, _], S, PseudoN),
    JonasN =:= PseudoN - 1.

clue3(S) :-
    indexOf([henning, _, _], S, HenningN),
    indexOf([_, elm, _], S, ElmN),
    2 =:= abs(HenningN - ElmN).

clue4(S) :- 
    indexOf([tobias, _, _], S, TobiasN),
    indexOf([_, _, appendList], S, AppN),
    AppN =:= TobiasN + 1.

clue5(S) :-
    indexOf([daniel, _, _], S, DanielN),
    indexOf([_, _, packrat], S, PackratN),
    2 =:= abs(DanielN - PackratN).

clue6(S) :-
    indexOf([jonas, _, _], S, JonasN),
    indexOf([_, type, _], S, TypeN),
    3 =:= abs(JonasN - TypeN).

clue7(S) :- 
    indexOf([henning, _, _], S, NameN),
    indexOf([_, pseudo, _], S, ProgN),
    ProgN > NameN.

clue8(S) :- 
    indexOf([_, _, bogosort], S, BogoSortN),
    indexOf([_, _, euklid], S, EuklidN),
    BogoSortN =:= EuklidN - 1.


clue9(S) :- 
    indexOf([_, type, packrat], S, _).

clue10(S) :-
    indexOf([jonas, _, _], S, JonasN),
    indexOf([henning, _, _], S, HenningN),
    3 =:= abs(JonasN - HenningN).
    
clue11(S) :-
    indexOf([daniel, _, _], S, DanielN),
    indexOf([roman, _, _], S, RomanN),
    2 =:= abs(DanielN - RomanN).
    
clue12(S) :-
    indexOf([_, scala, _], S, _).
    
    
    