package com.bubber.aoc._2024

import com.bubber.aoc.Puzzle

class Day10(input: String) : Puzzle(input) {
    private fun trail(start: Pair<Int, Int>, level: Int): List<Pair<Int, Int>> {
        val list: MutableList<Pair<Int, Int>> = mutableListOf()
        if (level == 9) {
            return mutableListOf(start)
        }
        getPointsAround(start).filter(::isLegal).filter {
            inputLines[it.first][it.second] != '.'
        }.filter {
            inputLines[it.first][it.second].digitToInt() == level + 1
        }.forEach {
            list += trail(it, level + 1)
        }
        return list
    }



    override fun solvePartOne(): Int {
        var sum = 0
        inputLines.forEachIndexed { j, line ->
            line.forEachIndexed { i, it ->
                if (it == '0') {
                    sum += trail(j to i, 0).distinct().count()
                }
            }
        }
        return sum
    }

    override fun solvePartTwo(): Int {
        var sum = 0
        inputLines.forEachIndexed { j, line ->
            line.forEachIndexed { i, it ->
                if (it == '0') {
                    sum += trail(j to i, 0).count()
                }
            }
        }
        return sum
    }

}