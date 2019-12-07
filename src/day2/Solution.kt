package day2

import shared.Utils

fun main(args: Array<String>) {
    val values = Utils.readTextFile("src/day2/input.txt")
    val intCode = values[0].split(",").map { it.toInt() }

    val solution = Solution()

    println("Answer One: ${solution.calculateFirstAnswer(intCode)}")
    println("Answer Two: ${solution.calculateSecondAnswer(intCode, 19690720)}")
}


class Solution {

    fun calculateFirstAnswer(values: List<Int>): Int {
        val changedIntCode = values.toMutableList()
        changedIntCode[1] = 12
        changedIntCode[2] = 2

        return buildIntCode(changedIntCode)[0]
    }

    fun calculateSecondAnswer(values: List<Int>, wantedOutput: Int): Int {
        val nounVerbPair = findPairThatReproducesGivenOutput(values, wantedOutput)

        return 100 * nounVerbPair.first + nounVerbPair.second
    }

    private fun buildIntCode(values: List<Int>): List<Int> {
        val newIntCode = values.toMutableList()
        for (index in 0..values.size step 4) {
            when (values[index]) {
                1 -> newIntCode[newIntCode[index + 3]] =
                    newIntCode[newIntCode[index + 1]] + newIntCode[newIntCode[index + 2]]
                2 -> newIntCode[newIntCode[index + 3]] =
                    newIntCode[newIntCode[index + 1]] * newIntCode[newIntCode[index + 2]]
                99 -> return newIntCode
                else -> println("Invalid opcode, skipping...")
            }
        }

        return newIntCode
    }

    private fun findPairThatReproducesGivenOutput(intcode: List<Int>, givenOutput: Int): Pair<Int, Int> {
        val newIntCode = intcode.toMutableList()
        for (noun in 0..99) {
            for (verb in 0..99) {
                newIntCode[1] = noun
                newIntCode[2] = verb
                val output = buildIntCode(newIntCode)[0]

                if (output == givenOutput) return Pair(noun, verb)
            }
        }

        return Pair(-1, -1)
    }
}