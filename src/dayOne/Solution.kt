package dayOne

import shared.Utils

fun main(args: Array<String>) {
    val solution = Solution()

    println("Solution: ${solution.run("input")}")

}

class Solution {

    fun run(filename: String) : Int {
        return Utils.readTextFile(filename).map {
            logic(it.toInt())
        }.sum()
    }

    private fun logic(mass: Int): Int {
        return (mass / 3) - 2
    }
}