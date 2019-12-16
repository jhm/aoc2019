package ca.johnmajor.aoc2019

import kotlin.math.abs
import kotlin.math.sign

class Day12(private val moons: List<Moon>) : Exercise<Int, Long> {
    override fun part1(): Int = run().drop(999).first().sumBy { it.energy() }

    override fun part2(): Long {
        val size = moons.getOrNull(0)?.points?.size
            ?: error("Moons must have at least 1 axis.")

        val periods = Array(size) {
            false to mutableSetOf<List<Pair<Int, Int>>>()
        }

        for (moon in run()) {
            for (i in periods.indices) {
                if (!periods[i].first &&
                    !periods[i].second.add(moon.map { it.points[i] to it.velocity[i] })
                ) {
                    periods[i] = periods[i].copy(first = true)
                }
                if (periods.all { it.first }) {
                    return periods.fold(1L) { a, b -> lcm(a, b.second.size.toLong()) }
                }
            }
        }
        error("Unreachable")
    }

    fun run(): Sequence<List<Moon>> = generateSequence(step(moons), ::step)

    private fun step(xs: List<Moon>): List<Moon> = xs.map { it.move(xs) }
}

data class Moon(val points: List<Int>, val velocity: List<Int> = List(points.size) { 0 }) {
    constructor(vararg points: Int) : this(points.toList())

    fun energy(): Int = points.sumBy(::abs) * velocity.sumBy(::abs)

    fun move(neighbors: List<Moon>): Moon {
        val velocities = points.withIndex().map { (i, point) ->
            velocity.getOrElse(i) { 0 } + neighbors.sumBy {
                (it.points.getOrElse(i) { 0 } - point).sign
            }
        }
        val points = points.withIndex().map { (i, point) ->
            point + velocities.getOrElse(i) { 0 }
        }
        return Moon(points, velocities)
    }

    companion object {
        private val NUMBER_REGEX = """-?\d+""".toRegex()

        fun parse(s: String): Moon? {
            val points = NUMBER_REGEX.findAll(s)
                .map { it.value.toInt() }
                .toList()
            return Moon(points)
        }
    }
}

fun day12(): Day12 {
    val points = Input(12).readLines().map { Moon.parse(it)!! }
    return Day12(points)
}

fun main() = run(day12())

