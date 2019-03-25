"use strict";
function gcd(a, b) {
    a = Math.abs(a);
    b = Math.abs(b);
    if (a === 0) {
        if (b === 0) {
            return Infinity;
        } else {
            return b;
        }
    }
    while (b !== 0) {
        if (a > b) {
            a = a - b;
        } else {
            b = b - a;
        }
    }
    return a;
}