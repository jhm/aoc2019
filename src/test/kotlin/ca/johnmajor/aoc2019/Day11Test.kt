package ca.johnmajor.aoc2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day11Test {
    @Test
    fun `part 1`() {
        assertEquals(2373, day11().part1())
    }

    @Test
    fun `part 2`() {
        val expected = "\n" +
                " ███   ██  █  █ ███  █    ███  █  █ █  █   \n" +
                " █  █ █  █ █ █  █  █ █    █  █ █  █ █ █    \n" +
                " █  █ █    ██   █  █ █    █  █ █  █ ██     \n" +
                " ███  █    █ █  ███  █    ███  █  █ █ █    \n" +
                " █    █  █ █ █  █ █  █    █    █  █ █ █    \n" +
                " █     ██  █  █ █  █ ████ █     ██  █  █   "
        assertEquals(expected, day11().part2())
    }
}
