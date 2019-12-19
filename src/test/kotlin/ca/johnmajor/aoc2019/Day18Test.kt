package ca.johnmajor.aoc2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day18Test {
    @Test
    fun `part 1 example 1`() {
        val grid = """
            ########################
            #...............b.C.D.f#
            #.######################
            #.....@.a.B.c.d.A.e.F.g#
            ########################
        """.trimIndent().split("\n")
        assertEquals(132, Day18(grid).part1())
    }

    @Test
    fun `part 1 example 2`() {
        val grid = """
            #################
            #i.G..c...e..H.p#
            ########.########
            #j.A..b...f..D.o#
            ########@########
            #k.E..a...g..B.n#
            ########.########
            #l.F..d...h..C.m#
            #################
        """.trimIndent().split("\n")
        assertEquals(136, Day18(grid).part1())
    }

    @Test
    fun `part 2 example`() {
        val grid = """
            #############
            #DcBa.#.GhKl#
            #.###@#@#I###
            #e#d#####j#k#
            ###C#@#@###J#
            #fEbA.#.FgHi#
            #############
        """.trimIndent().split("\n")
        val starts = findEntrances(grid)
        assertEquals(32, VaultWithSections(grid).shortestPath(starts))
    }

    @Test
    fun `part 1`() {
        assertEquals(3546, day18().part1())
    }

    @Test
    fun `part 2`() {
        assertEquals(1988, day18().part2())
    }
}
