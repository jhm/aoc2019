package ca.johnmajor.aoc2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day12Test {
    @Test
    fun `part 1`() {
        assertEquals(6849, day12().part1())
    }

    @Test
    fun `part 1 10 steps`() {
        val start = listOf(
            Moon(-8, -10, 0),
            Moon(5, 5, 10),
            Moon(2, -7, 3),
            Moon(9, -8, -3)
        )
        val expected = listOf(
            Moon(Vec3(-9, -10, 1), Vec3(-2, -2, -1)),
            Moon(Vec3(4, 10, 9), Vec3(-3, 7, -2)),
            Moon(Vec3(8, -10, -3), Vec3(5, -1, -2)),
            Moon(Vec3(5, -10, 3), Vec3(0, -4, 5))
        )
        assertEquals(expected, Day12(start).run().drop(9).first())
    }

    @Test
    fun `part 2`() {
        assertEquals(356658899375688L, day12().part2())
    }

    @Test
    fun `part 2 example`() {
        val moons = listOf(
            Moon(-1, 0, 2),
            Moon(2, -10, -7),
            Moon(4, -8, 8),
            Moon(3, 5, -1)
        )
        assertEquals(2772, Day12(moons).part2())
    }

    @Test
    fun `part 2 long example`() {
        val moons = listOf(
            Moon(-8, -10, 0),
            Moon(5, 5, 10),
            Moon(2, -7, 3),
            Moon(9, -8, -3)
        )
        assertEquals(4686774924, Day12(moons).part2())
    }

    @Test
    fun `parses an input line`() {
        assertEquals(Vec3(-1, 0, 2), Vec3.parse("<x=-1, y=0, z=2>"))
        assertEquals(Vec3(2, -10, -7), Vec3.parse("<x=2, y=-10, z=-7>"))
        assertEquals(Vec3(4, -8, 8), Vec3.parse("<x=4, y=-8, z=8>"))
        assertEquals(Vec3(3, 5, -1), Vec3.parse("<x=3, y=5, z=-1>"))
    }
}
