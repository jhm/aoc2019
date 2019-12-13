package ca.johnmajor.aoc2019

import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread

class Day13(private val program: List<Long>) : Exercise<Int, Int> {
    override fun part1(): Int {
        val output = mutableListOf<Long>()
        IntcodeVM(ArrayList(program)).run({ 0L }, { output.add(it) })
        return output.takeWhile { it != Long.MIN_VALUE }
            .chunked(3)
            .count { it[2] == 2L }
    }

    override fun part2(): Int {
        val program = ArrayList(program)
        program[0] = 2
        val vm = IntcodeVM(program)
        val input = LinkedBlockingQueue<Long>()
        val output = LinkedBlockingQueue<Long>()

        var score = 0L
        var paddle = 0L

        val thread = thread { vm.run(input::take, output::put) }

        while (true) {
            val x = output.take()
            if (x == Long.MIN_VALUE)
                break
            val y = output.take()
            val type = output.take()

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
        thread.join()
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
