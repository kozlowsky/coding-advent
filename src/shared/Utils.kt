package shared

import java.io.File

object Utils {
    fun readTextFile(filename: String): ArrayList<String> {
        val lines = arrayListOf<String>()
        File("src/dayOne/$filename.txt").forEachLine {
            lines.add(it)
        }

        return lines
    }
}