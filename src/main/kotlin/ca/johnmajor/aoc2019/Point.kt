package ca.johnmajor.aoc2019

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point): Point =
        Point(x + other.x, y + other.y)

    operator fun minus(other: Point): Point =
        Point(x - other.x, y - other.y)

    fun move(direction: CardinalDirection, distance: Int = 1) =
        when (direction) {
            CardinalDirection.NORTH -> Point(x, y + distance)
            CardinalDirection.EAST -> Point(x + distance, y)
            CardinalDirection.SOUTH -> Point(x, y - distance)
            CardinalDirection.WEST -> Point(x - distance, y)
        }

    fun move(move: CardinalMove): Point =
        move(move.direction, move.distance)

    fun manhattanDistance(from: Point = ORIGIN) = abs(x - from.x) + abs(y - from.y)

    fun distance(other: Point): Double =
        sqrt((other.x - x.toDouble()).pow(2) + (other.y - y.toDouble()).pow(2))

    fun neighbors(): List<Point> =
        listOf(Point(x, y + 1), Point(x, y - 1), Point(x + 1, y), Point(x - 1, y))

    fun diagonalNeighbors(): List<Point> =
        listOf(
            Point(x - 1, y + 1),
            Point(x - 1, y - 1),
            Point(x + 1, y + 1),
            Point(x + 1, y - 1)
        )

    companion object {
        @JvmField
        val ORIGIN = Point(0, 0)
    }
}
