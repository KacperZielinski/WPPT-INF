even_permutation(Xs, Ys):-
  permutation(Xs, Ys),
  sign(Xs, 1, D),
  sign(Ys, 1, E),
  D = E.

odd_permutation(Xs, Ys):-
  permutation(Xs, Ys),
  sign(Xs, 1, D),
  sign(Ys, 1, E),
  D =\= E.
  
sign([], D, D).
sign([Y|Xs], A, D):-
  sign_pom(Xs, Y, A, B),
  sign(Xs, B, D).

sign_pom([], _, D, D).
sign_pom([X|Xs], Y, A, D) :-
  Y =\= X,
  B is A * (Y - X) / abs(Y - X),  
  sign_pom(Xs, Y, B, D).