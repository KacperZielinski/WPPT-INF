jest_matk¹(X) :- kobieta(X), matka(X, Y), X \= Y.
jest_ojcem(X) :- mê¿czyzna(X), ojciec(X, Y), X \= Y.
jest_synem(X) :-  mê¿czyzna(X), rodzic(Y, X), X \= Y.
siostra(X, Y) :- kobieta(X), rodzic(Y, X), rodzic(Y, Z), X \= Y, X \=Z, Y \= Z.
dziadek(X, Y) :- mê¿czyzna(X), ojciec(X, Z), ojciec(Z, Y), X \= Y, X \=Z, Y \=Z.
rodzeñstwo(X, Y) :- rodzic(Z, X), rodzic(Z, Y), X \= Y, X\=Z, Y \=Z.