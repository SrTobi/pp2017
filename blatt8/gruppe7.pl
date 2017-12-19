% C = (P + S) mod 256

shifted(S, P, C) :- integer(S), integer(P), !, C is (P + S) mod 256.
shifted(S, P, C) :- integer(S), integer(C), !, P is (C - S) mod 256.
shifted(S, P, C) :- integer(P), integer(C), !, S is (C - P) mod 256.
shifted(_, _, _).


caesar(_, [], []).
caesar(S, [P|Pr], [C|Cr]) :- shifted(S, P, C), caesar(S, Pr, Cr).

vigenere(_, [], []).
vigenere([S|Sr], [P|Pr], [C|Cr]) :-
    shifted(S, P, C),
    append(Sr, [S], Snew),
    vigenere(Snew, Pr, Cr).

subseq(S, L) :- append(_, Y, L), append(S, _, Y).

chifrat([140,146,95,96,96,80,170,152,97,81,115,171,82,164,153,
    156,83,168,146,171,94,80,165,159,152,81,164,147,159,149,81,
    167,165,160,148,151,150,165,163,156,83,146,164,82,158,145,
    164,171,83,170,150,147,164,92,81,132,156,164,164,82,133,
    159,161,159,156,150,112,82,95,80,133,159,152,81,164,147,
    159,149,81,167,165,160,148,151,150,165,163,156,83,146,
    164,82,151,166,150,169,172,81,170,151,147,162,81,129,148,
    158,150,165,83,80,94,87,138,150,157,158,94,80,122,94,159,
    157,81,150,161,80,158,176,83,167,150,164,171,80,
    147,156,166,165,82]).

decrypt(K, X) :-
    chifrat(C),
    length(K, 8),
    subseq("procedure", X),
    vigenere(K, X, C),
    vigenere(K, Msg, C),
    writef("Schluessel: %s\nPlaintext: %s\n", [K, Msg]).



% delete([X|L],X,L).
% delete([X|L],Y,[X|R]) :- delete(L,Y,R).


remove([(Coin, Amount)|Coins], C, [(Coin, Amount)|CoinsRes]) :-
    remove(Coins, C, CoinsRes).

remove([(Coin, Amount)|Coins], Coin, [(Coin, NewAmount)|Coins]) :-
    Amount > 0,
    NewAmount is Amount - 1.

put([], C, [(C, 1)]).
put([(Coin, Amount)|Coins], Coin, [(Coin, NewAmount)|Coins]) :-
    NewAmount is Amount + 1,
    !.
put([(Coin, Amount)|Coins], C, [(Coin, Amount)|CoinsRes]) :-
    put(Coins, C, CoinsRes).


change(0, _, []).
change(X, Av, ChangeNew) :-
    remove(Av, Coin, NewAv),
    NewX is X - Coin,
    NewX >= 0,
    change(NewX, NewAv, Change),
    put(Change, Coin, ChangeNew).

changeNoDups(0, _, []).
changeNoDups(X, Av, [Coin,HeadChange|Change]) :-
    remove(Av, Coin, NewAv),
    NewX is X - Coin,
    NewX >= 0,
    change(NewX, NewAv, [HeadChange|Change]),
    Coin >= HeadChange.
