package ca.johnmajor.aoc2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day22Test {
    @Test
    fun `part 1`() {
        assertEquals(1867, day22().part1())
    }

    @Test
    fun `part 2`() {
        assertEquals(71047285772808, day22().part2())
    }
}
