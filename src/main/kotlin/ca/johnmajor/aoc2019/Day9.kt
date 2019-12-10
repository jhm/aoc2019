package ca.johnmajor.aoc2019

import java.io.File

fun day9part1(input: ArrayList<Long>): List<Long> {
    val output = mutableListOf<Long>()
    IntcodeVM(input).run({ 1L }, { output.add(it) })
    return output
}

fun day9part2(input: ArrayList<Long>): List<Long> {
    val output = mutableListOf<Long>()
    IntcodeVM(input).run({ 2L }, { output.add(it) })
    return output
}

fun main() {
    val input = File(ClassLoader.getSystemResource("day9-input.txt").file)
        .readText()
        .trim()
        .split(",")
        .map(String::toLong)

    val part1 = day9part1(ArrayList(input)).joinToString(",")
    println("Part 1 Answer: $part1") // 3780860499

    val part2 = day9part2(ArrayList(input)).joinToString(",")
    println("Part 2 Answer: $part2") // 33343
}
