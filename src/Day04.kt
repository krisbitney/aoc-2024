enum class Direction {
    LEFT,
    RIGHT,
    UP,
    DOWN,
    UPLEFT,
    UPRIGHT,
    DOWNRIGHT,
    DOWNLEFT;

    fun next(i: Int, j: Int): Pair<Int, Int> =
        when (this) {
            LEFT -> i - 1 to j
            RIGHT -> i + 1 to j
            UP -> i to j + 1
            DOWN -> i to j - 1
            UPLEFT -> i - 1 to j + 1
            UPRIGHT -> i + 1 to j + 1
            DOWNRIGHT -> i + 1 to j - 1
            DOWNLEFT -> i - 1 to j - 1
        }
}

fun main() {

    fun isValidLocation(matrix: List<CharArray>, i: Int, j: Int): Boolean =
        i >= 0 && j >= 0 && i < matrix.size && j < matrix[i].size

    fun countXMas(matrix: List<CharArray>, i: Int, j: Int): Int {
        if (matrix[i][j] != 'X') return 0
        var count = 0
        for (direction in Direction.entries) {
            // check M
            var next = direction.next(i, j)
            if (!isValidLocation(matrix, next.first, next.second)) continue
            if (matrix[next.first][next.second] != 'M') continue
            // check A
            next = direction.next(next.first, next.second)
            if (!isValidLocation(matrix, next.first, next.second)) continue
            if (matrix[next.first][next.second] != 'A') continue
            // check S
            next = direction.next(next.first, next.second)
            if (!isValidLocation(matrix, next.first, next.second)) continue
            if (matrix[next.first][next.second] != 'S') continue
            // found one!
            count++
        }
        return count
    }

    fun countCrossMas(matrix: List<CharArray>, i: Int, j: Int): Int {
        if (matrix[i][j] != 'A') return 0
        if (
            !isValidLocation(matrix, i - 1, j - 1) ||
                !isValidLocation(matrix, i + 1, j + 1) ||
                !isValidLocation(matrix, i + 1, j - 1) ||
                !isValidLocation(matrix, i - 1, j + 1)
        ) {
            return 0
        }
        if (matrix[i - 1][j - 1] == 'M' && matrix[i + 1][j + 1] == 'S') {
            if (
                matrix[i + 1][j - 1] == 'M' && matrix[i - 1][j + 1] == 'S' ||
                    matrix[i + 1][j - 1] == 'S' && matrix[i - 1][j + 1] == 'M'
            ) {
                return 1
            }
        }
        if (matrix[i - 1][j - 1] == 'S' && matrix[i + 1][j + 1] == 'M') {
            if (
                matrix[i + 1][j - 1] == 'M' && matrix[i - 1][j + 1] == 'S' ||
                    matrix[i + 1][j - 1] == 'S' && matrix[i - 1][j + 1] == 'M'
            ) {
                return 1
            }
        }
        return 0
    }

    fun part1(input: List<String>): Int {
        var count = 0
        val matrix = input.map { it.toCharArray() }
        for (i in 0 until matrix.size) {
            for (j in 0 until matrix[i].size) {
                count += countXMas(matrix, i, j)
            }
        }
        return count
    }

    fun part2(input: List<String>): Int {
        var count = 0
        val matrix = input.map { it.toCharArray() }
        for (i in 0 until matrix.size) {
            for (j in 0 until matrix[i].size) {
                count += countCrossMas(matrix, i, j)
            }
        }
        return count
    }

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
