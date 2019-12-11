package ca.johnmajor.aoc2019

import java.io.File

fun List<CardinalMove>.allPoints(start: Point = Point(0, 0)): Sequence<Point> =
    sequence {
        if (isNotEmpty()) {
            var current = start
            yield(current)
            forEach { move ->
                repeat(move.distance) {
                    current = current.translate(CardinalMove(move.cardinalDirection, 1))
                    yield(current)
                }
            }
        }
    }

fun main() {
    val input = File(ClassLoader.getSystemResource("day3-input.txt").file)
        .readLines()
        .map { it.trim().split(",").map { s -> CardinalMove.from(s)!! }.allPoints() }

    val xs = input[0]
    val ys = input[1]
    val intersects = xs.toSet().intersect(ys.toSet()).drop(1)

    val part1 = intersects.map { it.manhattanDistance() }.min()
    println("Part 1 Answer: $part1") // 806

    val part2 = intersects.map { xs.indexOf(it) + ys.indexOf(it) }.min()
    println("Part 2 Answer: $part2") // 66076
}
