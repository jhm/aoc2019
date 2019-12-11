package ca.johnmajor.aoc2019

enum class CardinalDirection {
    NORTH, EAST, SOUTH, WEST;

    companion object {
        fun from(c: Char): CardinalDirection? =
            when (c.toUpperCase()) {
                'N', 'U' -> NORTH
                'E', 'R' -> EAST
                'S', 'D' -> SOUTH
                'W', 'L' -> WEST
                else -> null
            }
    }
}
