package com.bubber.aoc

fun main() {
    runAll()
}

fun run(puzzle: Puzzle) {
    println(puzzle.javaClass.simpleName)
    println(" Part One: " + puzzle.solvePartOne())
    println(" Part Two: " + puzzle.solvePartTwo())
}

fun runAll(){
    arrayOf(
        Day1("2023/src/main/resources/2023/01/input.txt"),
        Day2("2023/src/main/resources/2023/02/input.txt"),
        Day3("2023/src/main/resources/2023/03/input.txt")
    ).forEach {
        run(it)
    }
}