package ca.johnmajor.aoc2019

import kotlin.math.abs
import kotlin.math.sign

class Day12(private val moons: List<Moon>) : Exercise<Int, Long> {
    override fun part1(): Int = run().drop(999).first().sumBy { it.energy() }

    override fun part2(): Long {
        val periods = Array(3) { false to mutableSetOf<List<Pair<Int, Int>>>() }
        for (moons in run()) {
            for (i in periods.indices) {
                if (!periods[i].first &&
                    !periods[i].second.add(moons.map { it.position[i] to it.velocity[i] })
                ) {
                    periods[i] = periods[i].copy(true)
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

    private operator fun Vec3.get(i: Int): Int =
        when (i) {
            0 -> x
            1 -> y
            2 -> z
            else -> error("Invalid index for Vec3: $i")
        }
}

data class Vec3(val x: Int, val y: Int, val z: Int) {
    operator fun plus(other: Vec3): Vec3 = Vec3(x + other.x, y + other.y, z + other.z)

    companion object {
        private val NUMBER_REGEX = """-?\d+""".toRegex()

        fun parse(line: String): Vec3? {
            val numbers = NUMBER_REGEX.findAll(line)
                .take(3)
                .map { it.value.toInt() }
                .toList()

            val x = numbers.getOrNull(0)
            val y = numbers.getOrNull(1)
            val z = numbers.getOrNull(2)
            return if (x != null && y != null && z != null) {
                Vec3(x, y, z)
            } else {
                null
            }
        }
    }
}

data class Moon(val position: Vec3, val velocity: Vec3 = Vec3(0, 0, 0)) {
    constructor(x: Int, y: Int, z: Int) : this(Vec3(x, y, z))

    fun potentialEnergy(): Int =
        abs(position.x) + abs(position.y) + abs(position.z)

    fun kineticEnergy(): Int =
        abs(velocity.x) + abs(velocity.y) + abs(velocity.z)

    fun energy(): Int = potentialEnergy() * kineticEnergy()

    fun move(neighbors: List<Moon>): Moon {
        val velocity = neighbors.fold(velocity) { v, moon ->
            v + position.velocityChange(moon.position)
        }
        return Moon(position + velocity, velocity)
    }

    private fun Vec3.velocityChange(other: Vec3): Vec3 =
        Vec3((other.x - x).sign, (other.y - y).sign, (other.z - z).sign)
}

fun day12(): Day12 {
    val points = Input(12).readLines().map { Moon(Vec3.parse(it)!!) }
    return Day12(points)
}

fun main() = run(day12())

