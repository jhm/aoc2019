package ca.johnmajor.aoc2019

import java.util.stream.IntStream

private fun initialFuel(mass: Int): Int = mass / 3 - 2

private fun totalFuel(mass: Int): Int =
    IntStream.iterate(initialFuel(mass), { it > 0 }, ::initialFuel).sum()

class Day1(private val modules: List<Int>) : Exercise<Int, Int> {
    override fun part1(): Int = modules.sumBy(::initialFuel)
    override fun part2(): Int = modules.sumBy(::totalFuel)
}

fun day1(): Day1 = Day1(Input(1).readLines().map(String::toInt).toList())

fun main() = run(day1())
