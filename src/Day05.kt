fun main() {
    fun parseInput(input: String): List<List<String>> {
        val inList = input
            .split("\n\n")
            .map{
                it.split("\n").toList()
            }
            .toList()
        check(inList.count() ==2)
        return inList
    }
    fun getStacks(input: List<String>): List<ArrayDeque<Char>> {
        val result = mutableListOf<ArrayDeque<Char>>()
        val len = input.count();
        val stCount = ( input[len-1].count() + 1 ) / 4
        for (i in 1..stCount) result.add(ArrayDeque())
        check(result.count() == stCount)
        for (i in (len-2) downTo 0) {
            val lineLen = input[i].count()
            var ix =0
            for(j in 1..lineLen step 4) {
                check(ix < stCount)
                if (input[i][j] != ' ') {
                    result[ix].addLast(input[i][j])
                }
                ix++
            }
        }
        return result
    }
    fun processMoves(input: List<String>, stacks: List<ArrayDeque<Char>>) {
        val len = input.count()
        for(i in 0..(len-1)) {
            val words = input[i].split(" ")
            check(words.count() == 6)
            val moveCount = words[1].toInt()
            val moveFrom = words[3].toInt() - 1
            val moveTo = words[5].toInt() - 1
            check(moveFrom < stacks.count())
            check(moveTo < stacks.count())
            for(ii in 1..moveCount) {
                if (stacks[moveFrom].count() > 0) {
                    val char = stacks[moveFrom].removeLast()
                    stacks[moveTo].addLast(char)
                }
            }
        }
    }
    fun processMoves2(input: List<String>, stacks: List<ArrayDeque<Char>>) {
        val len = input.count()
        for(i in 0..(len-1)) {
            val words = input[i].split(" ")
            check(words.count() == 6)
            val moveCount = words[1].toInt()
            val moveFrom = words[3].toInt() - 1
            val moveTo = words[5].toInt() - 1
            check(moveFrom < stacks.count())
            check(moveTo < stacks.count())
            val tmpStack = ArrayDeque<Char>()
            for(ii in 1..moveCount) {
                if (stacks[moveFrom].count() > 0) {
                    val char = stacks[moveFrom].removeLast()
                    tmpStack.addLast(char)
                }
            }
            while(tmpStack.count() >0) {
                val char = tmpStack.removeLast()
                stacks[moveTo].addLast(char)
            }
        }
    }

    fun part1(input: String): String {
        var result: String = ""
        val inList = parseInput(input)
        val stacks = getStacks(inList[0])
        processMoves(inList[1], stacks)
        for(stack in stacks) {
            if (stack.count() > 0) {
                result += stack.removeLast()
            }
        }
        return result
    }

    fun part2(input: String): String {
        var result: String = ""
        val inList = parseInput(input)
        val stacks = getStacks(inList[0])
        processMoves2(inList[1], stacks)
        for(stack in stacks) {
            if (stack.count() > 0) {
                result += stack.removeLast()
            }
        }
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readFile("Day05_test")
    check(part1(testInput).equals("CMZ"))
    check(part2(testInput).equals("MCD"))

    val input = readFile("Day05")
    println(part1(input))
    println(part2(input))
}
