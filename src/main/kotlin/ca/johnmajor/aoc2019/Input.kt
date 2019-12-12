package ca.johnmajor.aoc2019

import java.io.File

class Input(val day: Int) {
    fun readText() =
        File(ClassLoader.getSystemResource("day$day-input.txt").file).readText()

    fun readLines() =
        File(ClassLoader.getSystemResource("day$day-input.txt").file).readLines()
}
