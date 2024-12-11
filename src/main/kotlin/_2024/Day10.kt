package com.bubber.aoc._2024

import com.bubber.aoc.Puzzle

class Day10(input: String) : Puzzle(input) {
    private val maxRowIndex = inputLines.size
    private val maxColumnIndex = inputLines[0].length
    private val around = arrayOf(1 to 0, 0 to 1, 0 to -1, -1 to 0)

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

    private fun isLegal(point: Pair<Int, Int>): Boolean {
        return point.first > -1 && point.second > -1 && point.first < maxRowIndex && point.second < maxColumnIndex
    }

    private fun getPointsAround(point: Pair<Int, Int>): List<Pair<Int, Int>> {
        return around.toMutableList().map { it.first + point.first to it.second + point.second }
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