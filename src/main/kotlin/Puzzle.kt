package com.bubber.aoc

import java.io.File

abstract class Puzzle(filepath: String) {
    val inputLines = File(filepath).readLines()
    private val maxRowIndex = inputLines.size
    private val maxColumnIndex = inputLines[0].length
    private val around = arrayOf(
        1 to 0,
        0 to 1,
        0 to -1,
        -1 to 0
    )

    //X TO Y
    enum class Direction(val coords: Pair<Int, Int>) {
        UP(0 to -1),
        RIGHT(1 to 0),
        DOWN(0 to 1),
        LEFT(-1 to 0)
    }

    abstract fun solvePartOne(): Number
    abstract fun solvePartTwo(): Number


    fun isLegal(point: Pair<Int, Int>): Boolean {
        return point.first > -1 && point.second > -1 && point.first < maxRowIndex && point.second < maxColumnIndex
    }

    fun getPointsAround(point: Pair<Int, Int>): List<Pair<Int, Int>> {
        return around.toMutableList().map { it.first + point.first to it.second + point.second }
    }
}