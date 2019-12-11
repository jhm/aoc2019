package ca.johnmajor.aoc2019

import java.io.File
import kotlin.math.abs

class Day10(private val asteroids: List<Point>) {
    fun part1(): Int? = mostVisible()?.second

    fun part2(): Int? {
        return mostVisible()?.first?.let { station ->
            val sortedByAngle = asteroids.asSequence()
                .filter { it != station }
                .groupBy { station.angle(it) }
                .map { (angle, asteroids) -> angle to asteroids.sortedBy { station.distance(it) }.toMutableList() }
                .sortedByDescending { it.first }
                .map { it.second }

            var p: Point? = null
            for (i in 0 until 200) {
                val ys = sortedByAngle[i % sortedByAngle.size]
                if (ys.isNotEmpty()) {
                    p = ys.removeAt(0)
                }
            }
            p?.let { it.x * 100 + it.y }
        }
    }

    private fun mostVisible(): Pair<Point, Int>? =
        asteroids.map {
            it to (countVisible(it, asteroids - setOf(it)) ?: 0)
        }.maxBy { it.second }

    private fun countVisible(from: Point, to: List<Point>): Int? =
        to.map {
            val dx = it.x - from.x
            val dy = it.y - from.y
            val d = abs(gcd(dx, dy))
            dx / d to dy / d
        }.distinct().size

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
