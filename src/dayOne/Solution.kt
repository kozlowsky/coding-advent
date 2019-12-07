package dayOne

import shared.Utils

fun main(args: Array<String>) {
    val solution = Solution()

    println("Solution: ${solution.run(Utils.readTextFile("input"))}")
}

class Solution {

    fun run(values: ArrayList<String>): Int {
        return values.map {
            calculateRequiredFuel(it.toInt())
        }.sum()
    }

    private fun calculateRequiredFuel(mass: Int): Int {
        return (mass / 3) - 2
    }
}