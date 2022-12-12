import java.util.Arrays

class Flow(val items: MutableList<Long>, val op: (Long) -> Long, val div: Long, val ix1: Int, val ix2: Int)

fun main() {
    val MODV:Long = 223_092_870L
    fun opFun(op: Char, inValue: String): (Long) -> Long {
        val value:Long = if (inValue.equals("old")) -1L else inValue.toLong()
        when (op) {
            '*' -> return {input -> 
                if (value == -1L)
                    (input * input) % MODV
                else
                    (input * value) % MODV 
                }
            '+' -> return { input -> 
                if (value == -1L)
                    (input + input) % MODV
                else
                    (input + value) % MODV    
                }
            else -> {
                println("Hello op $op")
                return {input -> input}
            } 
        }
    }
    fun parseOne(lines: List<String>): Flow {
        val items: MutableList<Long> = lines[1].split(":").last().split(",").map{ it.trim().toLong()}.toMutableList()
        val opInp = lines[2].split(" ").takeLast(2);
        val op = opFun(opInp[0][0], opInp[1])
        val div = lines[3].split(" ").last().toLong()
        val ix1 = lines[4].split(" ").last().toInt()
        val ix2 = lines[5].split(" ").last().toInt()
        return Flow(items, op, div, ix1, ix2)
    }
    fun parseInput(input: List<String>): List<Flow> {
        //val result = mutableListOf<Flow>()
        val inter = input.windowed(6,7);
        val result: List<Flow> = inter.map{ parseOne(it)}.toList()
        return result;
    }
    fun doOnce(works: List<Flow>, count: Array<Long>, divBy3: Boolean) {
        for((ix, work) in works.withIndex()) {
            val numInspected = work.items.count()
            count[ix] = count[ix] + numInspected
            for(item in work.items) {
                var newV: Long = (work.op)(item)
                if (divBy3) newV /= 3
                if ((newV % work.div) == 0L) works[work.ix1].items.add(newV)
                else works[work.ix2].items.add(newV)
            }
            work.items.clear()
        }
    }
    fun part1(input: List<String>): Long {
        var result: Long
        val parsedInput = parseInput(input)
        val count: Array<Long> = Array<Long>(parsedInput.count()){0L}
        for(i in 1..20) doOnce(parsedInput, count, true)
        result = count.sortedDescending().take(2).fold(1L) {mul, item -> mul * item}
        return result
    }

    fun part2(input: List<String>): Long {
        var result: Long
        val parsedInput = parseInput(input)
        val count: Array<Long> = Array<Long>(parsedInput.count()){0L}
        for(i in 1..10000) doOnce(parsedInput, count, false)
        result = count.sortedDescending().take(2).fold(1L) {mul, item -> mul * item}
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 10605L)
    check(part2(testInput) == 2713310158L)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}
