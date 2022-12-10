import java.util.Arrays

fun main() {
    fun part1(input: List<String>): Long {
        var result: Long = 0
        var cycles: Int = 0
        var value: Long = 1
        var ix: Int = 0
        while(cycles < 220) {
            when {
                input[ix].startsWith("noop") -> {
                    cycles++
                    if (((cycles-20) % 40) == 0) {
                        result += (value * cycles)
                    }
                }                
                else -> {
                    val prev = value
                    val reg = input[ix].split(" ").last().toLong()
                    value += reg
                    cycles += 2
                    if (((cycles-20) % 40) == 1) {
                        result += (prev * (cycles-1))
                    }
                    if (((cycles-20) % 40) == 0) {
                        result += (prev * cycles)
                    }
                }
            }
            ix++
        }
        return result
    }

    fun draw(cursor: Int, spritePos: Int): Char {
        if (cursor >= spritePos && cursor < (spritePos+3)) return '#'
        return '.'
    }

    fun part2(input: List<String>): Long {
        var result: Long = 0
        var spritePos: Int = 0
        var cursor = 0
        var cycles = 0
        var printStr = ""
        var ix = 0
        while(cycles < 240) {
            when {
                input[ix].startsWith("noop") -> {
                    printStr += draw(cursor, spritePos)
                    cursor++
                    cycles++
                }                
                else -> {
                    printStr += draw(cursor, spritePos)
                    cursor++
                    if(cursor == 40) cursor = 0

                    printStr += draw(cursor, spritePos)
                    cursor++

                    val reg = input[ix].split(" ").last().toInt()
                    spritePos += reg
                    cycles += 2
                }
            }
            if (cursor == 40) cursor = 0
            ix++
        }
        printStr.chunked(40).map {
            println(it.toString())
        }
        return result    
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 13140L)
    check(part2(testInput) == 0L)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
