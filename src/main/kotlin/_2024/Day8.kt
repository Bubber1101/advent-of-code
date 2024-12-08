package com.bubber.aoc._2024

import com.bubber.aoc.Puzzle
import kotlin.math.abs
import kotlin.math.pow

class Day8(input: String) : Puzzle(input) {
    private val maxRowIndex = inputLines.size
    private val maxColumnIndex = inputLines[0].length

    val antennas = mutableMapOf<Char, List<Pair<Int, Int>>>()

    init {
        inputLines.forEachIndexed { j, line ->
            line.forEachIndexed { i, char ->
                if (char != '.') {
                    antennas.merge(char, mutableListOf(i to j), { a, b -> a + b })
                }
            }
        }
    }

    override fun solvePartOne(): Int {
        val antiNodes = mutableSetOf<Pair<Int, Int>>()
        antennas.keys.forEach {
            val a = antennas[it]!!
            for (antenna in a) {
                for (other in a) {
                    val distance = antenna.first - other.first to antenna.second - other.second
                    arrayOf(
                        antenna, other
                    ).map {
                        mutableListOf(
                            it.first + distance.first to it.second + distance.second,
                            it.first - distance.first to it.second - distance.second
                        )
                    }.reduce { a, b -> (a + b).toMutableList() }
                        .filterNot { it == antenna || it == other }
                        .filter { pair ->
                            pair.first > -1 && pair.second > -1 && pair.first < maxRowIndex && pair.second < maxColumnIndex
                        }.forEach {
                            antiNodes.add(it)
                        }
                }
            }
        }

        return antiNodes.size
    }

    override fun solvePartTwo(): Int {
        val antiNodes = mutableSetOf<Pair<Int, Int>>()

        antennas.keys.forEach { symbol ->
            val a = antennas[symbol]!!
            for (antenna in a) {
                for (other in a) {
                    val distance = antenna.first - other.first to antenna.second - other.second
                    arrayOf(
                        antenna, other
                    ).map {
                        var list = mutableListOf<Pair<Int, Int>>()
                        for (i in 1..maxRowIndex) {
                            list.add(it.first + distance.first * i to it.second + distance.second * i)
                            list.add(
                                it.first - distance.first * i to it.second - distance.second * i
                            )
                        }
                        return@map list
                    }.reduce { a, b -> (a + b).toMutableList() }
                        .filter { pair ->
                            pair.first > -1 && pair.second > -1 && pair.first < maxRowIndex && pair.second < maxColumnIndex
                        }.forEach {
                            antiNodes.add(it)
                        }
                }
            }
        }

        return antiNodes.size
    }
}
