package ca.johnmajor.aoc2019

enum class CardinalDirection {
    NORTH, EAST, SOUTH, WEST;

    fun clockwise(): CardinalDirection =
        when (this) {
            NORTH -> EAST
            EAST -> SOUTH
            SOUTH -> WEST
            WEST -> NORTH
        }

    fun counterClockwise(): CardinalDirection =
        when (this) {
            NORTH -> WEST
            EAST -> NORTH
            SOUTH -> EAST
            WEST -> SOUTH
        }

    fun opposite(): CardinalDirection =
        when (this) {
            NORTH -> SOUTH
            SOUTH -> NORTH
            EAST -> WEST
            WEST -> EAST
        }

    companion object {
        fun from(s: String): CardinalDirection? =
            if (s.isEmpty()) null else from(s.first())

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
