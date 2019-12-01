package ca.johnmajor.aoc2019

import java.io.File
import java.util.stream.IntStream

fun calculateFuel(mass: Int): Int = mass / 3 - 2

fun calculateTotalFuel(mass: Int): Int =
    IntStream.iterate(calculateFuel(mass), { it > 0 }, ::calculateFuel).sum()

fun main() {
    val sum = File(ClassLoader.getSystemResource("day1-input.txt").file).useLines {
        it.map(String::toInt).fold(0 to 0) { (p1, p2), mass ->
            p1 + calculateFuel(mass) to p2 + calculateTotalFuel(mass)
        }
    }
    println("Part 1 Answer: ${sum.first}\nPart 2 Answer: ${sum.second}")
}
