package ca.johnmajor.aoc2019

import java.io.File

fun Int.toDigits() = toString().map(Char::toInt)

fun day4part1(i: Int): Boolean {
    val ds = i.toDigits().zipWithNext()
    return ds.any { (a, b) -> a == b } && ds.all { (a, b) -> a <= b }
}

fun day4part2(i: Int): Boolean {
    val ds = i.toDigits()
    return ds.zipWithNext().all { (a, b) -> a <= b } &&
            ds.groupBy { it }.any { (_, e) -> e.size == 2 }
}

fun main() {
    val input = File(ClassLoader.getSystemResource("day4-input.txt").file)
        .readText()
        .trim()
        .split("-")
        .map(String::toInt)
    val part1 = (input[0]..input[1]).count(::day4part1)
    println("Part 1 Answer: $part1")

    val part2 = (input[0]..input[1]).count(::day4part2)
    println("Part 2 Answer: $part2")
}
