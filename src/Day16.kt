fun main() {
    fun parseInput(input: List<String>): Array<Pair<Int, List<Int>>> {
        var revI = input.count() - 1
        var fowI = 0
        val indexes = mutableMapOf("AA" to revI--)
        val regex = """Valve ([A-Z]+) has flow rate=(\d+); tunnels? leads? to valves? (.*)""".toRegex()
        val resultPre = Array(input.count()){Pair<Int, String>(0,"")}
        val result = Array(input.count()){Pair<Int,List<Int>>(0,listOf<Int>())}
        for(line in input) {
            val regresult = regex.find(line)
            val (fromStr, flowStr, adjList) = regresult!!.groupValues.drop(1)
            val flowInt = flowStr.toInt()
            if(flowInt>0 && !indexes.contains(fromStr))indexes[fromStr]=fowI++
            if(flowInt==0 && !indexes.contains(fromStr))indexes[fromStr]=revI--
            val fromV = indexes[fromStr]!!
            resultPre[fromV]=Pair(flowInt, adjList)            
        }
        for((ix, item) in resultPre.withIndex()) {
            val adjIntList = item.second.split(", ").map{indexes[it]!!}.toList() 
            result[ix]= Pair(item.first, adjIntList)
        }

        return result
    }
    fun part1(input: List<String>): Int {
        var result: Int = 0
        val adjArray = parseInput(input)
        val flows = Array(adjArray.count()){Array(5){Array(32768){0}}}
        for(t in 1..30) {
            val currt = t % 3
            val prevt = (t+3-1)%3
            val pre2t = (t+3-2)%3
            for(ix in adjArray.indices)
            for(mix in 0..32767) flows[ix][currt][mix]=0
            for(ix in adjArray.indices) {
                //dont open
                for(mix in 0..32767) {
                    var maxAdj = 0
                    for(aix in adjArray[ix].second) {
                        maxAdj = Math.max(maxAdj, flows[aix][prevt][mix])
                    }
                    flows[ix][currt][mix] = maxAdj
                }

                if (t<=1) continue
                if ( adjArray[ix].first == 0 ) continue
                //open valve
                //println("open valve for ix $ix")
                val myMask: Int = (1 shl ix)
                for(mix in 0..16383)
                if((myMask and mix)==0){
                    val newMask = myMask or mix
                    //if (newMask>32678) println("ix myMask mix newMask: $ix, $myMask, $mix, $newMask")
                    var maxAdj = 0
                    for(aix in adjArray[ix].second) {
                        maxAdj = Math.max(maxAdj, flows[aix][pre2t][mix])
                    }
                    val maxMe = adjArray[ix].first * (t-1) + maxAdj
                    flows[ix][currt][newMask] = Math.max(flows[ix][currt][newMask], maxMe)
                }
            }
        }
        for(ix in 0..32678) result = Math.max(result, flows[input.count()-1][30%3][ix])
        println("result $result")
        return result
    }

    fun part2(input: List<String>): Int {
        var result: Int = 0
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day16_test")
    check(part1(testInput) == 1651)
    check(part2(testInput) == 0)

    val input = readInput("Day16")
    println(part1(input))
    println(part2(input))
}
