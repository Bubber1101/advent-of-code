package com.bubber.aoc._2024

import com.bubber.aoc.Puzzle
import kotlin.math.abs

class Day3(input: String) : Puzzle(input) {
    override fun solvePartOne(): Int {
        val input = inputLines.reduce{ acc, s -> acc + s}
        val regex = Regex("mul\\(\\d{1,3},\\d{1,3}\\)")
        val nums = Regex("\\d{1,3}")
        var sum = 0
        regex.findAll(input).forEach {
            sum += nums.findAll(it.value).map { it.value.toInt() }.reduce(Int::times)
        }
        return sum
    }

    override fun solvePartTwo(): Int {
        val input = inputLines.reduce{ acc, s -> acc + s}
        val regex = Regex("mul\\(\\d{1,3},\\d{1,3}\\)|don't\\(\\)|do\\(\\)")
        val nums = Regex("\\d{1,3}")
        var sum = 0
        var flag = true
        val instructions = regex.findAll(input).map { it.value }
        instructions.forEach {
            if (it == "don't()"){
                flag = false
            } else if (it == "do()"){
                flag = true
            } else {
                if (flag) {
                    sum += nums.findAll(it).map { it.value.toInt() }.reduce(Int::times)
                }
            }
        }
        return sum
    }
}