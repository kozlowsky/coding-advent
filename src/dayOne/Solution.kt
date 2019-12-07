package dayOne

import shared.Utils

fun main(args: Array<String>) {
    val solution = Solution()

    println("Solution: ${solution.runPartOne(Utils.readTextFile("input"))}")
    println("Solution: ${solution.runPartTwo(Utils.readTextFile("input"))}")
}

class Solution {

    fun runPartOne(values: ArrayList<String>): Int {
        return values.sumBy {
            calculateRequiredFuel(it.toInt())
        }
    }

    fun runPartTwo(values: ArrayList<String>): Int {
        return values.sumBy {
            calculateRequiredFuel(it.toInt(), 0)
        }
    }

    private fun calculateRequiredFuel(mass: Int, acc: Int): Int {
        val requiredFuel = calculateRequiredFuel(mass)
        return if (requiredFuel > 0)
            calculateRequiredFuel(requiredFuel, acc + requiredFuel)
        else acc
    }


    private fun calculateRequiredFuel(mass: Int): Int {
        return (mass / 3) - 2
    }
}