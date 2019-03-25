jednokrotnie(L, X) :-
	select(X, L, L1),
	\+ member(X, L1).
  
 dwukrotnie(L, X) :-
	select(X, L, L1),
	 select(X, L1, L2),
	\+ member(X, L2).
  
