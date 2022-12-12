import java.util.Arrays

fun main() {
    fun populateVisible(input: List<String>): Array<Array<Boolean>> {
        val rowCount = input.count()
        val colCount = input[0].count()
        val visible = Array(rowCount) {Array(colCount) {false} }
        for (ri in input.indices) {
            var currHeight: Int = 0
            for ((ci, ch) in input[ri].withIndex()) {
                val thisHeight = ch - '0' + 1;
                if ( thisHeight > currHeight ) {
                    currHeight = thisHeight
                    visible[ri][ci] = true
                }
            }
            currHeight = 0
            for (ci in (colCount-1) downTo 0 step 1) {
                val thisHeight = input[ri][ci] - '0' + 1
                if (thisHeight > currHeight) {
                    currHeight = thisHeight
                    visible[ri][ci] = true
                }
            }
        }
        for (ci in 0..(colCount-1)) {
            var currHeight: Int = 0
            for(ri in 0..(rowCount-1)) {
                val thisHeight = input[ri][ci]-'0' + 1
                if (thisHeight > currHeight) {
                    currHeight = thisHeight
                    visible[ri][ci] = true
                }
            }
            currHeight = 0
            for(ri in (rowCount-1) downTo 0 step 1) {
                val thisHeight = input[ri][ci]-'0' + 1
                if (thisHeight > currHeight) {
                    currHeight = thisHeight
                    visible[ri][ci] = true
                }
            }
        }
        return visible
    }
    fun countVisible(visible: Array<Array<Boolean>>): Int {
        var result = 0
        for(row in visible) {
            for(col in row) {
                if(col) result++
            }
        }
        return result
    }
    fun calculate(input: List<String>): Int {
        var result: Int = 0
        val rowCount = input.count()
        val colCount = input[0].count()
        for(ri in 1..(rowCount-2))
        for(ci in 1..(colCount-2)) {
            val currHeight = input[ri][ci] - '0'
            var upV = 0
            var downV = 0
            var rightV = 0
            var leftV = 0
            //go up
            for(tri in ri-1 downTo 0 step 1) {
                val thisHeight = input[tri][ci] - '0'
                upV++
                if (thisHeight >= currHeight) break
            }
            //go down
            for(tri in (ri+1)..(rowCount-1)) {
                val thisHeight = input[tri][ci] - '0'
                downV++
                if (thisHeight >= currHeight) break
            }
            //go right
            for(tci in (ci+1)..(colCount-1)) {
                val thisHeight = input[ri][tci] - '0'
                rightV++
                if(thisHeight >= currHeight) break
            }
            //go left
            for(tci in (ci-1) downTo 0 step 1) {
                val thisHeight = input[ri][tci] - '0'
                leftV++
                if(thisHeight >= currHeight) break
            }
            val thisV = upV * downV * rightV * leftV
            if (thisV > result) result = thisV
        }
        return result
    }
    fun part1(input: List<String>): Int {
        var result: Int
        val visible = populateVisible(input)
        result = countVisible(visible)
        return result
    }

    fun part2(input: List<String>): Int {
        var result: Int
        result = calculate(input)
        return result    
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
