import kotlin.math.abs

fun main() {

    fun toNumericLists(input: List<String>): List<List<Int>> =
        input.map { it.split(" ").map { it.toInt() } }

    fun dropItemFromList(list: List<Int>, i: Int): List<Int> {
        return (list.subList(0, i) + list.subList(i + 1, list.size))
    }

    fun isReportSafe(it: List<Int>): Int {
        if (it.size < 2) {
            return 1
        }
        if (it[0] == it[1]) {
            return 0
        }
        if (abs(it[0] - it[1]) > 3) {
            return 0
        }
        val increasing = it[1] > it[0]
        for (i in 1 until it.size - 1) {
            if (increasing && it[i] >= it[i + 1]) {
                return 0
            } else if (!increasing && it[i] <= it[i + 1]) {
                return 0
            } else if (abs(it[i] - it[i + 1]) > 3) {
                return 0
            }
        }
        return 1
    }

    fun part1(input: List<String>): Int {
        return toNumericLists(input).sumOf(::isReportSafe)
    }

    fun part2(input: List<String>): Int {
        return toNumericLists(input)
            .map {
                if (isReportSafe(it) == 0) {
                    for (i in 0 until it.size) {
                        if (isReportSafe(dropItemFromList(it, i)) == 1) {
                            return@map 1
                        }
                    }
                    return@map 0
                }
                return@map 1
            }
            .sum()
    }

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
