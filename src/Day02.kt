fun main() {
    fun score(me: Char?, ot: Char): Int {
        var result: Int = 0
        if (me == 'A') result += 1
        else if (me == 'B') result += 2
        else if (me == 'C') result +=3

        if (me == ot) result += 3
        else if ( me == 'A' && ot == 'C' ) result += 6
        else if ( me == 'B' && ot == 'A' ) result += 6
        else if ( me == 'C' && ot == 'B' ) result += 6
        return result
    } 
    fun part1(input: List<String>): Int {
        var result: Int = 0
        val map1 = mapOf('X' to 'A', 'Y' to 'B', 'Z' to 'C')
        for (line in input) {
            if (line.length < 3) continue
            result += score(map1[line[2]], line[0])
        }
        return result
    }

    fun part2(input: List<String>): Int {
        var result: Int = 0
        //A Rock, B Paper, C Scissor
        val mapw = mapOf('A' to 'B', 'B' to 'C', 'C' to 'A')
        val mapl = mapOf('A' to 'C', 'B' to 'A', 'C' to 'B')
        for (line in input) {
            if (line.length < 3) continue
            var me: Char?
            if (line[2] == 'X') me = mapl[line[0]]
            else if(line[2] =='Y') me = line[0]
            else me = mapw[line[0]]
            result += score(me, line[0])
        }
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
