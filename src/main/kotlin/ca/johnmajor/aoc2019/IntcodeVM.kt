package ca.johnmajor.aoc2019

import kotlin.math.pow

class IntcodeVM(private val mem: ArrayList<Long>) {

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

    private fun set(ip: Int, rb: Int, arg: Int, value: Long) {
        val index = getIndex(ip, rb, arg)
        if (index < this.mem.size) {
            mem[index] = value
        } else {
            this.mem.ensureCapacity(index + 1)
            this.mem.addAll(List(index - this.mem.size) { 0L })
            this.mem.add(value)
        }
    }

    private fun get(ip: Int, rb: Int, arg: Int): Long =
        mem.getOrElse(getIndex(ip, rb, arg)) { 0L }

    private fun getIndex(ip: Int, rb: Int, arg: Int): Int =
        when (getParameterMode(ip, arg)) {
            ParameterMode.POSITION -> mem[ip + arg].toInt()
            ParameterMode.IMMEDIATE -> ip + arg
            ParameterMode.RELATIVE -> rb + mem[ip + arg].toInt()
            else -> throw IllegalArgumentException("Invalid parameter mode.")
        }

    private fun getParameterMode(ip: Int, arg: Int): ParameterMode? =
        ParameterMode.from((mem.getOrElse(ip) { 0L } / (10.0).pow(arg + 1) % 10).toInt())

    fun run(input: () -> Long, output: (Long) -> Unit) {
        var ip: Int? = 0
        var base = 0

        while (ip != null) {
            ip = when (Opcode.from(mem.getOrElse(ip) { 0L }.toInt() % 100)) {
                Opcode.ADD -> {
                    set(ip, base, 3, get(ip, base, 1) + get(ip, base, 2))
                    ip + 4
                }
                Opcode.MUL -> {
                    set(ip, base, 3, get(ip, base, 1) * get(ip, base, 2))
                    ip + 4
                }
                Opcode.SAV -> {
                    set(ip, base, 1, input())
                    ip + 2
                }
                Opcode.OUT -> {
                    output(get(ip, base, 1))
                    ip + 2
                }
                Opcode.JIT -> if (get(ip, base, 1) == 0L) ip + 3 else get(ip, base, 2).toInt()
                Opcode.JIF -> if (get(ip, base, 1) != 0L) ip + 3 else get(ip, base, 2).toInt()
                Opcode.LT -> {
                    set(ip, base, 3, if (get(ip, base, 1) < get(ip, base, 2)) 1L else 0L)
                    ip + 4
                }
                Opcode.EQ -> {
                    set(ip, base, 3, if (get(ip, base, 1) == get(ip, base, 2)) 1L else 0L)
                    ip + 4
                }
                Opcode.BASE -> {
                    base += get(ip, base, 1).toInt()
                    ip + 2
                }
                Opcode.HALT -> null
                else -> throw IllegalArgumentException("Invalid opcode: ${mem.getOrElse(ip) { 0L }}.")
            }
        }
    }
}
