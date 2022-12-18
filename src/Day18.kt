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
        val inp  = mutableSetOf<Pair<Int,Pair<Int,Int>>>()
        for(line in input) {
            val (x, y, z) = line.split(",").map{it.toInt()}
            inp.add(Pair(x,Pair(y,z)))
        }
        val holes = mutableSetOf<Pair<Int,Pair<Int,Int>>>()
        val visited = mutableSetOf<Pair<Int,Pair<Int,Int>>>()
        val queue = mutableListOf<Pair<Int,Pair<Int,Int>>>()
        for(pa in -1..22)
        for(sa in -1..22) {
            queue.add(Pair(0,Pair(pa,sa)))
            queue.add(Pair(pa,Pair(0,sa)))
            queue.add(Pair(pa,Pair(sa,0)))
        }
        while(queue.count()>0) {
            val entry = queue.removeFirst()
            if(visited.contains(entry)) continue
            visited.add(entry)
            if(inp.contains(entry)) continue
            holes.add(entry)
            val (x, yz) = entry
            val (y, z) = yz
            if(x>=0)queue.add(Pair(x-1,Pair(y,z)))
            if(x<=21)queue.add(Pair(x+1,Pair(y,z)))
            if(y>=0)queue.add(Pair(x,Pair(y-1,z)))
            if(y<=21)queue.add(Pair(x,Pair(y+1,z)))
            if(z>=0)queue.add(Pair(x,Pair(y,z-1)))
            if(z<=21)queue.add(Pair(x,Pair(y,z+1)))
        }
        for(item in holes) {
            val (x, yz) = item
            val (y, z) = yz
            if(inp.contains(Pair(x+1, Pair(y,z)))) result++
            if(inp.contains(Pair(x-1, Pair(y,z)))) result++
            if(inp.contains(Pair(x, Pair(y+1,z)))) result++
            if(inp.contains(Pair(x, Pair(y-1,z)))) result++
            if(inp.contains(Pair(x, Pair(y,z+1)))) result++
            if(inp.contains(Pair(x, Pair(y,z-1)))) result++
        }
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day18_test")
    check(part1(testInput) == 64)
    check(part2(testInput) == 58)

    val input = readInput("Day18")
    println(part1(input))
    println(part2(input))
}
