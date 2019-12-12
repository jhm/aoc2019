package ca.johnmajor.aoc2019

class Day8(
    private val image: String,
    private val width: Int = 25,
    private val height: Int = 6
) : Exercise<Int?, String> {

    private val capacity = width * height

    override fun part1(): Int? =
        image.chunked(capacity).minBy { it.count { c -> c == '0' } }?.let {
            it.count { c -> c == '1' } * it.count { c -> c == '2' }
        }

    override fun part2(): String {
        val layers = image.chunked(capacity)
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
        return "\n" + result.chunked(width)
            .joinToString("\n")
            .replace('0', ' ')
            .replace('1', '\u2588')
    }
}

fun day8(): Day8 = Day8(Input(8).readText().trim())

fun main() = run(day8())

