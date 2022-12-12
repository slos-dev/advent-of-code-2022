fun main() {
    fun moveOnce(pos: Pair<Int, Int>, char: Char): Pair<Int, Int> {
        var result: Pair<Int, Int>
        when (char) {
            'R' -> result = Pair(pos.first, pos.second + 1)
            'L' -> result = Pair(pos.first, pos.second - 1)
            'U' -> result = Pair(pos.first + 1, pos.second)
            'D' -> result = Pair(pos.first - 1, pos.second)
            else -> {
                println("hello")
                result = Pair(0,0)
            }
        }
        return result
    }
    fun moveTail(head: Pair<Int, Int>, tail: Pair<Int, Int>): Pair<Int, Int> {
        val colDiff = head.second - tail.second
        val rowDiff = head.first - tail.first
        if (kotlin.math.abs(rowDiff) < 2 && kotlin.math.abs(colDiff) < 2 ){
            return tail
        }
        var result: Pair<Int, Int>
        when (Pair(rowDiff, colDiff)) {
            Pair(-2, 0) -> result = moveOnce(tail, 'D')
            Pair(-2, 1), Pair(-2, 2) -> {
                result = moveOnce(tail, 'D')
                result = moveOnce(result, 'R')
            }
            Pair(-2, -1), Pair(-2, -2) ->{
                result = moveOnce(tail, 'D')
                result = moveOnce(result, 'L')
            }
            Pair(2, 0) -> result = moveOnce(tail, 'U')
            Pair(2, -1), Pair(2, -2) -> {
                result = moveOnce(tail, 'U')
                result = moveOnce(result, 'L')
            }
            Pair(2, 1), Pair(2, 2) -> {
                result = moveOnce(tail, 'U')
                result = moveOnce(result, 'R')
            }
            Pair(0, -2) -> result = moveOnce(tail, 'L')
            Pair(-1, -2), Pair(-2, -2) -> {
                result = moveOnce(tail, 'L')
                result = moveOnce(result, 'D')
            }
            Pair(1, -2), Pair(2, -2) -> {
                result = moveOnce(tail, 'L')
                result = moveOnce(result, 'U')
            }
            Pair(0, 2) -> result = moveOnce(tail, 'R')
            Pair(-1, 2), Pair(-2, 2) -> {
                result = moveOnce(tail, 'R')
                result = moveOnce(result, 'D')
            }
            Pair(1, 2), Pair(2, 2) -> {
                result = moveOnce(tail, 'R')
                result = moveOnce(result, 'U')
            }
            else -> {
                println("hello 4 $rowDiff, $colDiff")
                result = Pair(0, 0)
            }
        }
        return result
    }
    fun part1(input: List<String>): Int {
        var result: Int
        var h = Pair(0, 0)
        var t = Pair(0, 0)
        val visited = mutableSetOf<Pair<Int,Int>>()
        visited.add(t)
        for(line in input) {
            val sp = line.split(" ")
            val char = sp[0][0]
            val times = sp[1].toInt()
            for(i in 1..times) {
                h = moveOnce(h, char)
                t = moveTail(h, t)
                visited.add(t)
            }
        }
        result = visited.count()
        return result
    }

    fun part2(input: List<String>): Int {
        var result: Int
        var rope = Array(10){Pair<Int,Int>(0,0)}
        //head = rope[0], tail = rope[9]
        val visited = mutableSetOf<Pair<Int,Int>>()
        visited.add(rope[9])
        for(line in input) {
            val sp = line.split(" ")
            val char = sp[0][0]
            val times = sp[1].toInt()
            for(i in 1..times) {
                rope[0] = moveOnce(rope[0], char)
                for(j in 1..9) {
                    rope[j] = moveTail(rope[j-1], rope[j])
                }
                visited.add(rope[9])
            }
        }
        result = visited.count()
        return result    
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 88)
    check(part2(testInput) == 36)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
