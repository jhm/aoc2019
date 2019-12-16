package ca.johnmajor.aoc2019

import kotlin.math.absoluteValue

class Day16(input: String) : Exercise<String, String> {
    private val digits = input.map { it.toInt() - 48 }

    override fun part1(): String {
        val pattern = listOf(0, 1, 0, -1)
        val ds = digits.toIntArray()

        repeat(100) {
            for (i in 1 until ds.size) {
                ds[i - 1] = (i..ds.size).fold(0) { sum, n ->
                    sum + ds[n - 1] * pattern[(n / i) % 4]
                }.absoluteValue % 10
            }
        }
        return ds.take(8).joinToString("")
    }

    override fun part2(): String {
        val offset = digits.take(7).fold(0) { a, d -> 10 * a + d }
        val signal = digits.cycle()
            .drop(offset)
            .take(10000 * digits.size - offset)
            .toMutableList()

        repeat(100) {
            for (i in (signal.size - 2) downTo 0) {
                // The multiplier will always be 1.
                signal[i] = (signal[i] + signal[i + 1]) % 10
            }
        }
        return signal.subList(0, 8).joinToString("")
    }

    private fun <T> List<T>.cycle(): Sequence<T> = sequence {
        while (true) {
            yieldAll(iterator())
        }
    }
}

fun day16(): Day16 = Day16(Input(16).readText().trim())

fun main() = run(day16())
