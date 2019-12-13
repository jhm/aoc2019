package ca.johnmajor.aoc2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day13Test {
    @Test
    fun `part 1`() {
        assertEquals(335, day13().part1())
    }

    @Test
    fun `part 2`() {
        assertEquals(15706, day13().part2())
    }
}
