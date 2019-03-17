x = suma(1, 123, 500, 115, 44, 88);

function suma() {
    "use strict";
    var i;
    var sum = 0;
    for (i = 0; i < arguments.length; i+=1) {
        sum += arguments[i];
    }
    return sum;
}