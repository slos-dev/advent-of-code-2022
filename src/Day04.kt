fun main() {
    fun part1(input: List<String>): Int {
        var result: Int = 0
        for (str in input) {
            val range = str
                .split(",")
                .map{
                    it.split("-").map{
                        it.toInt()
                    }.toList()
                }
                .toList();
            if(range[0][0] >= range[1][0] && range[0][1]<=range[1][1]) result++
            else if(range[1][0] >= range[0][0] && range[1][1]<=range[0][1]) result++
        }
        return result
    }

    fun part2(input: List<String>): Int {
        var result: Int = 0
        for (str in input) {
            val range = str
                .split(",")
                .map{
                    it.split("-").map{
                        it.toInt()
                    }.toList()
                }
                .toList();
            if(range[0][0] <= range[1][0] && range[0][1]>=range[1][0]) result++
            else if(range[0][0] <= range[1][1] && range[0][1]>=range[1][1]) result++
            else if(range[1][0] <= range[0][0] && range[1][1]>=range[0][0]) result++
            else if(range[1][0] <= range[0][1] && range[1][1]>=range[0][1]) result++
        }
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
