package ca.johnmajor.aoc2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day17Test {
    @Test
    fun `part 1`() {
        assertEquals(5068, day17().part1())
    }

    @Test
    fun `part 2`() {
        assertEquals(1415975, day17().part2())
    }
}
