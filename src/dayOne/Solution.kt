package dayOne

import java.io.File

fun main(args: Array<String>) {
    val solution = Solution()

    println("Solution: ${solution.run("input")}")

}

class Solution {

    fun run(filename: String) : Int {
        return readTextFile(filename).map {
            logic(it.toInt())
        }.sum()
    }

    private fun logic(mass: Int): Int {
        return (mass / 3) - 2
    }

    private fun readTextFile(filename: String): ArrayList<String> {
        val lines = arrayListOf<String>()
        File("src/dayOne/$filename.txt").forEachLine {
            lines.add(it)
        }

        return lines
    }
}