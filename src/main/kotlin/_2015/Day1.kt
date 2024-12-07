package com.bubber.aoc._2015

import com.bubber.aoc.Puzzle

class Day1(input: String) : Puzzle(input) {
    override fun solvePartOne(): Number {
        var up = 0
        for (instruction in inputLines[0]) {
            if (instruction == '(') up++
            else up--
        }
        return up
    }

    override fun solvePartTwo(): Number {
        var up = 0
        inputLines[0].forEachIndexed { index, char ->
            if (char == '(') up++
            else up--
            if (up == -1) {
                return index + 1
            }
        }
        return -1
    }
}