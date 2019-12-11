package ca.johnmajor.aoc2019

data class CardinalMove(val cardinalDirection: CardinalDirection, val distance: Int) {
    companion object {
        fun from(s: String): CardinalMove? {
            val direction = CardinalDirection.from(s)
            val distance = s.substring(1).toIntOrNull()
            return direction?.let { distance?.let { it1 -> CardinalMove(it, it1) } }
        }
    }
}
