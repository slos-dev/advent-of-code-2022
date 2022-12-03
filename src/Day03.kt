fun main() {
    fun score(char: Char): Int {
        if (char >= 'a' && char <= 'z') return char - 'a' + 1
        return char - 'A' + 27
    }
    fun populate(line: String, set: MutableSet<Char>) {
            val len = line.length
            var ix = 0
            while (ix < len ) {
                set.add(line[ix])
                ix++
            }
    }
    fun part1(input: List<String>): Int {
        var result: Int = 0
        for(str in input) {
            var first = mutableSetOf<Char>()
            val len = str.length
            var ix = 0
            while (ix < (len / 2) ) {
                first.add(str[ix])
                ix++
            }
            ix = len / 2
            var found = false
            while (ix < len && !found) {
                if (first.contains(str[ix])) {
                    found = true
                    result += score(str[ix])
                }
                ix++
            }
        }
        return result
    }

    fun part2(input: List<String>): Int {
        var result: Int = 0
        val lines = input.size
        var lix = 0;
        while (lix < lines) {
            val first = mutableSetOf<Char>()
            val second = mutableSetOf<Char>()
            populate(input[lix], first)
            lix++
            populate(input[lix], second)
            lix++
            val len = input[lix].length
            val str = input[lix]
            lix++
            var found = false
            var ix = 0
            while (ix < len && !found) {
                if (first.contains(str[ix]) && second.contains(str[ix])) {
                    found = true
                    result += score(str[ix])
                }
                ix++
            }
        }
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
