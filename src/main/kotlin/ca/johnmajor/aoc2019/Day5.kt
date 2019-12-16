package ca.johnmajor.aoc2019

class Day5(private val program: List<Long>) : Exercise<Long?, Long?> {
    override fun part1(): Long? =
        IntcodeVM(ArrayList(program)) { 1L }.runUntilHalt().last()

    override fun part2(): Long? =
        IntcodeVM(ArrayList(program)) { 5L }.runUntilHalt().last()
}

fun day5(): Day5 {
    val input = Input(5).readText().trim().split(",").map(String::toLong)
    return Day5(input)
}

fun main() = run(day5())
