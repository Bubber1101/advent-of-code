package com.bubber.aoc._2024

import com.bubber.aoc.Puzzle
import kotlin.math.abs

class Day2(input: String) : Puzzle(input) {
    override fun solvePartOne(): Int {
        var count = 0
        for(line in inputLines) {
            if (isSafe(line.split(" ").map(String::toInt))) {
                count++
            }
        }
        return count
    }

    override fun solvePartTwo(): Int {
        var count = 0
        for(line in inputLines) {
            val levels = line.split(" ").map(String::toInt)
            if (isSafe(levels)) {
                count++
            } else {
                if(levels.mapIndexed{i, _ ->
                    var temp = levels.toMutableList()
                    temp.removeAt(i)
                    temp
                }.any{isSafe(it)}){
                    count++
                }
            }
        }
        return count
    }

        private fun isSafe(levels: List<Int>): Boolean {
        val order  = levels[0] < levels[1]
        levels.windowed(2).forEach {
            if (it[0] < it[1] != order || abs(it[0] - it[1]) !in (1..3)) {
                return false
            }
        }
        return true
    }
}