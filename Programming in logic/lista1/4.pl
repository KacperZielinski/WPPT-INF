le(0,0).
le(1,1).
le(1,2).
le(2,2).
le(2,3).
le(3,3).
le(3,4).

maksymalny(X) :-
    \+ (le(X,Z) , X\=Z).
najwiêkszy(X) :-
    \+ le(X,_).
minimalny(X) :-
    \+ (le(Z,X) , X\=Z).
najmniejszy(X) :-
    \+ le(_,X).