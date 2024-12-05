package com.bubber.aoc._2024

import com.bubber.aoc.Puzzle

class Day5(input: String) : Puzzle(input) {
    private val orderMap = mutableMapOf<Int, Set<Int>>() //Map of number to set of numbers that need to be after it.

    private val rules = inputLines.subList(0, inputLines.indexOf("")).map {
        val rule = it.split("|")
        return@map rule[0].toInt() to rule[1].toInt()
    }
    private val updates = inputLines.subList(inputLines.indexOf("") + 1, inputLines.lastIndex + 1).map {
        it.split(",").map { it.toInt() }
    }

    init {
        for (rule in rules) {
            orderMap.merge(rule.first, hashSetOf(rule.second), { a, b -> a + b })
        }
    }

    override fun solvePartOne(): Int {
        var sum = 0
        for (update in updates) {
            if (isCorrect(update)) {
                sum += update.elementAt(update.size / 2)
            }
        }
        return sum
    }


    fun isCorrect(update: List<Int>): Boolean {
        update.forEachIndexed { i, page ->
            val numbersAfterThis = orderMap[page]?.map { update.indexOf(it) }?.filter { it > -1 }
            if (numbersAfterThis?.any { it < i } == true) {
                return false
            }
        }
        return true
    }


    override fun solvePartTwo(): Int {
        val incorrectUpdates = updates.filter { !isCorrect(it) }.map { it.toMutableList() }
        for (update in incorrectUpdates) {
            while (!isCorrect(update)) {
                for ((i, page) in update.withIndex()) {
                    val numbersAfterThis = orderMap[page]?.map { update.indexOf(it) }?.filter { it > -1 }
                    if (numbersAfterThis?.any { it < i } == true) {
                        var index = numbersAfterThis.sorted().first() - 1
                        if (index < 0) index = 0
                        update.remove(page)
                        update.add(index, page)
                        break
                    }
                }
            }
        }
        return incorrectUpdates.map { it.elementAt(it.size / 2) }.sum()
    }
}
