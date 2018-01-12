
% info(TypContext, LambdaAusdruck, Typ von LambdaAusdruck)
inf(_, true, bool) :- !.
inf(_, false, bool) :- !.
inf(_, Number, int) :- number(Number), !.

inf(Context, v(VarName), T) :-
    T = Context.VarName.

inf(Context, app(Func, Arg), T) :-
    inf(Context, Func, FuncT),
    inf(Context, Arg, ArgT),
    FuncT = f(ArgT, T).

inf(Context, abs(VarName, Body), T) :-
    NewContext = Context.put(VarName, VarT),
    inf(NewContext, Body, BodyT),
    T = f(VarT, BodyT).


% Beispielanfrage
% inf(context{}, abs(x, abs(y, app(v(y), app(v(x), v(y))))), T).