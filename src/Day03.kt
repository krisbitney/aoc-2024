fun main() {

    val mulRegex = """^mul\((\d+),(\d+)\)$""".toRegex()

    fun executeMul(instruction: String): Int {
        val (left, right) =
            mulRegex.matchEntire(instruction)?.destructured
                ?: throw Exception("Invalid instruction: $instruction")
        return left.toInt() * right.toInt()
    }

    fun part1(input: List<String>): Int {
        val inputRegex = """(mul\(\d+,\d+\))""".toRegex()
        val fullInputString = input.joinToString("")
        return inputRegex.findAll(fullInputString).sumOf { match -> executeMul(match.value) }
    }

    fun part2(input: List<String>): Int {
        val inputRegex = """(mul\(\d+,\d+\)|do\(\)|don't\(\))""".toRegex()
        val fullInputString = input.joinToString("")
        var enabled = true
        var sum = 0
        inputRegex.findAll(fullInputString).forEach { match ->
            if (match.value == "do()") {
                enabled = true
            } else if (match.value == "don't()") {
                enabled = false
            } else if (enabled) {
                sum += executeMul(match.value)
            }
        }
        return sum
    }

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
