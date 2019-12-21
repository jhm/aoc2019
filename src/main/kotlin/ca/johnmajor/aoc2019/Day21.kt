package ca.johnmajor.aoc2019

class Day21(private val program: List<Long>) : Exercise<Long, Long> {
    override fun part1(): Long {
        val script = """
            NOT A T
            NOT B J
            OR T J
            NOT C T
            OR T J
            AND D J
            WALK
        """.trimIndent()
        return run(script)
    }

    override fun part2(): Long {
        val script = """
            NOT A T
            NOT B J
            OR T J
            NOT C T
            OR T J
            AND D J
            NOT E T
            NOT T T
            OR H T
            AND T J
            RUN
        """.trimIndent()
        return run(script)
    }

    private fun run(script: String): Long {
        val input = script.trim().map { it.toLong() }.toMutableList()
        input.add('\n'.toLong())
        return IntcodeVM(ArrayList(program)) { input.removeAt(0) }.run().last()
    }
}

fun day21(): Day21 {
    val program = Input(21).readText()
        .trim()
        .split(",")
        .map(String::toLong)
        .toList()
    return Day21(program)
}

fun main() = run(day21())
