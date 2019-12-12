package ca.johnmajor.aoc2019

class Day4(private val start: Int, private val end: Int) : Exercise<Int, Int> {
    override fun part1(): Int =
        (start..end).count {
            val ds = it.toDigits().zipWithNext()
            ds.any { (a, b) -> a == b } && ds.all { (a, b) -> a <= b }
        }

    override fun part2(): Int =
        (start..end).count {
            val ds = it.toDigits()
            ds.zipWithNext().all { (a, b) -> a <= b } &&
                    ds.groupBy { it }.any { (_, e) -> e.size == 2 }
        }
}

fun Int.toDigits() = toString().map(Char::toInt)

fun day4(): Day4 {
    val input = Input(4).readText()
        .trim()
        .split("-")
        .map(String::toInt)
    return Day4(input[0], input[1])
}

fun main() = run(day4())

