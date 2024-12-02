package com.bubber.aoc._2024

import com.bubber.aoc.Puzzle

class Day1(input: String) : Puzzle(input) {
    override fun solvePartOne(): Int {
        val left = inputLines.map { line -> line.split("   ")[0].toInt() }.sorted().toList()
        val right = inputLines.map { line -> line.split("   ")[1].toInt() }.sorted().toList()

        var sum = 0
        left.indices.forEach { i ->
            var difference = left[i] - right[i]
            if (difference < 0){
                difference *= -1
            }
            sum += difference
        }
        return sum
    }

    override fun solvePartTwo(): Int {
        val left = inputLines.map { line -> line.split("   ")[0].toInt() }.toList()
        val right = inputLines.map { line -> line.split("   ")[1].toInt() }.toList()
        var score = 0
        for (num in left) {
            score += num * right.count{it == num}
        }
        return score
    }

}