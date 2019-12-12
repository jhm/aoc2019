package ca.johnmajor.aoc2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day7Test {
    @Test
    fun `part 1`() {
        assertEquals(95757, day7().part1())
    }

    @Test
    fun `part 2`() {
        assertEquals(4275738, day7().part2())
    }
}
