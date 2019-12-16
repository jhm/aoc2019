package ca.johnmajor.aoc2019

import kotlin.math.pow

enum class Opcode(val opcode: Int) {
    ADD(1),
    MUL(2),
    SAV(3),
    OUT(4),
    JIT(5),
    JIF(6),
    LT(7),
    EQ(8),
    BASE(9),
    HALT(99);

    companion object {
        private val lookup = values().associateBy { it.opcode }
        fun from(opcode: Int): Opcode? = lookup[opcode]
    }
}

enum class ParameterMode {
    POSITION,
    IMMEDIATE,
    RELATIVE;

    companion object {
        private val lookup = values().associateBy { it.ordinal }
        fun from(i: Int): ParameterMode? = lookup[i]
    }
}

class IntcodeVM(private val mem: ArrayList<Long>, private val input: () -> Long) {
    private var ip = 0
    private var base = 0

    var halted = false
        private set

    private fun set(n: Int, value: Long) {
        val index = getIndex(n)
        if (index < this.mem.size) {
            mem[index] = value
        } else {
            this.mem.ensureCapacity(index + 1)
            this.mem.addAll(List(index - this.mem.size) { 0L })
            this.mem.add(value)
        }
    }

    private fun get(n: Int): Long =
        mem.getOrElse(getIndex(n)) { 0L }

    private fun getIndex(n: Int): Int =
        when (getParameterMode(n)) {
            ParameterMode.POSITION -> mem[ip + n].toInt()
            ParameterMode.IMMEDIATE -> ip + n
            ParameterMode.RELATIVE -> base + mem[ip + n].toInt()
            else -> throw IllegalArgumentException("Invalid parameter mode.")
        }

    private fun getParameterMode(n: Int): ParameterMode? =
        ParameterMode.from((mem.getOrElse(ip) { 0L } / (10.0).pow(n + 1) % 10).toInt())

    fun nextOutput(): Long? {
        while (true) {
            when (Opcode.from(mem.getOrElse(ip) { 0L }.toInt() % 100)) {
                Opcode.ADD -> {
                    set(3, get(1) + get(2))
                    ip += 4
                }
                Opcode.MUL -> {
                    set(3, get(1) * get(2))
                    ip += 4
                }
                Opcode.SAV -> {
                    set(1, input())
                    ip += 2
                }
                Opcode.OUT -> {
                    val output = get(1)
                    ip += 2
                    return output
                }
                Opcode.JIT -> if (get(1) == 0L) ip += 3 else ip = get(2).toInt()
                Opcode.JIF -> if (get(1) != 0L) ip += 3 else ip = get(2).toInt()
                Opcode.LT -> {
                    set(3, if (get(1) < get(2)) 1L else 0L)
                    ip += 4
                }
                Opcode.EQ -> {
                    set(3, if (get(1) == get(2)) 1L else 0L)
                    ip += 4
                }
                Opcode.BASE -> {
                    base += get(1).toInt()
                    ip += 2
                }
                Opcode.HALT -> {
                    halted = true
                    return null
                }
                else -> throw IllegalArgumentException("Invalid opcode: ${mem.getOrElse(ip) { 0L }}.")
            }
        }
    }

    fun runUntilHalt(): Sequence<Long> = sequence {
        while (true) {
            yield(nextOutput() ?: break)
        }
    }
}
