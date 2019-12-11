package ca.johnmajor.aoc2019

import java.io.File
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt

class Day10(private val asteroids: List<Asteroid>) {
    fun part1(): Int? = mostVisible()?.second

    fun part2(): Int? {
        return mostVisible()?.first?.let { station ->
            val sortedByAngle = asteroids.asSequence()
                .filter { it != station }
                .groupBy { station.angle(it) }
                .map { (angle, asteroids) -> angle to asteroids.sortedBy { station.distance(it) }.toMutableList() }
                .sortedByDescending { it.first }
                .map { it.second }

            var p: Asteroid? = null
            for (i in 0 until 200) {
                val ys = sortedByAngle[i % sortedByAngle.size]
                if (ys.isNotEmpty()) {
                    p = ys.removeAt(0)
                }
            }
            p?.let { it.x * 100 + it.y }
        }
    }

    private fun mostVisible(): Pair<Asteroid, Int>? =
        asteroids.map {
            it to (it.countVisible(asteroids - setOf(it)) ?: 0)
        }.maxBy { it.second }

    companion object {
        fun parseInput(path: String): List<Asteroid> =
            File(ClassLoader.getSystemResource(path).file).useLines { lines ->
                lines.withIndex().flatMap { (y, line) ->
                    line.asSequence()
                        .withIndex()
                        .filter { it.value == '#' }
                        .map { Asteroid(it.index, y) }
                }.toList()
            }
    }
}

data class Asteroid(val x: Int, val y: Int) {
    fun countVisible(xs: List<Asteroid>): Int? =
        xs.map {
            val dx = it.x - x
            val dy = it.y - y
            val d = abs(gcd(dx, dy))
            dx / d to dy / d
        }.distinct().size

    fun distance(other: Asteroid): Double =
        sqrt((other.x - x.toDouble()).pow(2) + (other.y - y.toDouble()).pow(2))

    fun angle(other: Asteroid): Double =
        atan2((other.x - x).toDouble(), (other.y - y).toDouble())
}

fun main() {
    val input = Day10.parseInput("day10-input.txt")
    val exercise = Day10(input)
    println("Part 1: ${exercise.part1()}") // 263
    println("Part 2: ${exercise.part2()}") // 1110
}
