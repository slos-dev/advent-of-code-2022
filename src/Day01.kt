fun main() {
    fun part1(input: List<String>): Long {
        var result: Long = 0
        var curr: Long = 0
        for(value in input) {
            if (value.length == 0) {
                if (curr > result) {
                    result = curr
                }
                curr = 0
            } else {
                val parsed: Long = value.toLong()
                curr += parsed
            }
        }
        if(curr > result) {
            result = curr
        }
        return result
    }

    fun part2(input: List<String>): Long {
        val inter = mutableListOf<Long>()
        var curr: Long = 0
        for(value in input) {
            if (value.length == 0) {
                inter.add(curr)
                curr = 0
            } else {
                val parsed: Long = value.toLong()
                curr += parsed
            }
        }
        if (curr > 0) {
            inter.add(curr)
        }
        val result: Long = inter
            .sortedDescending()
            .foldIndexed(0L) { idx, sum, element -> if (idx < 3) sum + element else sum }
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000L)
    check(part2(testInput) == 45000L)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
