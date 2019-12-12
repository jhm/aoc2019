package ca.johnmajor.aoc2019

class Day2(private val program: IntArray) : Exercise<Int, Int?> {
    override fun part1(): Int = run(12, 2)

    override fun part2(): Int? {
        for (noun in 1..99) {
            for (verb in 1..99) {
                if (run(noun, verb) == 19690720) {
                    return 100 * noun + verb
                }
            }
        }
        return null
    }

    fun run(noun: Int = 12, verb: Int = 2): Int {
        val p = program.clone()
        p[1] = noun
        p[2] = verb
        var pos = 0
        while (p[pos] != 99) {
            when (p[pos]) {
                1 -> p[p[pos + 3]] = p[p[pos + 1]] + p[p[pos + 2]]
                2 -> p[p[pos + 3]] = p[p[pos + 1]] * p[p[pos + 2]]
            }
            pos += 4
        }
        return p[0]
    }
}

fun day2(): Day2 {
    val program = Input(2).readText()
        .trim()
        .split(",")
        .map(String::toInt)
        .toIntArray()
    return Day2(program)
}

fun main() = run(day2())

