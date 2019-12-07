package day1

import shared.Utils

fun main(args: Array<String>) {
    val input = Utils.readTextFile("src/day1/input.txt")
    val solution = Solution()

    println("Answer One: ${solution.calculateFirstAnswer(input.map { it.toInt() })}")
    println("Answer Two: ${solution.calculateSecondAnswer(input.map { it.toInt() })}")
}

class Solution {

    fun calculateFirstAnswer(values: List<Int>): Int {
        return values.sumBy {
            calculateRequiredFuel(it)
        }
    }

    fun calculateSecondAnswer(values: List<Int>): Int {
        return values.sumBy {
            calculateRequiredFuel(it, 0)
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