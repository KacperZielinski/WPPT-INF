function egcd(a, b) {
    "use strict";
    a = Math.abs(a);
    b = Math.abs(b);
    if (a === 0) {
        if (b === 0) {
            return [Infinity, 0, 0];
        } else {
            return [0, 0, 0];
        }
    }
    var x = 1;
    var y = 0;
    var r = 0;
    var s = 1;
    var c;
    var r1;
    var s1;
    var q;

    while (b !== 0) {
        c = a % b;
        q = a / b;
        a = b;
        b = c;

        r1 = r;
        s1 = s;
        r = x - (q * r);
        s = y - (q * s);
        x = r1;
        y = s1;
    }
    return [a, x, y];
}