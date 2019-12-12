package ca.johnmajor.aoc2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day1Test {
    @Test
    fun `part 1`() {
        assertEquals(3226407, day1().part1())
    }

    @Test
    fun `part 1 examples`() {
        assertEquals(2, Day1(listOf(12)).part1())
        assertEquals(2, Day1(listOf(12)).part1())
        assertEquals(654, Day1(listOf(1969)).part1())
        assertEquals(33583, Day1(listOf(100756)).part1())
    }

    @Test
    fun `part 2`() {
        assertEquals(4836738, day1().part2())
    }

    @Test
    fun `part 2 examples`() {
        assertEquals(2, Day1(listOf(14)).part2())
        assertEquals(966, Day1(listOf(1969)).part2())
        assertEquals(50346, Day1(listOf(100756)).part2())
    }
}
