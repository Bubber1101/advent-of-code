package com.bubber.aoc

import java.util.logging.Logger


class Day3(input: String) : Puzzle(input) {
    val log = Logger.getLogger(this.javaClass.name)

    data class PartNumber(val number: String, val start: Pair<Int, Int>) {
        fun getPoints(): List<Pair<Int, Int>> {
            return number.indices.map { Pair(start.first, start.second + it) }
        }
    }

    private val maxRowIndex = inputLines.size
    private val maxColumnIndex = inputLines[0].length

    //relative indexes
    private val before = arrayOf(Pair(0, -1), Pair(-1, -1), Pair(1, -1))
    private val after = arrayOf(Pair(0, 1), Pair(-1, 1), Pair(1, 1))
    private val aboveAndBelow = arrayOf(Pair(1, 0), Pair(-1, 0))
    private var numbers: Array<PartNumber> = arrayOf()

    init {
        numbers = findNumbers()
    }

    private fun findNumbers(): Array<PartNumber> {
        var tempNumber = ""
        var numbers: Array<PartNumber> = arrayOf()
        inputLines.forEachIndexed { i, row ->
            row.forEachIndexed { j, element ->
                if (element.isDigit()) {
                    tempNumber += element
                } else {
                    if (tempNumber != "") {
                        numbers = numbers.plus(PartNumber(tempNumber, Pair(i, j - tempNumber.length)))
                    }
                    tempNumber = ""
                }

                if (j == maxColumnIndex - 1) {
                    if (tempNumber != "") {
                        numbers = numbers.plus(PartNumber(tempNumber, Pair(i, j - tempNumber.length + 1)))
                    }
                    tempNumber = ""
                }
            }
        }
        return numbers
    }

    override fun solvePartOne(): Int {
        return numbers.filter { verifyNumber(it.number, it.start.first, it.start.second) }.map { it.number.toInt() }
            .sum()
    }

    override fun solvePartTwo(): Int {
        var sum = 0

        inputLines.forEachIndexed { i, row ->
            row.forEachIndexed { j, element ->
                if (element == '*') {
                    log.fine("i= $i, j= $j, -> ${getNumbersAround(i, j)}")
                    sum += getNumbersAround(i, j).takeIf { it.size == 2 }?.map { it.number.toInt() }
                        ?.reduce { a, b -> a * b }
                        ?: 0
                }
            }
        }

        return sum
    }

    private fun getNumbersAround(i: Int, j: Int): List<PartNumber> {
        var indexes: Set<Pair<Int, Int>> = setOf()
        before.plus(after).plus(aboveAndBelow).forEach { indexes = indexes.plus(Pair(it.first + i, it.second + j)) }
        indexes =
            indexes.filter { pair -> pair.first > -1 && pair.second > -1 && pair.first < maxRowIndex && pair.second < maxColumnIndex }
                .toSet()
        return numbers.filter { it.getPoints().any { point -> indexes.contains(point) } }
    }

    private fun verifyNumber(num: String, i: Int, j: Int): Boolean {
        var indexes: Array<Pair<Int, Int>> = arrayOf()


        before.forEach { indexes = indexes.plus(Pair(it.first + i, it.second + j)) }
        after.forEach { indexes = indexes.plus(Pair(it.first + i, it.second + j + num.length - 1)) }
        for (x in 0..num.length - 1) {
            aboveAndBelow.forEach {
                indexes = indexes.plus(Pair(it.first + i, it.second + j + x))
            }
        }
        val distinct = indexes.distinct()
            .filter { pair -> pair.first > -1 && pair.second > -1 && pair.first < maxRowIndex && pair.second < maxColumnIndex }

        log.fine(
            "Element at [$i][$j] is $num [${
                distinct.map { inputLines[it.first][it.second] }.filter { it != '.' }.filter { !it.isDigit() }
            }]"
        )

        return distinct.map { inputLines[it.first][it.second] }.filter { it != '.' }.filter { !it.isDigit() }
            .isNotEmpty()
    }
}