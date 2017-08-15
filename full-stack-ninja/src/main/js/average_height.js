function calcAvgHeight(data) {
    var acc = 0
    var i = 0
    for (var k in data) {
        acc += data[k].height
        i++
    }
    return i == 0 ? null : acc / i
}

var avgHeight = calcAvgHeight({
    Matt: { height: 174, weight: 69 },
    Jason: { height: 190, weight: 103 }
});

alert(avgHeight);