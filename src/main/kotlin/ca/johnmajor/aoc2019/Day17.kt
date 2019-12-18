package ca.johnmajor.aoc2019

class Day17(private val program: List<Long>) : Exercise<Int, Int> {

    private val lines by lazy { buildLines() }

    override fun part1(): Int {
        var sum = 0
        for (y in lines.indices) {
            for (x in lines[y].indices) {
                if (Point(x, y).isIntersection()) {
                    sum += x * (lines.size - 1 - y)
                }
            }
        }
        return sum
    }

    override fun part2(): Int {
        val program = ArrayList(program)
        program[0] = 2L
        // Solved routine manually by looking at the path given by the path
        // function below.
        val routine = "A,C,A,A,C,B,C,B,B,C\n"
        val a = "L,12,R,8,L,6,R,8,L,6\n"
        val b = "L,6,R,6,L,12\n"
        val c = "R,8,L,12,L,12,R,8\n"
        val input = listOf(routine, a, b, c, "n\n").flatMap {
            it.map(Char::toLong)
        }.toMutableList()
        return IntcodeVM(program) { input.removeAt(0) }.run().last().toInt()
    }

    private fun buildLines(): List<List<Long>> {
        val lines = mutableListOf<List<Long>>()
        val line = mutableListOf<Long>()
        for (i in IntcodeVM(ArrayList(program)) { 0L }.run()) {
            if (i == 10L) {
                lines.add(line.toList())
                line.clear()
            } else {
                line.add(i)
            }
        }
        return lines.reversed()
    }

    private fun Point.isIntersection(): Boolean =
        isScaffold() && neighbors().all { it.isScaffold() }

    private fun Point.isScaffold(): Boolean =
        y >= 0 && y < lines.size && x >= 0 && x < lines[y].size && lines[y][x] == 35L

    private fun findRobot(): Point? {
        for (y in lines.indices) {
            for (x in lines[y].indices) {
                if (lines[y][x] == 94L) {
                    return Point(x, y)
                }
            }
        }
        return null
    }

    // Find a path by continuing until there's no more scaffolding and we have
    // to turn.
    private fun path(): List<Pair<Char, Int>> {
        val path = mutableListOf<Pair<Char, Int>>()

        var position = findRobot()
        var direction = CardinalDirection.NORTH
        var distance = 0
        var turn = '-'

        while (position != null) {
            position =
                if (position.move(direction).isScaffold()) {
                    distance += 1
                    position.move(direction)
                } else {
                    if (distance != 0) {
                        path.add(turn to distance)
                    }
                    distance = 1
                    when {
                        position.move(direction.left()).isScaffold() -> {
                            turn = 'L'
                            direction = direction.left()
                            position.move(direction)
                        }
                        position.move(direction.right()).isScaffold() -> {
                            turn = 'R'
                            direction = direction.right()
                            position.move(direction, 1)
                        }
                        else -> null
                    }
                }
        }
        return path
    }
}

private fun linesToString(lines: List<List<Long>>): String {
    val sb = StringBuilder()
    for (y in lines.indices) {
        for (x in lines[y].indices) {
            sb.append(lines[y][x].toChar())
        }
        sb.append("\n")
    }
    return sb.toString()
}

fun day17(): Day17 {
    val program = Input(17).readText().trim().split(",").map(String::toLong)
    return Day17(program)
}

fun main() = run(day17())
