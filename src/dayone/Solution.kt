package dayone

import shared.Utils

fun main(args: Array<String>) {
    val solution = Solution()

    println("Answer: ${solution.calculateFirstAnswer(Utils.readTextFile("src/dayone/input.txt").map { it.toInt() })}")
    println("Answer: ${solution.calculateSecondAnswer(Utils.readTextFile("src/dayone/input.txt").map { it.toInt() })}")
}

class Solution {

    fun calculateFirstAnswer(values: List<Int>): Int {
        return values.sumBy {
            calculateRequiredFuel(it.toInt())
        }
    }

    fun calculateSecondAnswer(values: List<Int>): Int {
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