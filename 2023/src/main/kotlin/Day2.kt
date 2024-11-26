package com.bubber.aoc

class Day2(input: String) : Puzzle(input), Solvable {
    override fun solvePartOne(): Int {
        val colorMaxMap = mapOf(
            "green" to 13,
            "blue" to 14,
            "red" to 12,
        )

        var sum = 0
        for (game in inputLines) {
            val inputParts = game.split(":")
            val id = inputParts.first().split(" ").last().toInt()
            val sets = inputParts.last().split(";")
            var isPossible = true
            for (set in sets) {
                for (it in set.split(",")) {
                    val split = it.trim().split(" ")
                    val num = split.first().toInt()
                    val color = split.last()
                    isPossible = colorMaxMap.getValue(color) >= num
                    if (!isPossible) {
                        break
                    }
                }
                if (!isPossible) {
                    break
                }
            }
            if (isPossible) {
                sum += id
            }
        }
        return sum
    }

    override fun solvePartTwo(): Int {
        var sum = 0
        for (game in inputLines) {
            val colorMinMap = mutableMapOf(
                "green" to 0,
                "blue" to 0,
                "red" to 0,
            )

            val inputParts = game.split(":")
            val sets = inputParts.last().split(";")
            for (set in sets) {
                for (it in set.split(",")) {
                    val split = it.trim().split(" ")
                    val num = split.first().toInt()
                    val color = split.last()
                    if (colorMinMap.getValue(color) < num) {
                        colorMinMap.put(color, num)
                    }
                }
            }

            sum += colorMinMap.values.reduce { x, y -> x * y }
        }
        return sum
    }
}