import java.util.Arrays
fun main() {
    fun part1(input: List<String>, tri: Int): Int {
        var result: Int = 0
        //find min col and max col
        var minCol = 9_000_000
        var maxCol = -9_000_000
        val regex = """Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)""".toRegex()
        for(line in input) {
            val regresult = regex.find(line)
            val (sci, sri, bci, bri) = regresult!!.groupValues.drop(1).map{it.toInt()}
            val manD = Math.abs(sci-bci) + Math.abs(sri-bri)
            val rem = manD - Math.abs(sri-tri)
            if (rem<0) continue;
            minCol = Math.min(minCol, sci-rem)
            maxCol = Math.max(maxCol, sci+rem)            
        }
        val totCols = maxCol-minCol+1
        println("totCols $totCols")
        val count = Array(totCols){0}
        for(line in input) {
            val regresult = regex.find(line)
            val (sci, sri, bci, bri) = regresult!!.groupValues.drop(1).map{it.toInt()}
            val manD = Math.abs(sci-bci) + Math.abs(sri-bri)
            val rem = manD - Math.abs(sri-tri)
            if (rem<0) {
                continue;
            }
            for(tci in sci-rem..sci+rem) {
                if(tci == bci && bri == tri) continue
                count[tci-minCol]++
            }
        }
        //println("array ${Arrays.toString(count)}")
        for(item in count)
        if(item>0)result++
        println("result $result")
        return result
    }

    class CR(val first: Int, val second: Int): Comparable<CR> {
        override fun compareTo(other: CR): Int = when {
            this.first != other.first -> this.first compareTo other.first // compareTo() in the infix form 
            this.second != other.second -> this.second compareTo other.second
            else -> 0
        }
    }
    fun calc(rix: Int, thr: Int, listCoords: List<List<Int>>): Long {
        val coords = mutableListOf<CR>()
        for(line in listCoords) {
            val (sci, sri, _, _, mand) = line
            val rem = mand - Math.abs(sri-rix)
            if(rem < 0) continue
            coords.add(CR(sci-rem, sci+rem))
        }
        val sortedCoords = coords.sorted()
        var currEnd = -1
        for(item in sortedCoords) {
            val start = item.first
            val end = item.second
            if(start>currEnd+1) {
                var result: Long = 1L + currEnd
                result = result * 4_000_000L
                result = result + rix
                return result;

            }
            currEnd = Math.max(currEnd, end)
        }
        if (currEnd<thr) {
                var result: Long = 1L + currEnd
                result = result * 4_000_000L
                result = result + rix
                return result;
        }
        return 0L

    }

    fun part2(input: List<String>, thr: Int): Long {
        var result: Long = 0
        val regex = """Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)""".toRegex()
        val listCoords = mutableListOf<List<Int>>()
        for(line in input) {
            val regresult = regex.find(line)
            val coords = regresult!!.groupValues.drop(1).map{it.toInt()}.toMutableList()
            val (sci, sri, bci, bri) = coords
            val manD = Math.abs(sci-bci) + Math.abs(sri-bri)
            coords.add(manD)            
            listCoords.add(coords)
        }
        for(tri in 0..thr) {
            val res = calc(tri, thr, listCoords)
            if (res > 0) {
                println("found one: $res")
                return res
            }
        }

        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day15_test")
    check(part1(testInput,10) == 26)
    check(part2(testInput,20) == 56_000_011L)

    val input = readInput("Day15")
    println(part1(input,2_000_000))
    println(part2(input,4_000_000))
}
