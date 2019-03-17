above(rower, olowek).
above(aparat, motyl).
left_of(olowek, klepsydra).
left_of(klepsydra, motyl).
left_of(motyl, ryba).
left_of(aparat, ryba).

right_of(Object1, Object2) :- left_of(Object2, Object1).
below(Object1, Object2) :- above(Object2, Object1).

left_left_of(Object1, Object2) :- left_of(Object1, Object2).
left_left_of(Object1, Object2) :-
    left_of(Object1, X),
    left_left_of(X, Object2).
	
above_above(Object1, Object2) :- above(Object1, Object2).
above_above(Object1, Object2) :-
    above(Object1, X),
    above_above(X, Object2).

higher(Object1, Object2) :- above_above(Object1, Object2).
higher(Object1, Object2) :-
    (left_of_left(Object2, X), above_above(Object1, X));
    (left_of_left(X, Object2), above_above(Object1, X));
	(above_above(Object1, X)).
	

% https://www.doc.gold.ac.uk/~mas02gw/prolog_tutorial/prologpages/recursion.html