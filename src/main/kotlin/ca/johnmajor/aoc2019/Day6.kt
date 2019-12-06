package ca.johnmajor.aoc2019

import java.io.File

private fun calculatePath(from: String, edges: Map<String, String>): List<String> {
    val result = mutableListOf<String>()
    var next = edges[from]
    while (next != null) {
        result.add(next)
        next = edges[next]
    }
    return result
}

private fun calculateOrbits(edges: Map<String, String>): Map<String, List<String>> {
    val result = mutableMapOf<String, List<String>>()
    for (orbiter in edges.keys) {
        result.computeIfAbsent(orbiter) { calculatePath(orbiter, edges)}
        // There is a simple optimization opportunity here as we could add all
        // the intermediate paths to avoid recomputing them. However, in this
        // case the input is so small that it doesn't really matter.
    }
    return result
}

fun main() {
    val input = File(ClassLoader.getSystemResource("day6-input.txt").file)
        .readLines()
        .map { it.split(")") }
        .associate { it[1] to it[0] }

    val orbits = calculateOrbits(input)
    println("Part 1 Answer: ${orbits.values.sumBy { it.size }}") // 387356

    val from = orbits["YOU"] ?: error("YOU not found in input.")
    val end = orbits["SAN"] ?: error("SAN not found in input.")
    val transfers = (from union end) subtract (from intersect end)
    println("Part 2 Answer: ${transfers.size}") // 532
}
