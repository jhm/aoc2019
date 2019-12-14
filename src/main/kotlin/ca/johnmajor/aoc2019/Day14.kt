package ca.johnmajor.aoc2019

import kotlin.math.ceil
import kotlin.math.max

class Day14(private val reactions: Map<String, Reaction>) : Exercise<Long, Long> {
    override fun part1(): Long = minimumOre("FUEL", 1)

    override fun part2(): Long {
        val totalOre = 1_000_000_000_000
        var left = 1L
        var right = totalOre
        var mid = 0L

        while (left <= right) {
            mid = (left + right) / 2
            val requiredOre = minimumOre("FUEL", mid)
            if (requiredOre < totalOre) {
                left = mid + 1
            } else if (requiredOre > totalOre) {
                right = mid - 1
            }
        }
        return mid
    }

    private fun minimumOre(
        name: String,
        amount: Long,
        stockpile: MutableMap<String, Long> = mutableMapOf()
    ): Long {
        val reaction = reactions[name] ?: error("No reaction for $name")
        val need = max(0, amount - stockpile.getOrPut(name) { 0 })
        // The amount of times we need to rerun the reaction to get the required amount.
        val reruns = ceil(need / reaction.produced.toDouble()).toLong()
        // Add the excess to the stockpile.
        stockpile.merge(name, 0) { o, _ -> o + (reruns * reaction.produced - amount) }

        var sum = 0L
        for ((iProduced, iName) in reaction.ingredients) {
            val iNeed = iProduced * reruns
            sum += if (iName == "ORE") iNeed else minimumOre(iName, iNeed, stockpile)
        }
        return sum
    }
}

data class Reaction(val name: String, val produced: Int, val ingredients: List<Pair<Int, String>>) {
    companion object {
        fun parse(s: String): Reaction {
            val parts = s.split(" => ")
            val source = parts[0].split(",").map {
                val ps = it.trim().split(" ", limit = 2)
                ps[0].toInt() to ps[1]
            }
            val output = parts[1].split(" ", limit = 2)
            return Reaction(output[1], output[0].toInt(), source)
        }

        fun parse(xs: List<String>): List<Reaction> = xs.map(::parse)

        fun parseToMap(s: String): Map<String, Reaction> =
            parseToMap(s.split("\n"))

        fun parseToMap(xs: List<String>): Map<String, Reaction> =
            parse(xs).map { it.name to it }.toMap()
    }
}

fun day14(): Day14 {
    val transforms = Reaction.parseToMap(Input(14).readLines())
    return Day14(transforms)
}

fun main() = run(day14())
