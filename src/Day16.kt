fun main() {
    val MAXD = 10000
    fun parseInput(input: List<String>): Pair<Array<Int>, Array<Array<Int>>> {
        var revI = input.count() - 1
        var fowI = 0
        val indexes = mutableMapOf("AA" to revI--)
        val regex = """Valve ([A-Z]+) has flow rate=(\d+); tunnels? leads? to valves? (.*)""".toRegex()
        for(line in input) {
            val regresult = regex.find(line)
            val (fromStr, flowStr, _) = regresult!!.groupValues.drop(1)
            val flowInt = flowStr.toInt()
            if ( indexes.contains(fromStr) ) continue
            if ( flowInt>0 ) indexes[fromStr] = fowI++
            else indexes[fromStr] = revI--
        }
        val flows = Array(fowI){0}
        val adjArray = Array(input.count()) {Array(input.count()) {MAXD}}

        for(line in input) {
            val regresult = regex.find(line)
            val (fromStr, flowStr, adjList) = regresult!!.groupValues.drop(1)
            val flowInt = flowStr.toInt()
            val fromIx = indexes[fromStr]!!
            if (fromIx<fowI) flows[fromIx]=flowInt

            adjArray[fromIx][fromIx] = 0
            adjList.split(", ").map{ indexes[it]!! }.forEach {
                adjArray[fromIx][it] = 1
                adjArray[it][fromIx] = 1
            }
        }
        for(iter in 0..input.count()) {
            for(i in adjArray.indices)
            for(j in adjArray.indices)
            for(k in adjArray.indices) 
            if (adjArray[i][k]>(adjArray[i][j]+adjArray[j][k])) {
                adjArray[i][k] = adjArray[i][j]+adjArray[j][k]
            }
        }
        return Pair(flows, adjArray)
    }
    fun calcbest(inp: Pair<Array<Int>, Array<Array<Int>>>): Array<Array<Array<Int>>> {
        val flows = inp.first
        val dist = inp.second
        val count = flows.count()
        val ALL = (1 shl count) - 1
        val MAXV = ALL + 1
        val best = Array(31){Array(16){Array(MAXV){0}}}
        for(t in 1..30) {
            for(toIx in 0..(count-1)) {
                val fresh = flows[toIx] * (t-1)
                val toMask = 1 shl toIx
                best[t][toIx][toMask] = Math.max(best[t][toIx][toMask], fresh)

                for(fromIx in 0..(count-1)) 
                if(fromIx!=toIx && (dist[fromIx][toIx]+1)<=t) {
                    val fromMask = 1 shl fromIx
                    for(mask in 0..ALL)
                    if ((fromMask and mask)==fromMask && (toMask and mask)==0) {
                        val calc = best[t-1-dist[fromIx][toIx]][fromIx][mask] + (flows[toIx] * (t-1))
                        val newMask = mask or toMask
                        best[t][toIx][newMask] = Math.max(best[t][toIx][newMask], calc)
                    }
                }
            }
        }
        return best
    }
    fun part1(input: List<String>): Int {
        var result: Int = 0
        val adjArray = parseInput(input)
        val best = calcbest(adjArray)
        val count = adjArray.first.count()
        val dist = adjArray.second
        val ALL = (1 shl count) - 1
        val stIx = input.count()-1
        for(toIx in 0..(count-1))
        if(dist[stIx][toIx]<=30){
            for(mask in 0..ALL)
            result = Math.max(result, best[30-dist[stIx][toIx]][toIx][mask])
        }
        return result
    }

    fun part2(input: List<String>): Int {
        var result: Int = 0
        val adjArray = parseInput(input)
        val best = calcbest(adjArray)
        val count = adjArray.first.count()
        val dist = adjArray.second
        val ALL = (1 shl count) - 1
        val stIx = input.count()-1
        for(myMask in 0..ALL) {
            val othMask = myMask xor ALL
            var bestMy = 0
            var bestOth = 0
            for(ix in 0..(count-1)) {
                val ixMask = 1 shl ix
                if((myMask and ixMask) == ixMask) {
                    val tt = dist[ix][stIx];
                    if(tt<=26) {
                        bestMy = Math.max(bestMy, best[26-tt][ix][myMask])
                    }
                }
                if((othMask and ixMask) == ixMask) {
                    val tt = dist[ix][stIx];
                    if(tt<=26) {
                        bestOth = Math.max(bestOth, best[26-tt][ix][othMask])
                    }
                }
            }
            result = Math.max(result, bestMy + bestOth)
        }
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day16_test")
    check(part1(testInput) == 1651)
    check(part2(testInput) == 1707)

    val input = readInput("Day16")
    println(part1(input))
    println(part2(input))
}
