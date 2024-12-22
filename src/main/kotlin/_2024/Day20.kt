package com.bubber.aoc._2024

import com.bubber.aoc.Puzzle

class Day20(input: String) : Puzzle(input) {
    override fun solvePartOne(): Int {
        val pattern = inputLines[0]
            .split(",")
            .map { it.trim() }
            .sortedBy { it.length }
            .joinToString(prefix = "^(", separator = "|", postfix = ")+$")
        println(pattern)
        var regex = Regex(pattern)
        var score = 0
        for (i in 2..inputLines.lastIndex) {
            if (regex.findAll(inputLines[i]).map { it.value }.joinToString("").length == inputLines[i].length) {
                score++
            }
        }
        return score
    }

    override fun solvePartTwo(): Int {

        return 0
    }

}