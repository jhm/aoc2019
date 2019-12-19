package ca.johnmajor.aoc2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day19Test {
    @Test
    fun `part 1`() {
        assertEquals(144, day19().part1())
    }

    @Test
    fun `part 2`() {
        assertEquals(13561537, day19().part2())
    }
}
