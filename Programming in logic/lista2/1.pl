usun_ostatni(L, L1) :-
    append(L1, [_], L).
usun_pierwszy([_|L], L).

srodkowy(L, X) :-
    usun_pierwszy(L, L1),
	usun_ostatni(L1, L2),
    (   L2 = [X]
    ->  true
    ;   srodkowy(L2, X)
    ).