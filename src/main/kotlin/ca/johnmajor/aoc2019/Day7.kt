package ca.johnmajor.aoc2019

import java.io.File
import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread

class Amplifiers(private val mem: ArrayList<Long>) {
    fun run(settings: List<Long>): Long {
        val queues = (settings.indices).map {
            LinkedBlockingQueue<Long>()
        }
        val threads = (settings.indices).map { i ->
            queues[i].put(settings[i])
            thread {
                IntcodeVM(ArrayList(mem)).run(queues[i]::take, queues[(i + 1) % settings.size]::put)
            }
        }
        queues[0].put(0L)
        threads.forEach { it.join() }
        return queues[0].take()
    }

    fun runPermutations(settings: List<Long>): List<Long> =
        settings.permutations().map(::run)
}

fun day7part1(mem: ArrayList<Long>): Long? =
    Amplifiers(mem).runPermutations(listOf(0, 1, 2, 3, 4)).max()

fun day7part2(mem: ArrayList<Long>): Long? =
    Amplifiers(mem).runPermutations(listOf(5, 6, 7, 8, 9)).max()

fun main() {
    val input = File(ClassLoader.getSystemResource("day7-input.txt").file)
        .readText()
        .trim()
        .split(",")
        .map(String::toLong)

    val part1 = day7part1(ArrayList(input))
    println("Part 1 Answer: $part1") // 95757

    val part2 = day7part2(ArrayList(input))
    println("Part 2 Answer: $part2") // 4275738
}
