package ca.johnmajor.aoc2019

import java.io.File

fun day2part1(input: IntArray, noun: Int = 12, verb: Int = 2): Int {
    input[1] = noun
    input[2] = verb
    var pos = 0
    while (input[pos] != 99) {
        when (input[pos]) {
            1 -> input[input[pos + 3]] = input[input[pos + 1]] + input[input[pos + 2]]
            2 -> input[input[pos + 3]] = input[input[pos + 1]] * input[input[pos + 2]]
        }
        pos += 4
    }
    return input[0]
}

fun day2part2(input: IntArray): Int? {
    for (noun in 1..99) {
        for (verb in 1..99) {
            if (day2part1(input.clone(), noun, verb) == 19690720) {
                return 100 * noun + verb
            }
        }
    }
    return null
}

fun main() {
    val input = File(ClassLoader.getSystemResource("day2-input.txt").file)
        .readText()
        .trim()
        .split(",")
        .map(String::toInt)
        .toIntArray()
    println("Part 1 Answer: ${day2part1(input.clone())}") // 3267740
    println("Part 2 Answer: ${day2part2(input.clone()) ?: 0}") // 7870
}

