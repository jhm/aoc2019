package ca.johnmajor.aoc2019

class Day3(m1: List<CardinalMove>, m2: List<CardinalMove>) : Exercise<Int?, Int?> {
    private val p1 = m1.points()
    private val p2 = m2.points()
    private val intersects = p1.toSet().intersect(p2.toSet()).drop(1)

    override fun part1(): Int? =
        intersects.map { it.manhattanDistance() }.min()

    override fun part2(): Int? =
        intersects.map { p1.indexOf(it) + p2.indexOf(it) }.min()
}

private fun List<CardinalMove>.points(start: Point = Point(0, 0)): Sequence<Point> =
    sequence {
        if (isNotEmpty()) {
            var current = start
            yield(current)
            forEach { move ->
                repeat(move.distance) {
                    current = current.translate(move.direction, 1)
                    yield(current)
                }
            }
        }
    }

fun day3(): Day3 {
    val moves = Input(3).readLines().map {
        it.trim().split(",").map { s -> CardinalMove.from(s)!! }
    }
    return Day3(moves[0], moves[1])
}

fun main() = run(day3())
