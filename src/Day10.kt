enum class TrailDirection {
    LEFT,
    RIGHT,
    UP,
    DOWN;

    fun opposite(): TrailDirection =
        when (this) {
            LEFT -> RIGHT
            RIGHT -> LEFT
            UP -> DOWN
            DOWN -> UP
        }
}

fun main() {

    data class Point(val x: Int, val y: Int) {
        fun isValidLocation(matrix: List<List<Int>>): Boolean =
            x >= 0 && y >= 0 && x < matrix.size && y < matrix[x].size

        fun step(direction: TrailDirection): Point =
            when (direction) {
                TrailDirection.UP -> Point(x - 1, y)
                TrailDirection.LEFT -> Point(x, y - 1)
                TrailDirection.RIGHT -> Point(x, y + 1)
                TrailDirection.DOWN -> Point(x + 1, y)
            }
    }

    fun explore(
        matrix: List<List<Int>>,
        pos: Point,
        trails: MutableList<List<Point>>,
        currentTrail: MutableList<Point>,
        lastDirection: TrailDirection?,
    ) {
        val elevation = matrix[pos.x][pos.y]
        if (elevation == 9) {
            trails.add(currentTrail)
            return
        }

        for (direction in TrailDirection.entries) {
            if (direction == lastDirection?.opposite()) continue
            val maybeNextPos = pos.step(direction)
            if (!maybeNextPos.isValidLocation(matrix)) continue
            if (matrix[maybeNextPos.x][maybeNextPos.y] == elevation + 1) {
                val nextTrail = (currentTrail + listOf(maybeNextPos)).toMutableList()
                explore(matrix, maybeNextPos, trails, nextTrail, direction)
            }
        }
    }

    fun part1(input: List<String>): Int {
        val matrix: List<List<Int>> = input.map { row -> row.toCharArray().map { it.digitToInt() } }
        val trails: MutableList<List<Point>> = mutableListOf()

        for (i in 0 until matrix.size) {
            for (j in 0 until matrix[i].size) {
                if (matrix[i][j] == 0) {
                    val start = Point(i, j)
                    explore(matrix, start, trails, mutableListOf(start), null)
                }
            }
        }

        // print paths for debugging
        //        for (trail in trails.slice(0 until 5)) {
        //            val markers = List<IntArray>(matrix.size) { IntArray(matrix[it].size) { 0 } }
        //            for (pos in trail) {
        //                markers[pos.x][pos.y] = 1
        //            }
        //            markers.forEach { println(it.joinToString("")) }
        //            println()
        //        }

        val score = mutableSetOf<Pair<Point, Point>>()
        trails.forEach { score.add(it[0] to it[it.lastIndex]) }
        return score.size
    }

    fun part2(input: List<String>): Int {
        val matrix: List<List<Int>> = input.map { row -> row.toCharArray().map { it.digitToInt() } }
        val trails: MutableList<List<Point>> = mutableListOf()

        for (i in 0 until matrix.size) {
            for (j in 0 until matrix[i].size) {
                if (matrix[i][j] == 0) {
                    val start = Point(i, j)
                    explore(matrix, start, trails, mutableListOf(start), null)
                }
            }
        }

        return trails.size
    }

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
