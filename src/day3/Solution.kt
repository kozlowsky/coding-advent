package day3

import shared.Utils
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


fun main(args: Array<String>) {
    val input = Utils.readTextFile("src/day3/input.txt")

    val wires = input.map {
        it.split(",")
    }.map {
        it.map { move ->
            Move(move[0], move.substring(1).toInt())
        }
    }

    val solution = Solution()
    println("Answer One: ${solution.calculateFirstAnswer(wires, Point(0, 0))}")
}


fun <T> List<T>.tail() = drop(1)

data class Point(val x: Int, val y: Int) {
    fun isValid() = x < Int.MAX_VALUE && y < Int.MAX_VALUE
    fun manhattanDistance(other: Point): Int {
        return abs(this.x - other.x) + abs(this.y - other.y)
    }
}

data class Move(val direction: Char, val amount: Int)

data class Segment(val first: Point, val second: Point) {
    fun intersect(other: Segment): Point {
        val a1 = this.second.y - this.first.y
        val b1 = this.first.x - this.second.x
        val c1 = a1 * (this.first.x) + b1 * (this.first.y)

        val a2 = other.second.y - other.first.y
        val b2 = other.first.x - other.second.x
        val c2 = a2 * (other.first.x) + b2 * (other.first.y)

        val det = a1 * b2 - a2 * b1

        if (det == 0) return Point(Int.MAX_VALUE, Int.MAX_VALUE)

        val x = (b2 * c1 - b1 * c2) / det
        val y = (a1 * c2 - a2 * c1) / det

        return if (
            (min(this.first.x, this.second.x) <= x) && x <= max(this.first.x, this.second.x) &&
            (min(this.first.y, this.second.y) <= y) && y <= max(this.first.y, this.second.y) &&
            (min(other.first.x, other.second.x) <= x) && x <= max(other.first.x, other.second.x) &&
            (min(other.first.y, other.second.y) <= y) && y <= max(other.first.y, other.second.y)
        ) Point(x, y)
        else Point(Int.MAX_VALUE, Int.MAX_VALUE)
    }
}

class Solution {

    fun calculateFirstAnswer(wires: List<List<Move>>, startingPoint: Point): Int {
        val (first, second) = wires.map {
            getPathPoints(it, startingPoint)
        }

        return findIntersectionPoints(first, second)
            .filter { it != startingPoint }
            .map { it.manhattanDistance(startingPoint) }
            .min()!!
    }

    private fun getPathPoints(
        moves: List<Move>,
        currentPoint: Point
    ): List<Point> {
        return moves.fold(mutableListOf(currentPoint)) { acc, move ->
            (acc + getNextPoint(move, acc.last())).toMutableList()
        }
    }

    private fun getNextPoint(move: Move, currentPoint: Point): Point {
        return when (move.direction) {
            'U' -> Point(currentPoint.x, currentPoint.y + move.amount)
            'R' -> Point(currentPoint.x + move.amount, currentPoint.y)
            'D' -> Point(currentPoint.x, currentPoint.y - move.amount)
            'L' -> Point(currentPoint.x - move.amount, currentPoint.y)
            else -> throw IllegalStateException("Incorrect move direction!")
        }
    }

    private fun findIntersectionPoints(wireOne: List<Point>, wireTwo: List<Point>): List<Point> {
        return wireOne
            .zipWithNext()
            .map { Segment(it.first, it.second) }
            .map { firstSegment ->
                wireTwo.zipWithNext()
                    .map {
                        Segment(it.first, it.second)
                    }.map { secondSegment ->
                        firstSegment.intersect(secondSegment)
                    }.filter { point ->
                        point.isValid()
                    }
            }.flatten()
    }
}