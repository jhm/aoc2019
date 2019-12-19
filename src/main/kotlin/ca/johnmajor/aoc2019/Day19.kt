package ca.johnmajor.aoc2019

class Day19(private val program: List<Long>) : Exercise<Long, Long> {
    override fun part1(): Long {
        var sum = 0L
        for (x in 0L until 50) {
            for (y in 0L until 50) {
                sum += if (isPulled(x, y)) 1 else 0
            }
        }
        return sum
    }

    override fun part2(): Long {
        var x = 0L
        // With our input the beam has a break in it near the origin so we
        // just set y so it's passed the break.
        var y = 10L
        while (true) {
            if (isPulled(x, y)) {
                if (isPulled(x + 99, y - 99)) {
                    return 10000 * x + (y - 99)
                } else {
                    y += 1
                }
            } else {
                x += 1
            }
        }
    }

    private fun isPulled(x: Long, y: Long): Boolean {
        val input = mutableListOf(x, y)
        val vm = IntcodeVM(ArrayList(program)) { input.removeAt(0) }
        return vm.nextOutput() == 1L
    }
}

fun day19(): Day19 {
    val program = Input(19).readText().trim().split(",").map(String::toLong)
    return Day19(program)
}

fun main() = run(day19())

