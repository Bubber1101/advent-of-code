package com.bubber.aoc

import com.bubber.aoc._2023.Day1
import com.bubber.aoc._2023.Day2
import com.bubber.aoc._2023.Day3

fun main() {
    run(com.bubber.aoc._2024.Day7("src/main/resources/2024/07.txt"))
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