fun main() {

    fun blink(stone: Long, n: Int, cache: Map<Int, MutableMap<Long, Long>>): Long {
        if (n == 0) {
            return 1L
        }

        val nCache = cache[n]!!
        return nCache.getOrPut(stone) {
            // rule 1
            if (stone == 0L) {
                return blink(1L, n - 1, cache)
            }

            // rule 2
            val stoneStr = stone.toString()
            if (stoneStr.length % 2 == 0) {
                val half = stoneStr.length / 2
                return blink(stoneStr.substring(0, half).toLong(), n - 1, cache) +
                    blink(stoneStr.substring(half).toLong(), n - 1, cache)
            }

            // rule 3
            return blink(stone * 2024L, n - 1, cache)
        }
    }

    fun part1(input: List<String>): Long {
        val n = 25
        val cache: MutableMap<Int, MutableMap<Long, Long>> = HashMap(n)
        (1..n).forEach { cache[it] = mutableMapOf() }
        return input[0].split(" ").sumOf { blink(it.toLong(), n, cache) }
    }

    fun part2(input: List<String>): Long {
        val n = 75
        val cache: MutableMap<Int, MutableMap<Long, Long>> = HashMap(n)
        (1..n).forEach { cache[it] = mutableMapOf() }
        return input[0].split(" ").sumOf { blink(it.toLong(), n, cache) }
    }

    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}
