package ca.johnmajor.aoc2019

class Day9(private val program: List<Long>) : Exercise<Long?, Long?> {
    override fun part1(): Long? =
        IntcodeVM(ArrayList(program)) { 1L }.runUntilHalt().last()

    override fun part2(): Long? =
        IntcodeVM(ArrayList(program)) { 2L }.runUntilHalt().last()
}

fun day9(): Day9 {
    val program = Input(9).readText()
        .trim()
        .split(",")
        .map(String::toLong)
    return Day9(program)
}

fun main() = run(day9())
