fun main() {
    fun addChar(char: Char, map: MutableMap<Char,Int>) {
        val upd = map.getOrDefault(char, 0) + 1
        map[char] = upd
    }
    fun remChar(char: Char, map: MutableMap<Char,Int>) {
        val upd = map.getOrDefault(char, 1) - 1
        if (upd == 0) {
            map -= char
        }else{
            map[char] = upd
        }
    }
    fun part1(input: List<String>): Int {
        var result: Int = 0
        for(line in input) {
            val charMap = mutableMapOf<Char,Int>()
            for((ix, char) in line.withIndex()) {
                addChar(char, charMap)
                if(ix>=4) remChar(line[ix-4], charMap)
                if(charMap.count() ==4) {
                    result = ix + 1
                    break
                }               
            }
            println("result: $result")
        }
        return result
    }

    fun part2(input: List<String>): Int {
        var result: Int = 0
        for(line in input) {
            val charMap = mutableMapOf<Char,Int>()
            for((ix, char) in line.withIndex()) {
                addChar(char, charMap)
                if(ix>=14) remChar(line[ix-14], charMap)
                if(charMap.count() ==14) {
                    result = ix + 1
                    break
                }               
            }
            println("result: $result")
        }
        return result    
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 26)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
