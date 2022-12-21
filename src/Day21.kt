fun main() {
    fun parse(input: List<String>): Map<Int, List<Int>> {
        val ix = mutableMapOf<String,Int>("root" to 0)
        val result = mutableMapOf<Int, List<Int>>()
        for(line in input){
            val a = line.split(":").first()
            if(ix.contains(a))continue
            ix[a] = ix.count()
        }
        for(line in input){
            val a = line.split(": ")
            val toIx = ix[a[0]]!!
            val rest = a.last().split(" ")
            if(rest.count()==1) {
                val me = rest[0].toInt()
                result[toIx]=listOf<Int>(me)
            } else if (rest.count()==3){
                val ixa = ix[rest[0]]!!
                val ixb = ix[rest[2]]!!
                val op: Int = when (rest[1]) {
                    "*" -> 0
                    "/" -> 1
                    "+" -> 2
                    "-" -> 3
                    else -> {
                        println("Hello 1")
                        -1
                    }
                }
                result[toIx]=listOf<Int>(ixa, ixb, op)
            } else {
                println("hello 2: ${rest.count()}")
            }
        }
        return result
    }

    fun parse2(input: List<String>): Pair<Array<Long?>, Map<Int, List<Int>>> {
        val ix = mutableMapOf<String,Int>("root" to 0)
        ix.put("humn", 1)
        val result = mutableMapOf<Int, List<Int>>()
        val result2 = Array<Long?>(input.count()){null}
        for(line in input){
            val a = line.split(":").first()
            if(ix.contains(a))continue
            ix[a] = ix.count()
        }
        for(line in input){
            val a = line.split(": ")
            val toIx = ix[a[0]]!!
            val rest = a.last().split(" ")
            if(rest.count()==1) {
                val me = rest[0].toInt()
                result[toIx]=listOf<Int>(me)
                result2[toIx]=me.toLong()
            } else if (rest.count()==3){
                val ixa = ix[rest[0]]!!
                val ixb = ix[rest[2]]!!
                val op: Int = when (rest[1]) {
                    "*" -> 0
                    "/" -> 1
                    "+" -> 2
                    "-" -> 3
                    else -> {
                        println("Hello 1")
                        -1
                    }
                }
                result[toIx]=listOf<Int>(ixa, ixb, op)
            } else {
                println("hello 2: ${rest.count()}")
            }
        }
        result2[1]=null
        return Pair(result2,  result)
    }
    fun dfs(ix: Int, map: Map<Int, List<Int>>): Long {
        val ins = map[ix]!!
        if (ins.count()==1) return ins[0].toLong()
        check(ins.count()==3)
        val out1 = dfs(ins[0], map)
        val out2 = dfs(ins[1], map)
        val result: Long = when (ins[2]) {
            0 -> out1 * out2
            1 -> out1 / out2
            2 -> out1 + out2
            3 -> out1 - out2
            else -> {
                println("hello 4")
                -1L
            }
        }
        return result
        
    }

    fun dfs2(ix: Int, map: Map<Int, List<Int>>, arr: Array<Long?>) {
        val ins = map[ix]!!
        if (ins.count()==1) return
        check(ins.count()==3)
        dfs2(ins[0], map, arr)
        dfs2(ins[1], map, arr)

        if(arr[ins[0]] == null || arr[ins[1]]==null) return
        val out1 = arr[ins[0]]!!
        val out2 = arr[ins[1]]!!
        val result: Long = when (ins[2]) {
            0 -> out1 * out2
            1 -> out1 / out2
            2 -> out1 + out2
            3 -> out1 - out2
            else -> {
                println("hello 4")
                -1L
            }
        }
        arr[ix]=result
    }
    fun dfs3(ix: Int, v: Long, map: Map<Int, List<Int>>, arr: Array<Long?>): Long {
        val ins = map[ix]!!
        if(ins.count()==1) return v
        var ixa = ins[0]
        var ixb = ins[1]
        val result: Long
        if (arr[ixa]==null){
            val v1 = arr[ixb]!!
            if(ix==0) return dfs3(ixa, v1, map, arr)
            result = when(ins[2]) {
                0 -> v / v1
                1 -> v * v1
                2 -> v - v1
                3 -> v + v1
                else -> {
                    println("hello 5")
                    -1L
                }
            }
            return dfs3(ixa, result, map, arr)        
        } else {
            val v1 = arr[ixa]!!
            if(ix==0) return dfs3(ixb, v1, map, arr)
            result = when(ins[2]) {
                0 -> v / v1
                1 -> v1 / v
                2 -> v - v1
                3 -> v1 - v
                else -> {
                    println("hello 6")
                    -1L
                }
            }        
            return dfs3(ixb, result, map, arr)
        }
    }
    fun part1(input: List<String>): Long {
        val parsed = parse(input)
        val result = dfs(0, parsed)
        return result
    }

    fun part2(input: List<String>): Long {
        val parsed = parse2(input)
        dfs2(0, parsed.second, parsed.first)
        val result = dfs3(0,0,parsed.second, parsed.first)
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day21_test")
    check(part1(testInput) == 152L)
    check(part2(testInput) == 301L)

    val input = readInput("Day21")
    println(part1(input))
    println(part2(input))
}
