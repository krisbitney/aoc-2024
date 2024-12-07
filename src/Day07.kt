fun main() {

    fun parseEquation(equation: String): Pair<Long, List<Long>> {
        val answerAndValues = equation.split(": ")
        val answer: Long = answerAndValues[0].toLong()
        val values: List<Long> = answerAndValues[1].split(" ").map { it.toLong() }
        return answer to values
    }

    fun concat(left: Long, right: Long): Long = (left.toString() + right.toString()).toLong()

    fun tryAllOperators(
        answer: Long,
        values: List<Long>,
        currentIndex: Int,
        result: Long,
        answers: MutableList<Long>,
        withConcat: Boolean,
    ) {
        if (currentIndex >= values.size) {
            answers.add(result)
            return
        }
        val nextValue = values[currentIndex]
        tryAllOperators(answer, values, currentIndex + 1, result + nextValue, answers, withConcat)
        tryAllOperators(answer, values, currentIndex + 1, result * nextValue, answers, withConcat)
        if (withConcat) {
            tryAllOperators(
                answer,
                values,
                currentIndex + 1,
                concat(result, nextValue),
                answers,
                true,
            )
        }
    }

    fun part1(input: List<String>): Long {
        return input.map(::parseEquation).sumOf {
            val (answer, values) = it
            val allResults = mutableListOf<Long>()
            tryAllOperators(answer, values, 1, values[0], allResults, false)
            return@sumOf if (allResults.contains(answer)) {
                answer
            } else {
                0
            }
        }
    }

    fun part2(input: List<String>): Long {
        return input.map(::parseEquation).sumOf {
            val (answer, values) = it
            val allResults = mutableListOf<Long>()
            tryAllOperators(answer, values, 1, values[0], allResults, true)
            return@sumOf if (allResults.contains(answer)) {
                answer
            } else {
                0
            }
        }
    }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
