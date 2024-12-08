fun main() {

    data class Point(val x: Int, val y: Int)

    fun isValidLocation(matrix: List<CharArray>, point: Point): Boolean =
        point.x >= 0 && point.y >= 0 && point.x < matrix.size && point.y < matrix[point.x].size

    fun maybeAnodeLocation(i: Int, j: Int, k: Int, l: Int): Pair<Point, Point> {
        var x0 = if (i >= k) i + (i - k) else i - (k - i)
        var y0 = if (j >= l) j + (j - l) else j - (l - j)
        var x1 = if (i >= k) k - (i - k) else k + (k - i)
        var y1 = if (j >= l) l - (j - l) else l + (l - j)
        return Point(x0, y0) to Point(x1, y1)
    }

    fun part1(input: List<String>): Int {
        val matrix = input.map { it.toCharArray() }
        val markers = List<IntArray>(matrix.size) { IntArray(matrix[it].size) { 0 } }
        for (i in 0 until matrix.size) {
            for (j in 0 until matrix[i].size) {
                if (matrix[i][j] == '.') continue
                for (k in 0 until matrix.size) {
                    for (l in 0 until matrix[k].size) {
                        if (i == k && j == l) continue
                        if (matrix[i][j] == matrix[k][l]) {
                            val (point0, point1) = maybeAnodeLocation(i, j, k, l)
                            if (isValidLocation(matrix, point0)) {
                                markers[point0.x][point0.y] = 1
                            }
                            if (isValidLocation(matrix, point1)) {
                                markers[point1.x][point1.y] = 1
                            }
                        }
                    }
                }
            }
        }
        return markers.sumOf { it.sum() }
    }

    fun part2(input: List<String>): Int {
        val matrix = input.map { it.toCharArray() }
        val markers = List<IntArray>(matrix.size) { IntArray(matrix[it].size) { 0 } }
        for (i in 0 until matrix.size) {
            for (j in 0 until matrix[i].size) {
                if (matrix[i][j] == '.' || matrix[i][j] == '#') continue
                for (k in 0 until matrix.size) {
                    for (l in 0 until matrix[k].size) {
                        if (i == k && j == l) continue
                        if (matrix[i][j] == matrix[k][l]) {
                            markers[i][j] = 1
                            markers[k][l] = 1
                            val initialAnodes = maybeAnodeLocation(i, j, k, l)
                            var point0 = initialAnodes.first
                            var point1 = initialAnodes.second
                            var tempI = i
                            var tempJ = j
                            var tempK = k
                            var tempL = l
                            while (isValidLocation(matrix, point0)) {
                                markers[point0.x][point0.y] = 1
                                tempK = tempI
                                tempL = tempJ
                                tempI = point0.x
                                tempJ = point0.y
                                point0 = maybeAnodeLocation(tempI, tempJ, tempK, tempL).first
                            }

                            tempI = i
                            tempJ = j
                            tempK = k
                            tempL = l
                            point0 = initialAnodes.first
                            point1 = initialAnodes.second
                            while (isValidLocation(matrix, point1)) {
                                markers[point1.x][point1.y] = 1
                                tempI = tempK
                                tempJ = tempL
                                tempK = point1.x
                                tempL = point1.y
                                point1 = maybeAnodeLocation(tempI, tempJ, tempK, tempL).second
                            }
                        }
                    }
                }
            }
        }
        return markers.sumOf { it.sum() }
    }

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
