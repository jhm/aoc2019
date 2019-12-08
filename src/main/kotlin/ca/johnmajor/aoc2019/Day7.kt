package ca.johnmajor.aoc2019

import java.io.File
import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread

class Amplifiers(private val mem: IntArray) {
    fun run(settings: List<Int>): Int {
        val queues = (settings.indices).map { LinkedBlockingQueue<Int>() }
        val threads = (settings.indices).map { i ->
            queues[i].put(settings[i])
            thread {
                IntcodeVM(mem.clone()).run(queues[i]::take, queues[(i + 1) % settings.size]::put)
            }
        }
        queues[0].put(0)
        threads.forEach { it.join() }
        return queues[0].take()
    }

    fun runPermutations(settings: List<Int>): List<Int> =
        settings.permutations().map(::run)
}

fun day7part1(mem: IntArray): Int? =
    Amplifiers(mem).runPermutations(listOf(0, 1, 2, 3, 4)).max()

fun day7part2(mem: IntArray): Int? =
    Amplifiers(mem).runPermutations(listOf(5, 6, 7, 8, 9)).max()

fun main() {
    val input = File(ClassLoader.getSystemResource("day7-input.txt").file)
        .readText()
        .trim()
        .split(",")
        .map(String::toInt)
        .toIntArray()

    val part1 = day7part1(input)
    println("Part 1 Answer: $part1") // 95757

    val part2 = day7part2(input)
    println("Part 2 Answer: $part2") // 4275738
}
