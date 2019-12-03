package ca.johnmajor.aoc2019

import java.io.File
import kotlin.math.abs

enum class Direction {
    RIGHT, LEFT, UP, DOWN;

    companion object {
        fun from(c: Char): Direction? =
            when (c.toUpperCase()) {
                'R' -> RIGHT
                'L' -> LEFT
                'U' -> UP
                'D' -> DOWN
                else -> null
            }
    }
}

data class Move(val direction: Direction, val distance: Int) {
    companion object {
        fun from(s: String): Move? {
            val direction = Direction.from(s.first())
            val distance = s.substring(1).toIntOrNull()
            return direction?.let { distance?.let { it1 -> Move(it, it1) } }
        }
    }
}

data class Point(val x: Int, val y: Int) {
    fun to(move: Move): Point =
        when (move.direction) {
            Direction.RIGHT -> Point(x + move.distance, y)
            Direction.LEFT -> Point(x - move.distance, y)
            Direction.UP -> Point(x, y + move.distance)
            Direction.DOWN -> Point(x, y - move.distance)
        }
    fun manhattan(from: Point = Point(0, 0)) = abs(x - from.x) + abs(y - from.y)
}

fun List<Move>.allPoints(start: Point = Point(0, 0)): Sequence<Point> =
    sequence {
        if (isNotEmpty()) {
            var current = start
            yield(current)
            forEach { move ->
                repeat(move.distance) {
                    current = current.to(Move(move.direction, 1))
                    yield(current)
                }
            }
        }
    }

fun main() {
    val input = File(ClassLoader.getSystemResource("day3-input.txt").file)
        .readLines()
        .map { it.trim().split(",").map { s -> Move.from(s)!! }.allPoints() }

    val xs = input[0]
    val ys = input[1]
    val intersects = xs.toSet().intersect(ys.toSet()).drop(1)

    val part1 = intersects.map { it.manhattan() }.min()
    println("Part 1 Answer: $part1")

    val part2 = intersects.map { xs.indexOf(it) + ys.indexOf(it) }.min()
    println("Part 2 Answer: $part2")
}
