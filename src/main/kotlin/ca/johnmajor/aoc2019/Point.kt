package ca.johnmajor.aoc2019

import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt

data class Point(val x: Int, val y: Int) {
    fun translate(direction: CardinalDirection, distance: Int) =
        when (direction) {
            CardinalDirection.NORTH -> Point(x, y + distance)
            CardinalDirection.EAST -> Point(x + distance, y)
            CardinalDirection.SOUTH -> Point(x, y - distance)
            CardinalDirection.WEST -> Point(x - distance, y)
        }

    fun translate(move: CardinalMove): Point =
        translate(move.direction, move.distance)

    fun manhattanDistance(from: Point = Point(0, 0)) = abs(x - from.x) + abs(y - from.y)

    fun distance(other: Point): Double =
        sqrt((other.x - x.toDouble()).pow(2) + (other.y - y.toDouble()).pow(2))

    fun angle(other: Point): Double =
        atan2((other.x - x).toDouble(), (other.y - y).toDouble())
}
