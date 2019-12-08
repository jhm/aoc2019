package ca.johnmajor.aoc2019

import java.io.File

fun day8part1(s: String, width: Int = 25, height: Int = 6): Int? {
    val layer = s.chunked(width * height).minBy { it.count { c -> c == '0' } }
    return layer?.let {
        it.count { c -> c == '1' } * it.count { c -> c == '2' }
    }
}

fun day8part2(s: String, width: Int = 25, height: Int = 6): String {
    val capacity = width * height
    val layers = s.chunked(capacity)
    val result = StringBuilder(capacity)

    for (i in 0 until capacity) {
        var color = '2'
        for (layer in layers) {
            color = layer[i]
            if (color != '2')
                break
        }
        result.append(color)
    }

    return result.chunked(width).joinToString("\n")
}

fun main() {
    val input = File(ClassLoader.getSystemResource("day8-input.txt").file)
        .readText()
        .trim()
    println("Part 1: ${day8part1(input)}") // 1463
    println("Part 2:\n${day8part2(input).replace('0', ' ').replace('1', '|')}") // GKCKH
}
