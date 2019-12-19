package ca.johnmajor.aoc2019

import java.util.*

class Day18(private val grid: List<String>) : Exercise<Int, Int> {
    override fun part1(): Int {
        val entrance = findEntrances(grid).firstOrNull() ?: error("No entrances.")
        return Vault(grid).shortestPath(entrance)
    }

    override fun part2(): Int {
        val entrance = findEntrances(grid).firstOrNull() ?: error("No entrances.")
        val vault = VaultWithSections(modifyGrid(entrance))
        return vault.shortestPath(entrance.diagonalNeighbors())
    }

    private fun modifyGrid(entrance: Point): List<String> {
        val (x, y) = entrance
        val modified = grid.toMutableList()
        modified[y] = modifyLine(grid[y], x - 1, "###")
        modified[y + 1] = modifyLine(grid[y + 1], x - 1)
        modified[y - 1] = modifyLine(grid[y - 1], x - 1)
        return modified
    }

    private fun modifyLine(line: String, x: Int, sub: String = "@#@"): String =
        line.substring(0, x) + sub + line.substring(x + sub.length)
}

// Breadth-first search to find all of the reachable keys while keeping
// track of the distance to each key.
private fun reachableKeys(
    grid: List<String>,
    from: Point,
    collected: Set<Char> = emptySet()
): Map<Char, Pair<Point, Int>> {
    val queue = ArrayDeque<Point>()
    queue.add(from)
    val distances = mutableMapOf(from to 0)
    val keys = mutableMapOf<Char, Pair<Point, Int>>()

    while (queue.isNotEmpty()) {
        val position = queue.remove()
        for (neighbor in position.neighbors()) {
            val tile = grid[neighbor.y][neighbor.x]
            if (tile == '#' || neighbor in distances) {
                continue
            }
            distances[neighbor] = distances[position]!! + 1
            if (tile in 'A'..'Z' && tile.toLowerCase() !in collected) {
                continue
            }
            if (tile in 'a'..'z' && tile !in collected) {
                keys[tile] = neighbor to distances[neighbor]!!
            } else {
                queue.add(neighbor)
            }
        }
    }
    return keys
}

class Vault(private val grid: List<String>) {
    // Variation of Dijkstra's shortest path.
    fun shortestPath(
        from: Point,
        collected: Set<Char> = emptySet(),
        cache: MutableMap<Pair<Point, String>, Int> = mutableMapOf()
    ): Int {
        val cacheKey = from to collected.sorted().joinToString()
        cache[cacheKey]?.let { return it }

        val keys = reachableKeys(grid, from, collected)
        val min = keys.map { (key, pd) ->
            val col = collected.toMutableSet()
            col.add(key)
            pd.second + shortestPath(pd.first, col, cache)
        }.min() ?: 0
        cache[cacheKey] = min
        return min
    }
}

class VaultWithSections(private val grid: List<String>) {
    // Look for reachable keys in each subsection of the grid
    private fun reachableKeys(
        from: List<Point>,
        collected: Set<Char>
    ): Map<Char, Triple<Point, Int, Int>> =
        from.withIndex().flatMap { (i, point) ->
            reachableKeys(grid, point, collected).map { (key, pd) ->
                key to Triple(pd.first, pd.second, i)
            }
        }.toMap()

    // There is some unnecessary duplication here between the shortestPath
    // method in each of the Vault classes.
    fun shortestPath(
        from: List<Point>,
        collected: Set<Char> = emptySet(),
        cache: MutableMap<Pair<List<Point>, String>, Int> = mutableMapOf()
    ): Int {
        val cacheKey = from to collected.sorted().joinToString()
        cache[cacheKey]?.let { return it }

        val keys = reachableKeys(from, collected)
        val min = keys.map { (key, pd) ->
            val col = collected.toMutableSet()
            col.add(key)
            val points = from.withIndex().map { (i2, pt) ->
                if (pd.third == i2) pd.first else pt
            }
            pd.second + shortestPath(points, col, cache)
        }.min() ?: 0
        cache[cacheKey] = min
        return min
    }
}

fun findEntrances(grid: List<String>): List<Point> {
    val entrances = mutableListOf<Point>()
    for (y in grid.indices) {
        for (x in grid[y].indices) {
            if (grid[y][x] == '@') {
                entrances.add(Point(x, y))
            }
        }
    }
    return entrances
}

fun day18(): Day18 {
    val grid = Input(18).readText().trim().split("\n")
    return Day18(grid)
}

fun main() = run(day18())
