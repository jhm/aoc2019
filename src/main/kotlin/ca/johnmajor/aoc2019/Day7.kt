package ca.johnmajor.aoc2019

import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread

class Day7(private val program: List<Long>) : Exercise<Long?, Long?> {
    override fun part1(): Long? = runPermutations(listOf(0, 1, 2, 3, 4)).max()

    override fun part2(): Long? = runPermutations(listOf(5, 6, 7, 8, 9)).max()

    private fun run(settings: List<Long>): Long {
        val queues = (settings.indices).map {
            LinkedBlockingQueue<Long>()
        }
        val threads = (settings.indices).map { i ->
            queues[i].put(settings[i])
            thread {
                IntcodeVM(ArrayList(program), queues[i]::take).run()
                    .forEach(queues[(i + 1) % settings.size]::put)
            }
        }
        queues[0].put(0L)
        threads.forEach(Thread::join)
        return queues[0].take()
    }

    private fun runPermutations(settings: List<Long>): List<Long> =
        settings.permutations().map(::run)
}

fun day7(): Day7 {
    val program = Input(7).readText()
        .trim()
        .split(",")
        .map(String::toLong)
    return Day7(program)
}

fun main() = run(day7())

