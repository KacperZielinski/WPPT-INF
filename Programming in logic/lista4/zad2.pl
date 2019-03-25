exists(A, list(A, _, _, _, _)).
exists(A, list(_, A, _, _, _)).
exists(A, list(_, _, A, _, _)).
exists(A, list(_, _, _, A, _)).
exists(A, list(_, _, _, _, A)).

left_of(L, R, list(L, R, _, _, _)).
left_of(L, R, list(_, L, R, _, _)).
left_of(L, R, list(_, _, L, R, _)).
left_of(L, R, list(_, _, _, L, R)).

middle(A, list(_, _, A, _, _)).

first(A, list(A, _, _, _, _)).

next_to(A, B, list(B, A, _, _, _)).
next_to(A, B, list(_, B, A, _, _)).
next_to(A, B, list(_, _, B, A, _)).
next_to(A, B, list(_, _, _, B, A)).
next_to(A, B, list(A, B, _, _, _)).
next_to(A, B, list(_, A, B, _, _)).
next_to(A, B, list(_, _, A, B, _)).
next_to(A, B, list(_, _, _, A, B)).

puzzle(Houses, FishOwner) :-
	first(			house(_,			norwegian,		_,			_,			_),	Houses),
	exists(		house(red,		english,  			_,     	_,    		_),	Houses),
	left_of(		house(white,			_,       			_,     	_,     	_),	house(green,_,_,_,_), Houses),
	exists(		house(_,      		danish,   			tea,  	_,     	_),	Houses),
	next_to(	house(_,      			_,        			_,    		_,     light),	house(_,_,_,cat,_), Houses),
	exists(		house(yellow, 		_,        			_,     	_,     cigar),	Houses),
	exists(		house(_,      german,   				_,     	_,     pipe),  Houses),
	middle(		house(_,      			_,        		milk,  		_,     _), Houses),
	next_to(	house(_,      			_,        			_,     	_,     light), house(_,_,water,_,_), Houses),
	exists(		house(_,      			_,        			_,     bird,  nofilter),  Houses),
	exists(		house(_,      swedish,  				_,     dog,   		_),  Houses),
	next_to(	house(_,      norwegian,			_,     	_,     	_),  house(blue,_,_,_,_), Houses),
	next_to(	house(_,      			_,        			_,     horse, 		_),  house(yellow,_,_,_,_), Houses),
	exists(		house(_,      			_,        		beer,  		_,  menthol),   Houses),
	exists(		house(green,  		_,        coffee,			_,     	_), Houses),
	exists(		house(_,      FishOwner,      		_,     fish,  		_), Houses).

rybki(Who) :-
    puzzle(Houses, Who),
    exists(house(_, Who, _, fish, _), Houses).
