srednia(L, Avg) :-
    length(L, X),
    suma_pom(L, S),
    Avg is S/X .

suma_pom([], 0).
suma_pom([H|T], S) :-
    suma_pom(T, S1),
    S is H + S1.

suma([], _, 0).
suma([H|T], Avg,  S) :-
    suma(T, Avg, S1),
    S is (H-Avg)**2 + S1.
	
wariancja(L, D) :-
    srednia(L, Avg),
    length(L, X),
    suma(L, Avg, Y),
    D is Y/X.
