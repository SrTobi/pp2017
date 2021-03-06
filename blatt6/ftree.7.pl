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

rel(childInLawOf, X, Y) :- married(Y, Z), naturalChildOf(X, Z), not(naturalChildOf(X, Y)).
rel(childInLawOf, X, Y) :- married(X, Z), naturalChildOf(Z, Y), not(naturalChildOf(X, Y)).
rel(childInLawOf, X, Y) :- married(X, Z1), naturalChildOf(Z1, Z2), married(Z2, Y), not(naturalChildOf(X, Y)).

rel(childOf, X, Y) :- childInLawOf(X, Y).
rel(childOf, X, Y) :- naturalChildOf(X, Y).

rel(grandChildOf, X, Y) :- childOf(X, Z), childOf(Z, Y).


% parent
rel(parentOf, X, Y) :- childOf(Y, X).
rel(grandParentOf, X, Y) :- grandChildOf(Y, X).

% sibling
rel(sibling, X, Y) :- parentOf(Z, X), parentOf(Z, Y), X \= Y.

% cousin
rel(cousin, X, Y) :- parentOf(Z1, X), parentOf(Z2, Y), sibling(Z1, Z2), X \= Y.

% Auncle
rel(auncleOf, X, Y) :- parentOf(Z, X), sibling(Z, Y), not(childOf(X, Y)).


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
