function findShortest(vectors) {
  var shortest = null;
  var shortestIndex = null;
  for (var i = 0; i < vectors.length; i++) {
    var v = vectors[i];
    var l = Math.sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]);
    if (shortest === null || shortest > l) {
      shortest = l;
      shortestIndex = i
    }
  }
  return vectors[shortestIndex];
}

var vectors = [[1, 1, 1], [2, 2, 2], [3, 3, 3]];
var shortest = findShortest(vectors);
// Expected output: 1,1,1
alert(shortest);