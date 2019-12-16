package ca.johnmajor.aoc2019

import java.lang.Integer.max
import java.lang.Integer.min
import java.util.*
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

class Day15(private val maze: Maze) : Exercise<Int, Int> {
    override fun part1(): Int =
        maze.shortestPath() ?: error("Couldn't find path to oxygen.")

    override fun part2(): Int =
        maze.timeToFlood() ?: error("Couldn't find time for oxygen flood.")
}

data class Maze(val points: Map<Point, Tile>, val oxygen: Point) {

    fun shortestPath(): Int? {
        val queue = ArrayDeque<Point>()
        val visited = mutableSetOf<Point>()
        val origins = mutableMapOf<Point, Point>()
        queue.add(Point.ORIGIN)

        while (queue.isNotEmpty()) {
            val position = queue.remove()
            if (position == oxygen) {
                return position.distanceToOrigin(origins)
            }
            if (visited.add(position)) {
                for (neighbor in openNeighbors(position, visited)) {
                    origins[neighbor] = position
                    queue.add(neighbor)
                }
            }
        }
        return null
    }

    fun timeToFlood(): Int? {
        val queue = ArrayDeque<Point>()
        val visited = mutableSetOf<Point>()
        val distance = mutableMapOf<Point, Int>()
        queue.add(oxygen)

        while (queue.isNotEmpty()) {
            val position = queue.remove()
            if (visited.add(position)) {
                for (neighbor in openNeighbors(position, visited)) {
                    distance[neighbor] = distance.getOrDefault(position, 0) + 1
                    queue.add(neighbor)
                }
            }
        }
        return distance.values.max()
    }

    private fun openNeighbors(position: Point, visited: Set<Point>): List<Point> =
        CardinalDirection.values()
            .map { position.translate(it, 1) }
            .filter { it !in visited && points[it] != Tile.WALL }

    fun draw(): String {
        val (xmin, xmax, ymin, ymax) = points.keys.fold(arrayOf(0, 0, 0, 0)) { acc, point ->
            acc[0] = min(acc[0], point.x)
            acc[1] = max(acc[1], point.x)
            acc[2] = min(acc[2], point.y)
            acc[3] = max(acc[3], point.y)
            acc
        }

        val sb = StringBuilder()
        for (y in ymax downTo ymin) {
            for (x in xmin..xmax) {
                sb.append(
                    if (x == 0 && y == 0) {
                        'H'
                    } else {
                        when (points[Point(x, y)]) {
                            Tile.WALL -> "\u2593"
                            Tile.OPEN -> " "
                            Tile.OXYGEN -> "\u2716"
                            else -> '#'
                        }
                    }
                )
            }
            sb.append("\n")
        }
        return sb.toString()
    }

    private fun Point.distanceToOrigin(origins: Map<Point, Point>): Int {
        var distance = 0
        var position = this
        while (origins.containsKey(position)) {
            distance++
            position = origins[position]!!
        }
        return distance
    }

    companion object {
        // Recursive depth first traversal to build the maze.
        fun fromIntcodeProgram(program: List<Long>): Maze {
            val input = LinkedBlockingQueue<Long>()
            val vm = IntcodeVM(ArrayList(program), input::take)

            val map = mutableMapOf<Point, Tile>()
            val visited = mutableSetOf<Point>()
            var oxygen: Point? = null

            fun explore(from: Point, direction: CardinalDirection) {
                val next = from.translate(direction, 1)
                if (next in visited || map[next] == Tile.WALL) {
                    return
                }
                // Attempt to move in the given direction.
                input.put(direction)
                val tile = Tile.from(vm.nextOutput() ?: error("VM halted.")) ?: error("Invalid tile.")
                map[next] = tile

                if (tile == Tile.WALL) {
                    return
                }

                visited.add(next)
                if (tile == Tile.OXYGEN) {
                    oxygen = next
                }
                for (d in CardinalDirection.values()) {
                    explore(next, d)
                }
                visited.remove(from)
                input.put(direction.opposite())
                vm.nextOutput()
            }

            for (direction in CardinalDirection.values()) {
                explore(Point.ORIGIN, direction)
            }
            return Maze(map.toMap(), oxygen ?: error("Oxygen wasn't found."))
        }

        private fun BlockingQueue<Long>.put(direction: CardinalDirection) {
            put(direction.toLong())
        }

        private fun CardinalDirection.toLong(): Long =
            when (this) {
                CardinalDirection.NORTH -> 1
                CardinalDirection.SOUTH -> 2
                CardinalDirection.WEST -> 3
                CardinalDirection.EAST -> 4
            }
    }

    enum class Tile {
        WALL, OPEN, OXYGEN;

        companion object {
            fun from(l: Long): Tile? =
                when (l) {
                    0L -> WALL
                    1L -> OPEN
                    2L -> OXYGEN
                    else -> null
                }
        }
    }
}

fun day15(): Day15 {
    val program = Input(15).readText().trim().split(",").map(String::toLong)
    val maze = Maze.fromIntcodeProgram(program)
    return Day15(maze)
}

fun main() = run(day15())
