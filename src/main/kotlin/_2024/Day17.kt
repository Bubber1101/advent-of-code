package com.bubber.aoc._2024

import com.bubber.aoc.Puzzle
import kotlin.math.pow

class Day17(input: String) : Puzzle(input) {


    class Computer(var A: Long, var B: Long, var C: Long, var program: MutableList<Int>) {
        private var pointer: Int = 0
        var output = mutableListOf<Int>()

        fun compute(): Boolean {
            if (pointer >= program.lastIndex) return false

            val operand = program[pointer + 1]
            when (program[pointer]) {
                0 -> {
                    A = (A / 2.0.pow(getComboOperand(operand).toDouble())).toLong()
                    pointer += 2
                }

                1 -> {
                    B = B xor operand.toLong()
                    pointer += 2
                }

                2 -> {
                    B = getComboOperand(operand) % 8
                    pointer += 2
                }

                3 -> {
                    if (A != 0L) {
                        pointer = operand
                    } else {
                        pointer += 2
                    }
                }

                4 -> {
                    B = B xor C
                    pointer += 2
                }

                5 -> {
                    output.add((getComboOperand(operand) % 8).toInt())
                    pointer += 2
                }

                6 -> {
                    B = (A / 2.0.pow(getComboOperand(operand).toDouble())).toLong()
                    pointer += 2
                }

                7 -> {
                    C = (A / 2.0.pow(getComboOperand(operand).toDouble())).toLong()
                    pointer += 2
                }

                else -> throw IllegalArgumentException("Invalid opcode")
            }
            return true
        }

        fun getComboOperand(i: Int): Long {
            when (i) {
                0, 1, 2, 3 -> return i.toLong()
                4 -> return A
                5 -> return B
                6 -> return C
                else -> throw IllegalArgumentException("Invalid operand")
            }
        }

        override fun toString(): String {
            return "Computer(A=$A, B=$B, C=$C, program=$program, pointer=$pointer, output=$output)"
        }
    }

    override fun solvePartOne(): Int {
        val computer = Computer(
            inputLines[0].split(":").last().trim().toLong(),
            inputLines[1].split(":").last().trim().toLong(),
            inputLines[2].split(":").last().trim().toLong(),
            inputLines[4].split(":").last().trim().split(",").map { it.toInt() }.toMutableList()
        )
        while (computer.compute()) {
//            println(computer)
        }
        println(computer)
        println("Output part 1: ${computer.output.joinToString(separator = ",")}")
        return 1
    }

    override fun solvePartTwo(): Number {
        var x = "111111111111111111111111111111111111111111111111"
        var bits = arrayOf("000", "001", "010", "011", "100", "101", "110", "111")
        var options = mutableListOf(x)
        var expected = inputLines[4].split(":").last().trim().split(",").map { it.toInt() }.toMutableList().reversed()
        expected.forEachIndexed { index, value ->
            var newOptions =mutableListOf<String>()
            options.forEach{option ->
                bits.map { option.replaceRange(index*3.. index*3+2, it) }.forEach{ bit->
                    val c = Computer(bit.toLong(2), 0 ,0, expected.reversed().toMutableList())
                    while (c.compute()) {}
                    if(c.output.reversed()[index] == value){
                        newOptions.add(bit)
                    }
                }
            }
            options = newOptions
        }
        return options.map { it.toLong(2) }.min()
    }


}