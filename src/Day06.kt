enum class Guard(val char: Char) {
    LEFT('<'),
    UP('^'),
    RIGHT('>'),
    DOWN('v');

    fun peek(i: Int, j: Int): Pair<Int, Int> =
        when (this) {
            LEFT -> i to j - 1
            UP -> i - 1 to j
            RIGHT -> i to j + 1
            DOWN -> i + 1 to j
        }

    fun turn(): Guard =
        when (this) {
            LEFT -> UP
            UP -> RIGHT
            RIGHT -> DOWN
            DOWN -> LEFT
        }
}

fun main() {

    fun findStartPos(matrix: List<CharArray>): Triple<Guard, Int, Int> {
        for (i in 0 until matrix.size) {
            for (j in 0 until matrix[i].size) {
                if (matrix[i][j] == Guard.UP.char) {
                    return Triple(Guard.UP, i, j)
                }
                if (matrix[i][j] == Guard.RIGHT.char) {
                    return Triple(Guard.RIGHT, i, j)
                }
                if (matrix[i][j] == Guard.DOWN.char) {
                    return Triple(Guard.DOWN, i, j)
                }
                if (matrix[i][j] == Guard.LEFT.char) {
                    return Triple(Guard.LEFT, i, j)
                }
            }
        }
        throw Exception("Failed to find starting position")
    }

    fun isValidLocation(matrix: List<CharArray>, i: Int, j: Int): Boolean =
        i >= 0 && j >= 0 && i < matrix.size && j < matrix[i].size

    fun explore(matrix: List<CharArray>, guard: Guard, i: Int, j: Int): Triple<Guard, Int, Int>? {
        val (k, l) = guard.peek(i, j)
        if (!isValidLocation(matrix, k, l)) {
            return null
        } else if (matrix[k][l] == '.') {
            return Triple(guard, k, l)
        } else if (matrix[k][l] == '#') {
            return Triple(guard.turn(), i, j)
        } else {
            return null
        }
    }

    fun part1(input: List<String>): Int {
        val matrix = input.map { it.toCharArray() }
        val markers = List<IntArray>(matrix.size) { IntArray(matrix[it].size) { 0 } }
        var (guard, i, j) = findStartPos(matrix)
        matrix[i][j] = '.'
        while (true) {
            markers[i][j] = 1
            val (nextGuard, nextI, nextJ) = explore(matrix, guard, i, j) ?: break
            guard = nextGuard
            i = nextI
            j = nextJ
        }

        // view map of patrol route for gut check
        markers.forEach { println(it.joinToString("")) }

        return markers.sumOf { it.sum() }
    }

    fun part2(input: List<String>): Int {
        val matrix = input.map { it.toCharArray() }
        val startPos = findStartPos(matrix)
        matrix[startPos.second][startPos.third] = '.'
        var count = 0
        for (m in 0 until matrix.size) {
            for (n in 0 until matrix[m].size) {
                if (matrix[m][n] != '.' || m == startPos.second && n == startPos.third) {
                    continue
                }
                matrix[m][n] = '#'

                val markers = List<IntArray>(matrix.size) { IntArray(matrix[it].size) { 0 } }
                var (guard, i, j) = startPos
                while (true) {
                    // check if in loop
                    if (markers[i][j] > 4) {
                        count++
                        break
                    }
                    markers[i][j]++

                    val (nextGuard, nextI, nextJ) = explore(matrix, guard, i, j) ?: break
                    guard = nextGuard
                    i = nextI
                    j = nextJ
                }

                matrix[m][n] = '.'
            }
        }

        return count
    }

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
