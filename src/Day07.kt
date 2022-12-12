fun main() {
    val MAXV: Long = 100000L
    fun removeDir(stack: MutableList<Long>): Long {
        var lastIx = stack.lastIndex
        check(lastIx >= 0)

        val lastVal = stack[lastIx]
        stack.removeAt(lastIx)

        //add to parent size
        lastIx--
        if(lastIx >= 0 && stack[lastIx] <= MAXV) stack[lastIx] += lastVal

        return lastVal
    }
    fun removeDir2(stack: MutableList<Long>, dirSize: MutableList<Long>) {
        var lastIx = stack.lastIndex
        check(lastIx >= 0)

        val lastVal = stack[lastIx]
        dirSize.add(lastVal)
        stack.removeAt(lastIx)

        //add to parent size
        lastIx--
        if(lastIx >= 0) stack[lastIx] += lastVal
    }
    fun part1(input: List<String>): Long {
        var result: Long = 0
        val stack = mutableListOf<Long>()
        for(line in input) {
            when (true) {
                line.startsWith("\$ cd ..") -> {
                    val lastVal = removeDir(stack)
                    if (lastVal <= MAXV ) result+=lastVal
                }
                line.startsWith("\$ cd ") -> stack.add(0)
                (line[0] in '0'..'9') -> {
                    val fileSize = line.split(" ").first().toLong()
                    val lastIx = stack.lastIndex
                    check(lastIx >=0 )
                    if (stack[lastIx] <= MAXV ) stack[lastIx] += fileSize
                }
                else -> {}
            }
        }
        while (stack.count() > 0) {
            val lastVal = removeDir(stack)
            if (lastVal <= MAXV ) result+=lastVal
        }
        return result
    }

    fun part2(input: List<String>): Long {
        var result: Long
        val stack = mutableListOf<Long>()
        val dirSize = mutableListOf<Long>()
        for(line in input) {
            when (true) {
                line.startsWith("\$ cd ..") -> {
                    removeDir2(stack, dirSize)
                }
                line.startsWith("\$ cd ") -> stack.add(0)
                (line[0] in '0'..'9') -> {
                    val fileSize = line.split(" ").first().toLong()
                    val lastIx = stack.lastIndex
                    check(lastIx >=0 )
                    stack[lastIx] += fileSize
                }
                else -> {}
            }
        }
        var usedSpace: Long = 0
        while (stack.count() > 0) {
            usedSpace = stack[0]
            removeDir2(stack, dirSize)
        }
        val freeSpace: Long = 70000000L - usedSpace
        val requiredSpace: Long = 30000000L - freeSpace
        result = usedSpace;
        for(size in dirSize) {
            if (size >= requiredSpace && size < result) result = size
        }
        return result    
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437L)
    check(part2(testInput) == 24933642L)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
