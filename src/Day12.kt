import java.util.Arrays

fun findStart(input: MutableList<String>, dist: Array<Array<Int>>, queue: ArrayDeque<Pair<Int,Int>>) {
    for(i in input.indices)
    for(j in input[0].indices) {
        if (input[i][j] == 'S') {
            val row = input[i].toCharArray()
            row[j]='a'
            input[i] = String(row)
            dist[i][j] = 1
            queue.add(Pair(i,j))
            return
        }
    }
    println("hello 1")
}
fun findStart2(input: MutableList<String>, dist: Array<Array<Int>>, queue: ArrayDeque<Pair<Int,Int>>) {
    for(i in input.indices)
    for(j in input[0].indices) {
        if (input[i][j] == 'S' || input[i][j] == 'a') {
            val row = input[i].toCharArray()
            row[j]='a'
            input[i] = String(row)
            dist[i][j] = 1
            queue.add(Pair(i,j))
        }
    }
}
fun findLast(input: MutableList<String>): Pair<Int, Int> {
    for(i in input.indices)
    for(j in input[0].indices) {
        if (input[i][j] == 'E') {
            val row = input[i].toCharArray()
            row[j]='z'
            input[i] = String(row)
            return Pair(i,j)
        }
    }
    println("hello 2")
    return Pair(-1,-1)
}
fun check(cr:Int, cc:Int, nr: Int, nc: Int, input: List<String>, dist: Array<Array<Int>>, queue: ArrayDeque<Pair<Int,Int>>){
    if (input[nr][nc] - input[cr][cc] >=2 ) {
        return
    }
    if(dist[nr][nc]!=0) return
    dist[nr][nc] = dist[cr][cc] + 1
    queue.add(Pair(nr,nc))
}
fun main() {
    fun part1(inputImm: List<String>): Int {
        val input = inputImm.toMutableList()
        val rowCount = input.count()
        val colCount = input[0].count()
        val dist = Array(rowCount){Array(colCount){0}}
        val queue = ArrayDeque<Pair<Int,Int>>()
        findStart(input, dist, queue)
        val end = findLast(input)
        while (queue.count() > 0) {
            val pos = queue.removeFirst()
            if (pos.first - 1 >= 0) check(pos.first, pos.second, pos.first - 1, pos.second, input, dist, queue)
            if (pos.first + 1 < rowCount) check(pos.first, pos.second, pos.first + 1, pos.second, input, dist, queue)
            if (pos.second - 1 >= 0) check(pos.first, pos.second, pos.first, pos.second - 1, input, dist, queue)
            if (pos.second + 1 < colCount) check(pos.first, pos.second, pos.first, pos.second + 1, input, dist, queue)
        }
        var result: Int = dist[end.first][end.second] -1 
        return result
    }

    fun part2(inputImm: List<String>): Int {
        val input = inputImm.toMutableList()
        val rowCount = input.count()
        val colCount = input[0].count()
        val dist = Array(rowCount){Array(colCount){0}}
        val queue = ArrayDeque<Pair<Int,Int>>()
        findStart2(input, dist, queue)
        val end = findLast(input)
        while (queue.count() > 0) {
            val pos = queue.removeFirst()
            if (pos.first - 1 >= 0) check(pos.first, pos.second, pos.first - 1, pos.second, input, dist, queue)
            if (pos.first + 1 < rowCount) check(pos.first, pos.second, pos.first + 1, pos.second, input, dist, queue)
            if (pos.second - 1 >= 0) check(pos.first, pos.second, pos.first, pos.second - 1, input, dist, queue)
            if (pos.second + 1 < colCount) check(pos.first, pos.second, pos.first, pos.second + 1, input, dist, queue)
        }
        var result: Int = dist[end.first][end.second] -1 
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 31)
    check(part2(testInput) == 29)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}
