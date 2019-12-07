package shared

import java.io.File

object Utils {
    fun readTextFile(pathToFile: String): List<String> {
        return File(pathToFile)
            .readLines()

    }
}