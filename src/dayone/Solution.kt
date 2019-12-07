package dayone

import shared.Utils

fun main(args: Array<String>) {
    val solution = Solution()

    println("Answer: ${solution.runPartOne(Utils.readTextFile("src/dayone/input.txt"))}")
    println("Answer: ${solution.runPartTwo(Utils.readTextFile("src/dayone/input.txt"))}")
}

class Solution {

    fun runPartOne(values: List<String>): Int {
        return values.sumBy {
            calculateRequiredFuel(it.toInt())
        }
    }

    fun runPartTwo(values: List<String>): Int {
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