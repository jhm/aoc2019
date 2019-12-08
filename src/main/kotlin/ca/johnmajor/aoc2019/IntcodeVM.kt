package ca.johnmajor.aoc2019

import kotlin.math.pow

class IntcodeVM(private val mem: IntArray) {

    enum class Opcode(val opcode: Int) {
        ADD(1),
        MUL(2),
        SAV(3),
        OUT(4),
        JIT(5),
        JIF(6),
        LT(7),
        EQ(8),
        HALT(99);

        companion object {
            private val lookup = values().associateBy { it.opcode }
            fun from(opcode: Int): Opcode? = lookup[opcode]
        }
    }

    enum class ParameterMode {
        POSITION,
        IMMEDIATE;

        companion object {
            private val lookup = values().associateBy { it.ordinal }
            fun from(i: Int): ParameterMode? = lookup[i]
        }
    }

    private fun set(ip: Int, arg: Int, value: Int) {
        mem[getIndex(ip, arg)] = value
    }

    private fun get(ip: Int, arg: Int): Int =
        mem[getIndex(ip, arg)]

    private fun getIndex(ip: Int, arg: Int): Int =
        when (getParameterMode(ip, arg)) {
            ParameterMode.POSITION -> mem[ip + arg]
            ParameterMode.IMMEDIATE -> ip + arg
            else -> throw IllegalArgumentException("Invalid parameter mode.")
        }

    private fun getParameterMode(ip: Int, arg: Int): ParameterMode? =
        ParameterMode.from((mem[ip] / (10.0).pow(arg + 1) % 10).toInt())

    fun run(input: () -> Int, output: (Int) -> Unit) {
        var ip: Int? = 0

        while (ip != null) {
            ip = when (Opcode.from(mem[ip] % 100)) {
                Opcode.ADD -> {
                    set(ip, 3, get(ip, 1) + get(ip, 2))
                    ip + 4
                }
                Opcode.MUL -> {
                    set(ip, 3, get(ip, 1) * get(ip, 2))
                    ip + 4
                }
                Opcode.SAV -> {
                    set(ip, 1, input())
                    ip + 2
                }
                Opcode.OUT -> {
                    output(get(ip, 1))
                    ip + 2
                }
                Opcode.JIT -> if (get(ip, 1) == 0) ip + 3 else get(ip, 2)
                Opcode.JIF -> if (get(ip, 1) != 0) ip + 3 else get(ip, 2)
                Opcode.LT -> {
                    set(ip, 3, if (get(ip, 1) < get(ip, 2)) 1 else 0)
                    ip + 4
                }
                Opcode.EQ -> {
                    set(ip, 3, if (get(ip, 1) == get(ip, 2)) 1 else 0)
                    ip + 4
                }
                Opcode.HALT -> null
                else -> throw IllegalArgumentException("Invalid opcode.")
            }
        }
    }
}
