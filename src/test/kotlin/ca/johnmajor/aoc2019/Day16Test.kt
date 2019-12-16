package ca.johnmajor.aoc2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day16Test {
    @Test
    fun `part 1 examples`() {
        assertEquals("24176176", Day16("80871224585914546619083218645595").part1())
        assertEquals("73745418", Day16("19617804207202209144916044189917").part1())
        assertEquals("52432133", Day16("69317163492948606335995924319873").part1())
    }

    @Test
    fun `part 1`() {
        assertEquals("19239468", day16().part1())
    }

    @Test
    fun `part 2 examples`() {
        assertEquals("84462026", Day16("03036732577212944063491565474664").part2())
        assertEquals("78725270", Day16("02935109699940807407585447034323").part2())
        assertEquals("53553731", Day16("03081770884921959731165446850517").part2())
    }
}
