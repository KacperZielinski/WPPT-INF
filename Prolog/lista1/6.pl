divisible(X,Y):-
	N is Y*Y,
	N =< X,
	X mod Y =:= 0.

divisible(X,Y):-
	Y < X,
	Z is Y+1,
	divisible(X, Z).

isPrime(X):-
Y is 2, X > 1, \+divisible(X,Y).
		
prime(LO, HI, N) :-
	between(LO, HI, N),
	isPrime(N).
