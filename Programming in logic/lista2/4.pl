ma(kacper, wiedza).
ma(kacper, samochod).
ma(andrzej, samochod).
ma(andrzej, drzewo).
ma(andrzej, widelec).

daje(10, andrzej, samochod, kacper).
daje(20, andrzej, widelec, kacper).

ma(0, Kto, Co) :-
	ma(Kto, Co).
	
daje
	
ma(Kiedy, Kto, Co) :-
	daje(Kiedy, _, Co, Kto),
	