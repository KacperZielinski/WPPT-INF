jest_matk�(X) :- kobieta(X), matka(X, Y), X \= Y.
jest_ojcem(X) :- m�czyzna(X), ojciec(X, Y), X \= Y.
jest_synem(X) :-  m�czyzna(X), rodzic(Y, X), X \= Y.
siostra(X, Y) :- kobieta(X), rodzic(Y, X), rodzic(Y, Z), X \= Y, X \=Z, Y \= Z.
dziadek(X, Y) :- m�czyzna(X), ojciec(X, Z), ojciec(Z, Y), X \= Y, X \=Z, Y \=Z.
rodze�stwo(X, Y) :- rodzic(Z, X), rodzic(Z, Y), X \= Y, X\=Z, Y \=Z.