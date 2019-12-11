package ca.johnmajor.aoc2019

import java.io.File
import java.util.stream.IntStream

private fun calculateFuel(mass: Int): Int = mass / 3 - 2

private fun calculateTotalFuel(mass: Int): Int =
    IntStream.iterate(calculateFuel(mass), { it > 0 }, ::calculateFuel).sum()

fun day1part1(input: IntArray): Int = input.sumBy(::calculateFuel)

fun day1part2(input: IntArray): Int = input.sumBy(::calculateTotalFuel)

fun main() {
    val input = File(ClassLoader.getSystemResource("day1-input.txt").file)
        .readLines()
        .map(String::toInt)
        .toIntArray()
    println("Part 1 Answer: ${day1part1(input)}") // 3226407
    println("Part 2 Answer: ${day1part2(input)}") // 4836736
}
