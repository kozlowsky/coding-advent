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

data class Point(val x: Int, val y: Int)

data class Move(val direction: Char, val amount: Int)

class Solution {

    fun calculateFirstAnswer(wires: List<List<Move>>, startingPoint: Point): Pair<Point, Int> {
        val wiresPathPoints = wires.map {
            getPathPoints(mutableListOf(startingPoint), it, startingPoint)
        }

        val intersectionPoints = findIntersectionPoints(wiresPathPoints)

        val distances = countManhattanDistances(intersectionPoints, startingPoint)
        println(distances)

        return distances[0]
    }

    private fun countManhattanDistances(points: List<Point>, startingPoint: Point): List<Pair<Point, Int>> {
        return points.map {
            Pair(it, abs(startingPoint.x - it.x) + abs(startingPoint.y - it.y))
        }.sortedBy { it.second }
    }

    private fun getPathPoints(currentPoints: MutableList<Point>, moves: List<Move>, currentPoint: Point): List<Point> {
        return if (moves.isNotEmpty()) {
            val nextPoint = getNextPoint(moves[0], currentPoint)
            currentPoints.add(nextPoint)
            getPathPoints(currentPoints, moves.tail(), nextPoint)
        } else {
            currentPoints
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

    private fun findIntersectionPoints(wires: List<List<Point>>): List<Point> {
        val intersectionPoints = mutableListOf<Point>()
        for (i in 0 until wires[0].size - 1) {
            for (j in 0 until wires[1].size - 1) {


                val intersectionPoint = findIntersectionPointBetweenTwoSegments(
                    wires[0][i],
                    wires[0][i + 1],
                    wires[1][j],
                    wires[1][j + 1]
                )

                if (intersectionPoint.x < Int.MAX_VALUE && !intersectionPoints.contains(intersectionPoint))
                    intersectionPoints.add(intersectionPoint)
            }
        }

        return intersectionPoints
    }

    private fun findIntersectionPointBetweenTwoSegments(
        A: Point,
        B: Point,
        C: Point,
        D: Point
    ): Point {
        val a1 = B.y - A.y
        val b1 = A.x - B.x
        val c1 = a1 * (A.x) + b1 * (A.y)

        val a2 = D.y - C.y
        val b2 = C.x - D.x
        val c2 = a2 * (C.x) + b2 * (C.y)

        val det = a1 * b2 - a2 * b1

        if (det == 0) return Point(Int.MAX_VALUE, Int.MAX_VALUE)

        val x = (b2 * c1 - b1 * c2) / det
        val y = (a1 * c2 - a2 * c1) / det

        return if (
            (min(A.x, B.x) <= x) && x <= max(A.x, B.x) &&
            (min(A.y, B.y) <= y) && y <= max(A.y, B.y) &&
            (min(C.x, D.x) <= x) && x <= max(C.x, D.x) &&
            (min(C.y, D.y) <= y) && y <= max(C.y, D.y)
        ) Point(x, y)
        else Point(Int.MAX_VALUE, Int.MAX_VALUE)
    }
}