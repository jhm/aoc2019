package ca.johnmajor.aoc2019

interface Exercise<A, B> {
    fun part1(): A
    fun part2(): B
}

fun <A, B> run(exercise: Exercise<A, B>) {
    val name = "Day ${exercise.javaClass.simpleName.replace("Day", "")}"
    println(name)
    println("=".repeat(name.length))
    println("Part 1: ${exercise.part1()}")
    println("Part 2: ${exercise.part2()}\n")
}

fun runAll() {
    run(day1())
    run(day2())
    run(day3())
    run(day4())
    run(day5())
    run(day6())
    run(day7())
    run(day8())
    run(day9())
    run(day10())
    run(day11())
    run(day12())
    run(day13())
    run(day14())
    run(day15())
    run(day16())
}

fun main() = runAll()
