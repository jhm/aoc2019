package ca.johnmajor.aoc2019

import java.io.File

fun main() {
    val input = File(ClassLoader.getSystemResource("day5-input.txt").file)
        .readText()
        .trim()
        .split(",")
        .map(String::toInt)

    var part1: Int? = null
    IntcodeVM(input.toIntArray()).run({ 1 }, { part1 = it }) // 9219874
    println("Part 1 Answer: $part1")

    var part2: Int? = null
    IntcodeVM(input.toIntArray()).run({ 5 }, { part2 = it }) // 5893654
    println("Part 2 Answer: $part2")
}
