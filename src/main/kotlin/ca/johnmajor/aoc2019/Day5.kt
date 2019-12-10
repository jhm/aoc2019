package ca.johnmajor.aoc2019

import java.io.File

fun main() {
    val input = File(ClassLoader.getSystemResource("day5-input.txt").file)
        .readText()
        .trim()
        .split(",")
        .map(String::toLong)

    var part1: Long? = null
    IntcodeVM(ArrayList(input)).run({ 1L }, { part1 = it }) // 9219874
    println("Part 1 Answer: $part1")

    var part2: Long? = null
    IntcodeVM(ArrayList(input)).run({ 5L }, { part2 = it }) // 5893654
    println("Part 2 Answer: $part2")
}
