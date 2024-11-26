package com.bubber.aoc

fun main() {
    arrayOf(
        Day1("2023/src/main/resources/2023/01/input.txt"),
        Day2("2023/src/main/resources/2023/02/input.txt")
    ).forEach {
        puzzle ->
        println(puzzle.javaClass.simpleName)
        println(" Part 1:" + puzzle.solvePartOne())
        println(" Part 2:" + puzzle.solvePartTwo())
    }
}