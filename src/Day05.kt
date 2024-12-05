fun main() {

    fun parseInput(input: List<String>): Pair<List<List<String>>, List<List<String>>> {
        val rules = mutableListOf<String>()
        val updates = mutableListOf<String>()
        var emptyLineSeen = false
        for (line in input) {
            if (emptyLineSeen) {
                updates.add(line)
            } else if (line == "") {
                emptyLineSeen = true
            } else {
                rules.add(line)
            }
        }
        val parsedRules = rules.map { it.split("|") }
        val parsedUpdates = updates.map { it.split(",") }
        return parsedRules to parsedUpdates
    }

    fun triageUpdates(
        rules: List<List<String>>,
        updates: List<List<String>>,
    ): Pair<List<List<String>>, List<List<String>>> {
        val incorrectUpdates = mutableListOf<List<String>>()
        var correctUpdates = updates
        rules.forEach { rule ->
            correctUpdates =
                correctUpdates.map { update ->
                    var i = 0
                    var j = update.lastIndex
                    while (true) {
                        if (i < update.size && update[i] != rule[0]) {
                            i++
                        } else if (j >= 0 && update[j] != rule[1]) {
                            j--
                        } else {
                            break
                        }
                    }
                    if (i < update.size && j >= 0 && i > j) {
                        incorrectUpdates.add(update)
                        return@map listOf<String>()
                    }
                    return@map update
                }
        }
        return correctUpdates to incorrectUpdates
    }

    fun fixUpdate(rules: List<List<String>>, update: MutableList<String>): List<String> {
        var isFixed = false
        while (!isFixed) {
            isFixed = true
            rules.forEach { rule ->
                var i = 0
                var j = update.lastIndex
                while (true) {
                    if (i < update.size && update[i] != rule[0]) {
                        i++
                    } else if (j >= 0 && update[j] != rule[1]) {
                        j--
                    } else {
                        break
                    }
                }
                if (i < update.size && j >= 0 && i > j) {
                    val temp = update[i]
                    update[i] = update[j]
                    update[j] = temp
                    isFixed = false
                }
            }
        }
        return update
    }

    fun part1(input: List<String>): Int {
        val (rules, updates) = parseInput(input)
        val (correctUpdates, _) = triageUpdates(rules, updates)
        return correctUpdates.sumOf { if (it.isEmpty()) 0 else it[it.lastIndex / 2].toInt() }
    }

    fun part2(input: List<String>): Int {
        val (rules, updates) = parseInput(input)
        val (_, incorrectUpdates) = triageUpdates(rules, updates)
        return incorrectUpdates.sumOf {
            val fixed = fixUpdate(rules, it.toMutableList())
            return@sumOf fixed[fixed.lastIndex / 2].toInt()
        }
    }

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
