package ca.johnmajor.aoc2019

import java.io.File
import kotlin.math.atan2

class Day10(private val asteroids: List<Point>) {
    fun part1(): Int? = mostVisible()?.second

    fun part2(): Int? {
        return mostVisible()?.first?.let { station ->
            asteroids.asSequence()
                .filter { it != station }
                .groupBy { station.angle(it) }
                .map { (angle, asteroids) -> angle to asteroids.sortedBy { station.distance(it) } }
                .sortedByDescending { it.first }
                .map { it.second }
                .roundRobin()
                .drop(199)
                .firstOrNull()?.let { it.x * 100 + it.y }
        }
    }

    private fun mostVisible(): Pair<Point, Int>? =
        asteroids.map {
            it to (countVisible(it, asteroids - setOf(it)) ?: 0)
        }.maxBy { it.second }

    private fun countVisible(from: Point, to: List<Point>): Int? =
        to.map { from.angle(it) }.distinct().size

    private fun Point.angle(other: Point): Double =
        // The y axis is flipped in our coordinate system.
        atan2((other.x - x).toDouble(), (other.y - y).toDouble())

    private fun <T> Iterable<Iterable<T>>.roundRobin(): Sequence<T> = sequence {
        val its = mapTo(mutableListOf()) { it.iterator() }
        while (its.isNotEmpty()) {
            val rows = its.iterator()
            while (rows.hasNext()) {
                val cols = rows.next()
                if (cols.hasNext()) {
                    yield(cols.next())
                } else {
                    rows.remove()
                }
            }
        }
    }

    companion object {
        fun parseInput(path: String): List<Point> =
            File(ClassLoader.getSystemResource(path).file).useLines { lines ->
                lines.withIndex().flatMap { (y, line) ->
                    line.asSequence()
                        .withIndex()
                        .filter { it.value == '#' }
                        .map { Point(it.index, y) }
                }.toList()
            }
    }
}

fun main() {
    val input = Day10.parseInput("day10-input.txt")
    val exercise = Day10(input)
    println("Part 1: ${exercise.part1()}") // 263
    println("Part 2: ${exercise.part2()}") // 1110
}
