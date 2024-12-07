package com.bubber.aoc

import java.io.File

abstract class Puzzle(filepath: String) {
    val inputLines = File(filepath).readLines()
    abstract fun solvePartOne(): Number
    abstract fun solvePartTwo(): Number
}