osiagalny(A,B) :-
	sciezka(A,B,[]). 

sciezka(A,B,V) :- 
	arc(A,X) ,
	not(member(X,V)) ,
	(
		B = X 
	;
		sciezka(X,B,[A|V]) 
	).

arc(a, b).
arc(b, a).
arc(b, c).
arc(c, d).