package ca.johnmajor.aoc2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day3Test {
    @Test
    fun `part 1`() {
        assertEquals(806, day3().part1())
    }

    @Test
    fun `part 2`() {
        assertEquals(66076, day3().part2())
    }
}
