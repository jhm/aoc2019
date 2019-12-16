package ca.johnmajor.aoc2019

import java.util.concurrent.LinkedBlockingQueue

class Day13(private val program: List<Long>) : Exercise<Int, Int> {
    override fun part1(): Int {
        val output = IntcodeVM(ArrayList(program)) { 0L }.run()
        return output.takeWhile { it != Long.MIN_VALUE }
            .chunked(3)
            .count { it[2] == 2L }
    }

    override fun part2(): Int {
        val program = ArrayList(program)
        program[0] = 2
        val input = LinkedBlockingQueue<Long>()
        val vm = IntcodeVM(program, input::take)

        var score = 0L
        var paddle = 0L

        while (true) {
            val x = vm.nextOutput() ?: break
            val y = vm.nextOutput() ?: break
            val type = vm.nextOutput() ?: break

            if (x == -1L && y == 0L) {
                score = type
            } else if (type == 3L) {
                paddle = x
            } else if (type == 4L) {
                input.put(
                    when {
                        x > paddle -> 1
                        x < paddle -> -1
                        else -> 0
                    }
                )
            }
        }
        return score.toInt()
    }
}

fun day13(): Day13 {
    val program = Input(13).readText()
        .trim()
        .split(",")
        .map(String::toLong)
    return Day13(program)
}

fun main() = run(day13())
