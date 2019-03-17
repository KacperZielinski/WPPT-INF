ope(X, Y, Z, '+') :- Z is X + Y.
ope(X, Y, Z, '-') :- Z is X - Y.
ope(X, Y, Z, '*') :- Z is X * Y.
ope(X, Y, Z, '/') :- not(Y = 0), not(Y = 1), A is X mod Y, A = 0, Z is X // Y.

tree([], _, _) :- !, fail.

tree([X|[]], X, X).

tree(X, W, Value) :-
    append(L, R, X),
    L \= X, tree(L, TLeft, Value1),	% not (L=X)
    R \= X, tree(R, TRight, Value2),
    ope(Value1, Value2, Value, Op),
	W =.. [Op, TLeft, TRight].
