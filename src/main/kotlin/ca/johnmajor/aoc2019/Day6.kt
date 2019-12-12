package ca.johnmajor.aoc2019

class Day6(private val edges: Map<String, String>) : Exercise<Int, Int> {
    override fun part1(): Int = orbits().values.sumBy { it.size }

    override fun part2(): Int {
        val orbits = orbits()
        val from = orbits["YOU"] ?: error("YOU not found in input.")
        val end = orbits["SAN"] ?: error("SAN not found in input.")
        val transfers = (from union end) subtract (from intersect end)
        return transfers.size
    }

    private fun orbits(): Map<String, List<String>> {
        val result = mutableMapOf<String, List<String>>()
        for (orbiter in edges.keys) {
            result.computeIfAbsent(orbiter) { path(orbiter) }
            // There is a simple optimization opportunity here as we could add all
            // the intermediate paths to avoid recomputing them. However, in this
            // case the input is so small that it doesn't really matter.
        }
        return result
    }

    private fun path(from: String): List<String> {
        val result = mutableListOf<String>()
        var next = edges[from]
        while (next != null) {
            result.add(next)
            next = edges[next]
        }
        return result
    }
}

fun day6(): Day6 {
    val edges = Input(6).readLines()
        .map { it.split(")") }
        .associate { it[1] to it[0] }
    return Day6(edges)
}

fun main() = run(day6())
