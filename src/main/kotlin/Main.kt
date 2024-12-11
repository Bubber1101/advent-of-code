package com.bubber.aoc

import com.bubber.aoc._2023.Day1
import com.bubber.aoc._2023.Day2
import com.bubber.aoc._2023.Day3
import kotlin.time.measureTime

fun main() {
    measureTime {
        run(com.bubber.aoc._2024.Day9("src/main/resources/2024/09.txt"))
    }.let { println("Time: $it") }
}

fun run(puzzle: Puzzle) {
    println(puzzle.javaClass.simpleName)
    println(" Part One: " + puzzle.solvePartOne())
    println(" Part Two: " + puzzle.solvePartTwo())
}

fun runAll(){
    arrayOf(
        Day1("src/main/resources/2023/01/input.txt"),
        Day2("src/main/resources/2023/02/input.txt"),
        Day3("src/main/resources/2023/03/input.txt")
    ).forEach {
        run(it)
    }
}