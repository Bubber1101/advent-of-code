package com.bubber.aoc._2024

import com.bubber.aoc.Puzzle
import kotlin.math.pow

class Day7(input: String) : Puzzle(input) {


    override fun solvePartOne(): Long {
        var sum = 0L
        inputLines.forEach { line ->
            val x = line.split(": ")[0].toLong()
            val numbers = line.split(": ")[1].split(" ").map(String::toLong)
            if (find(numbers.toList(), x, 0, 2.toDouble().pow(numbers.size.toDouble()).toInt() - 1) != -1) {
                sum += x
            }
        }
        return sum
    }

    fun find(numbers: List<Long>, x: Long, lb: Int, ub: Int): Int {
        for (i in lb..ub) {
            var result = getFromMultiplyIndices(numbers, i)
            if (result == x) {
                return i
            }
        }
        return -1
    }

    private fun getFromMultiplyIndices(numbers: List<Long>, mid: Int): Long {
        val nums = numbers.toMutableList()
        var sum = nums.removeFirst()
        var idxs = mid.toString(2).padStart(nums.size, '0').let { it.substring(it.length - nums.size) }
        nums.forEachIndexed { index, i ->
            if (idxs[index] == '1') {
                sum *= i
            } else {
                sum += i
            }
        }
        return sum
    }

    private fun getFromThreeIndices(numbers: List<Long>, idxs: String): Long {
        val nums = numbers.toMutableList()
        var sum = nums.removeFirst()
        nums.forEachIndexed { index, i ->
            if (idxs[index] == '*') {
                sum *= i
            } else if (idxs[index] == '+') {
                sum += i
            } else {
                sum = (sum.toString() + i.toString()).toLong()
            }
        }
        return sum
    }

    override fun solvePartTwo(): Long {
        var sum = 0L
        inputLines.forEach { line ->
            val x = line.split(": ")[0].toLong()
            val numbers = line.split(": ")[1].split(" ").map(String::toLong)
            var set = listOf("+", "*", "X")
            var strings = mutableListOf("")
            for (i in 2..numbers.size) {
                val temp = strings.toMutableList()
                for (s in set) {
                    for (t in temp) {
                        strings.add(t + s)
                    }
                }
                strings = strings.filter { it.length > i - 2 }.toMutableList()
            }

            if (findWithConcat(strings, numbers, x) != -1L) {
                sum += x
            }

        }
        return sum
    }

    private fun findWithConcat(
        strings: MutableList<String>,
        numbers: List<Long>,
        x: Long,
    ): Long {
        for (comb in strings) {
            var result = getFromThreeIndices(numbers, comb)
            if (result == x) {
                return result
            }
        }
        return -1
    }
}
