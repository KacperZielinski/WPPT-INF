max_sum(L, S) :-
    max_sum(L, 0, 0, S).

max_sum([], _, S, S).
max_sum([X | L], H, F, S) :-
    NewH is max(0, H + X),
    (F < NewH -> NewF is NewH; NewF is F),
    max_sum(L, NewH, NewF, S).