package ca.johnmajor.aoc2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day9Test {
    @Test
    fun `part 1`() {
        assertEquals(3780860499, day9().part1())
    }

    @Test
    fun `part 2`() {
        assertEquals(33343, day9().part2())
    }
}
