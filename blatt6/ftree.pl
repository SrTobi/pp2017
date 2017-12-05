% married
defrel(married, 'Me', 'Widow').
defrel(married, 'My Father', 'Step Daughter').

% children
defrel(naturalChildOf, 'Me', 'My Father').
defrel(naturalChildOf, 'My Son', 'Me').
defrel(naturalChildOf, 'My Son', 'Widow').
defrel(naturalChildOf, 'Step Daughter', 'Widow').
defrel(naturalChildOf, 'Their Son', 'Step Daughter').
defrel(naturalChildOf, 'Their Son', 'My Father').

person('Me').
person('My Father').
person('My Son').
person('Widow').
person('Step Daughter').
person('Their Son').

% married
rel(married, X, Y) :- defrel(married, X, Y).
rel(married, X, Y) :- defrel(married, Y, X).

% chidren
rel(naturalChildOf, X, Y) :- defrel(naturalChildOf, X, Y).
rel(childInLawOf, X, Y) :- naturalChildOf(X, Z), married(Z, Y), not(naturalChildOf(X, Y)).
rel(childInLawOf, X, Y) :- married(X, Z), naturalChildOf(Z, Y), not(naturalChildOf(X, Y)).
rel(childInLawOf, X, Y) :- married(X, Z1), naturalChildOf(Z1, Z2), married(Z2, Y), not(naturalChildOf(X, Y)).
rel(childOf, X, Y) :- naturalChildOf(X, Y).
rel(childOf, X, Y) :- childInLawOf(X, Y).
rel(grandChildOf, X, Y) :- childOf(X, Z), childOf(Z, Y).


% parent
rel(parentOf, X, Y) :- childOf(Y, X).
rel(grandParentOf, X, Y) :- grandChildOf(Y, X).

% sibling
rel(sibling, X, Y) :- naturalChildOf(X, Z), naturalChildOf(Y, Z), X \= Y.

% cousin
rel(cousin, X, Y) :- childOf(X, Z1), childOf(Y, Z2), X \= Y, Z1 \= Z2, childOf(Z1, Z), childOf(Z2, Z), not(sibling(X, Y)).

% Auncle
rel(auncleOf, X, Y) :- sibling(X, Z), parentOf(Z, Y).


married(X, Y) :- rel(married, X, Y).
naturalChildOf(X, Y) :- rel(naturalChildOf, X, Y).
childInLawOf(X, Y) :- rel(childInLawOf, X, Y).
childOf(X, Y) :- rel(childOf, X, Y).
grandChildOf(X, Y) :- rel(grandChildOf, X, Y).
parentOf(X, Y) :- rel(parentOf, X, Y).
grandParentOf(X, Y) :- rel(grandParentOf, X, Y).
sibling(X, Y) :- rel(sibling, X, Y).
cousin(X, Y) :- rel(cousin, X, Y).
auncleOf(X, Y) :- rel(auncleOf, X, Y).
