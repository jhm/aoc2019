package ca.johnmajor.aoc2019

import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread
import kotlin.math.max
import kotlin.math.min

class Day11(private val program: List<Long>) : Exercise<Long, String> {
    override fun part1(): Long = run(0).size.toLong()

    override fun part2(): String {
        val grid: Map<Point, Long> = run(1)
        val (xmin, xmax, ymin, ymax) = grid.keys.fold(arrayOf(0, 0, 0, 0)) { acc, point ->
            acc[0] = min(point.x, acc[0])
            acc[1] = max(point.x, acc[1])
            acc[2] = min(point.y, acc[2])
            acc[3] = max(point.y, acc[3])
            acc
        }

        return "\n" + (ymax downTo ymin).joinToString("\n") { y ->
            (xmin..xmax).joinToString("") { x ->
                val point = Point(x, y)
                if (grid[point] == 1L) "\u2588" else " "
            }
        }
    }

    fun run(start: Long): Map<Point, Long> {
        val input = LinkedBlockingQueue<Long>()
        val output = LinkedBlockingQueue<Long>()
        val controller = IntcodeVM(ArrayList(ArrayList(program)))
        val grid = mutableMapOf(Point(0, 0) to start)

        var direction = CardinalDirection.NORTH
        var current = Point(0, 0)

        val thread = thread { controller.run(input::take, output::put) }
        input.put(start)
        var color = output.take()

        while (color != Long.MIN_VALUE) {
            grid[current] = color
            direction = direction.rotate(output.take())
            current = current.translate(direction, 1)
            input.put(grid.getOrDefault(current, 0))
            color = output.take()
        }
        thread.join()
        return grid
    }

    private fun CardinalDirection.rotate(l: Long): CardinalDirection =
        if (l == 0L) counterClockwise() else clockwise()
}

fun day11(): Day11 {
    val program = Input(11).readText()
        .trim()
        .split(",")
        .map(String::toLong)
    return Day11(program)
}

fun main() = run(day11())