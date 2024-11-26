package com.bubber.aoc

import java.io.File

open class Puzzle(filepath: String) {
    val inputLines = File(filepath).readLines()
}