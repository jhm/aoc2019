package ca.johnmajor.aoc2019

import java.util.*

class Day20(private val grid: List<String>) : Exercise<Int, Int> {
    override fun part1(): Int =
        WarpMaze.from(grid).shortestPath() ?: error("Couldn't find path.")

    override fun part2(): Int =
        WarpMaze.from(grid).shortestPath(true) ?: error("Couldn't find path.")
}

data class WarpMaze(
    val start: Point,
    val end: Point,
    val grid: List<String>,
    val warps: Map<Point, Point>
) {
    fun shortestPath(recurse: Boolean = false): Int? {
        val queue = ArrayDeque<Triple<Point, Int, Int>>()
        queue.add(Triple(start, 0, 0))
        val cache = mutableMapOf<Pair<Point, Int>, Int>()

        while (queue.isNotEmpty()) {
            val (point, distance, depth) = queue.remove()
            if (point == end && depth == 0) {
                return distance
            }
            if (point to depth in cache) {
                continue
            }
            cache[point to depth] = distance
            for (neighbor in point.neighbors()) {
                if (grid[neighbor.y][neighbor.x] == '.') {
                    queue.add(Triple(neighbor, distance + 1, depth))
                }
            }
            if (point in warps) {
                if (recurse && point.isInner()) {
                    queue.add(Triple(warps[point]!!, distance + 1, depth + 1))
                } else if (recurse && depth > 0) {
                    queue.add(Triple(warps[point]!!, distance + 1, depth - 1))
                } else if (!recurse) {
                    queue.add(Triple(warps[point]!!, distance + 1, depth))
                }
            }
        }
        return null
    }

    private fun Point.isInner(): Boolean =
        y >= 3 && y < grid.size - 3 && x >= 3 && x < grid[y].length - 3

    companion object {
        fun from(grid: List<String>): WarpMaze {
            var start: Point? = null
            var end: Point? = null
            val warps = mutableMapOf<Pair<Char, Char>, MutableList<Point>>()

            for (y in 2 until grid.size - 2) {
                for (x in 2 until grid[y].length - 2) {
                    if (grid[y][x] != '.') {
                        continue
                    }
                    for ((a, b) in listOf(
                        grid[y][x - 2] to grid[y][x - 1],
                        grid[y][x + 1] to grid[y][x + 2],
                        grid[y - 2][x] to grid[y - 1][x],
                        grid[y + 1][x] to grid[y + 2][x]
                    )) {
                        if (a == 'A' && b == 'A') {
                            start = Point(x, y)
                        } else if (a == 'Z' && b == 'Z') {
                            end = Point(x, y)
                        } else if (a.isLetter() && b.isLetter()) {
                            val key = a to b
                            val warp = warps[key] ?: mutableListOf()
                            warp.add(Point(x, y))
                            warps[key] = warp
                        }
                    }
                }
            }
            return WarpMaze(
                start!!,
                end!!,
                grid,
                warps.flatMap { (_, ps) -> listOf(ps[0] to ps[1], ps[1] to ps[0]) }.toMap()
            )
        }
    }
}

fun day20(): Day20 {
    val grid = Input(20).readLines()
    return Day20(grid)
}

fun main() = run(day20())
