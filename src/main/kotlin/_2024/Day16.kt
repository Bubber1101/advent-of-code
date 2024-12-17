package com.bubber.aoc._2024

import com.bubber.aoc.Puzzle

class Day16(input: String) : Puzzle(input) {
    data class Maze(val start: Point, val end: Point, val points: MutableSet<Point>)
    data class Point(val coords: Pair<Int, Int>, val neighbors: MutableList<Point>) {}
    override fun solvePartOne(): Number {
        var score = 0
        return score
    }

    override fun solvePartTwo(): Number {
       var score = 0
        return score
    }

}