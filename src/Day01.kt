import kotlin.math.abs

fun main() {

    fun prepareLists(input: List<String>): Pair<MutableList<Int>, MutableList<Int>> {
        val left = mutableListOf<Int>()
        val right = mutableListOf<Int>()
        return input
            .map { it.split("   ") }
            .fold(left to right) { accum, element ->
                accum.first.add(element[0].toInt())
                accum.second.add(element[1].toInt())
                return accum
            }
    }

    fun part1(input: List<String>): Int {
        val (left, right) = prepareLists(input)
        left.sort()
        right.sort()
        return left.foldIndexed(0) { i, sum, element -> sum + abs(element - right[i]) }
    }

    fun part2(input: List<String>): Int {
        val (left, right) = prepareLists(input)
        val counts = right.groupingBy { it }.eachCount()
        return left.fold(0) { sum, element -> sum + element * (counts[element] ?: 0) }
    }

    //    // Test if implementation meets criteria from the description, like:
    //    check(part1(listOf("test_input")) == 1)
    //
    //    // Or read a large test input from the `src/Day01_test.txt` file:
    //    val testInput = readInput("Day01_test")
    //    check(part1(testInput) == 1)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
