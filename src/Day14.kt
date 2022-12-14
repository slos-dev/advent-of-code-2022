fun main() {
    fun canPlaceSand(ri: Int, ci: Int, arr: Array<Array<Int>>): Boolean {
        if (ri>=199) return false
        if(ci<=0 || ci>=599) return false
        //down
        if(arr[ri+1][ci] == 0 || arr[ri+1][ci] == 3) return false
        if(arr[ri+1][ci-1]==0 || arr[ri+1][ci-1] ==3) return false
        if(arr[ri+1][ci+1]==0 || arr[ri+1][ci+1] ==3) return false
        return true
    }
    fun isAbyss(ri: Int, ci: Int, arr: Array<Array<Int>>): Boolean {
        if (ri>=199) return true
        if(ci<=0 || ci>=599) return true
        if(arr[ri][ci] == 0 || arr[ri][ci] == 3) return true
        return false
    }
    fun dfs(ri: Int, ci: Int, arr: Array<Array<Int>>) {
        if(ri>198 || ci<1 || ci>598) return;
        if(arr[ri][ci]!=0) return;
        dfs(ri+1, ci, arr)
        if(!isAbyss(ri+1, ci, arr)){
            dfs(ri+1, ci-1, arr)
            if(!isAbyss(ri+1, ci-1, arr)) {
                dfs(ri+1, ci+1, arr)
            }
        }
        if (canPlaceSand(ri, ci, arr)) {
            arr[ri][ci]=2
        }
        else {
            arr[ri][ci]=3
        }
    }
    fun part1(input: List<String>): Int {
        //1 is rock , 2 is sand, 3 is abyss
        val arr = Array(200){Array<Int>(600) {0}}
        for(line in input) {
            val path = line.split(" -> ").map {
                val res = it.split(",")
                Pair(res[1].toInt(), res[0].toInt()-400)
            }
            for(i in 0..(path.count()-2)) {
                if (path[i].first == path[i+1].first) {
                    val fromC = Math.min(path[i].second, path[i+1].second)
                    val toC = Math.max(path[i].second, path[i+1].second)
                    for(j in fromC..toC) arr[path[i].first][j]=1
                }else {
                    val fromR = Math.min(path[i].first, path[i+1].first)
                    val toR = Math.max(path[i].first,path[i+1].first)
                    for(j in fromR..toR) arr[j][path[i].second] = 1
                }
            }
        }
        dfs(0,100,arr)

        var result: Int = 0
        for(i in 0..199)
        for(j in 0..199)
        if (arr[i][j]==2) result += 1
        return result
    }

    fun part2(input: List<String>): Int {
        //1 is rock , 2 is sand, 3 is abyss
        val arr = Array(200){Array<Int>(600) {0}}
        var maxR = 0
        for(line in input) {
            val path = line.split(" -> ").map {
                val res = it.split(",")
                val ri = res[1].toInt()
                maxR = Math.max(maxR, ri)
                Pair(ri, res[0].toInt()-200)
            }
            for(i in 0..(path.count()-2)) {
                if (path[i].first == path[i+1].first) {
                    val fromC = Math.min(path[i].second, path[i+1].second)
                    val toC = Math.max(path[i].second, path[i+1].second)
                    for(j in fromC..toC) arr[path[i].first][j]=1
                }else {
                    val fromR = Math.min(path[i].first, path[i+1].first)
                    val toR = Math.max(path[i].first,path[i+1].first)
                    for(j in fromR..toR) arr[j][path[i].second] = 1
                }
            }
        }
        for(j in 0..599)arr[maxR+2][j]=1
        dfs(0,300,arr)

        var result: Int = 0
        for(i in 0..199)
        for(j in 0..599)
        if (arr[i][j]==2) result += 1
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 24)
    check(part2(testInput) == 93)

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}
