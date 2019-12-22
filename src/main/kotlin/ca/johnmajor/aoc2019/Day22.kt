package ca.johnmajor.aoc2019

import java.math.BigInteger.ONE
import java.math.BigInteger.ZERO

private sealed class Shuffle {
    object NewStack : Shuffle()
    data class Cut(val n: Int) : Shuffle()
    data class Increment(val n: Int) : Shuffle()

    companion object {
        fun parse(instruction: String): Shuffle? =
            when {
                "deal into new stack" in instruction ->
                    NewStack
                "cut" in instruction ->
                    Cut(instruction.split(" ")[1].toInt())
                "deal with increment" in instruction ->
                    Increment(instruction.split(" ")[3].toInt())
                else -> error("Error parsing instruction.")
            }
    }
}

class Day22(instructions: List<String>) : Exercise<Int, Long> {
    private val instructions = instructions.map { Shuffle.parse(it)!! }

    override fun part1(): Int {
        val cards = 10007
        return instructions.fold(2019) { i, instruction ->
            when (instruction) {
                is Shuffle.NewStack -> cards - i - 1
                is Shuffle.Cut -> (i - instruction.n) % cards
                is Shuffle.Increment -> (i * instruction.n) % cards
            }
        }
    }

    // https://en.wikipedia.org/wiki/Modular_multiplicative_inverse
    // https://en.wikipedia.org/wiki/Fermat%27s_little_theorem
    // https://en.wikipedia.org/wiki/Geometric_series
    override fun part2(): Long {
        val i = 2020.toBigInteger()
        val cards = 119315717514047.toBigInteger()
        val iterations = 101741582076661.toBigInteger()

        val (mul, off) = instructions.fold(ONE to ZERO) { (mul, off), instruction ->
            when (instruction) {
                is Shuffle.NewStack -> {
                    val newMul = mul * (-1).toBigInteger()
                    newMul to (off + newMul)
                }
                is Shuffle.Cut -> {
                    mul to (off + instruction.n.toBigInteger() * mul)
                }
                is Shuffle.Increment -> {
                    (mul * instruction.n.toBigInteger().modInverse(cards)) to off
                }
            }
        }
        val increment = mul.modPow(iterations, cards)
        val offset = off * (ONE - mul.modPow(iterations, cards)) * (ONE - mul).modInverse(cards)
        return ((offset + i * increment) % cards).toLong()
    }
}

fun day22(): Day22 = Day22(Input(22).readLines())

fun main() = run(day22())
