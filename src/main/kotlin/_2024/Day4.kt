package com.bubber.aoc._2024

import com.bubber.aoc.Puzzle

class Day4(input: String) : Puzzle(input) {
    private val maxRowIndex = inputLines.size
    private val maxColumnIndex = inputLines[0].length
    private val left = arrayOf(Pair(0, -1), Pair(0, -2), Pair(0, -3))
    private val right = arrayOf(Pair(0, 1), Pair(0, 2), Pair(0, 3))
    private val up = arrayOf(Pair(1, 0), Pair(2, 0), Pair(3, 0))
    private val down = arrayOf(Pair(-1, 0), Pair(-2, 0), Pair(-3, 0))
    private val diagDownRight = right.copyOf().mapIndexed { i, it -> Pair(down[i].first, it.second) }.toTypedArray()
    private val diagDownLeft = left.copyOf().mapIndexed { i, it -> Pair(down[i].first, it.second) }.toTypedArray()
    private val diagUpLeft = left.copyOf().mapIndexed { i, it -> Pair(up[i].first, it.second) }.toTypedArray()
    private val diagUpRight = right.copyOf().mapIndexed { i, it -> Pair(up[i].first, it.second) }.toTypedArray()


    override fun solvePartOne(): Int {
        var sum = 0
        inputLines.forEachIndexed { i, row ->
            row.forEachIndexed { j, element ->
                if (element == 'X') {
                    sum += count(i, j)

                }
            }
        }
        return sum
    }

    private fun count(y: Int, x: Int): Int {
        return listOf(
            left.toList(),
            right.toList(),
            up.toList(),
            down.toList(),
            diagDownRight.toList(),
            diagDownLeft.toList(),
            diagUpLeft.toList(),
            diagUpRight.toList()
        ).map {
            it.map { Pair(it.first + y, it.second + x) }
        }.filter {
            it.all { pair ->
                pair.first > -1 && pair.second > -1 && pair.first < maxRowIndex && pair.second < maxColumnIndex
            }
        }.map {
            it.map { inputLines[it.first][it.second].toString() }.joinToString(separator = "", prefix = "X")
        }.count { it == "XMAS" }


    }


    override fun solvePartTwo(): Int {
        var sum = 0
        inputLines.forEachIndexed { i, row ->
            row.forEachIndexed { j, element ->
                if (element == 'A') {
                    if (isX(i, j)) {
                        sum++
                    }

                }
            }
        }
        return sum
    }

    private fun isX(y: Int, x: Int): Boolean {
        if (x == 0 || x == maxColumnIndex - 1 || y == maxRowIndex - 1 || y == 0) {
            return false
        }

        return arrayOf(
            arrayOf(Pair(1, -1), Pair(-1, 1)),
            arrayOf(Pair(-1, -1), Pair(1, 1))
        ).map {
            it.map {
                Pair(
                    it.first + y,
                    it.second + x
                )
            }.map { inputLines[it.first][it.second].toString() }.sorted().joinToString(separator = "")
        }.all { it =="MS" }
    }
}