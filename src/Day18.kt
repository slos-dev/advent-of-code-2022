fun main() {
    fun part1(input: List<String>): Int {
        var result: Int = 0
        val inp  = mutableListOf<List<Int>>()
        for(line in input) {
            val coords = line.split(",").map{it.toInt()}.toList()
            inp.add(coords)
        }
        for(ix in -1..22) {
            //x axis
            val mapX = mutableMapOf<Pair<Int,Int>,Int>()
            val mapY = mutableMapOf<Pair<Int,Int>,Int>()
            val mapZ = mutableMapOf<Pair<Int,Int>,Int>()
            for(item in inp) {
                val (x, y, z) = item
                if(ix == x || (ix+1) == x) mapX[Pair(y,z)] = mapX.getOrDefault(Pair(y,z),0) + 1
                if(ix == y || (ix+1) == y) mapY[Pair(x,z)] = mapY.getOrDefault(Pair(x,z),0) + 1
                if(ix == z || (ix+1) == z) mapZ[Pair(x,y)] = mapZ.getOrDefault(Pair(x,y),0) + 1
            }
            result += mapX.values.filter {it == 1}.sum()
            result += mapY.values.filter {it == 1}.sum()
            result += mapZ.values.filter {it == 1}.sum()
        }
        return result
    }

    fun part2(input: List<String>): Int {
        var result: Int = 0
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day18_test")
    check(part1(testInput) == 64)
    check(part2(testInput) == 0)

    val input = readInput("Day18")
    println(part1(input))
    println(part2(input))
}
