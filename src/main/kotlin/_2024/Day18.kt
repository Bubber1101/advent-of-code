package com.bubber.aoc._2024

import com.bubber.aoc.Puzzle
import com.bubber.aoc.utils.AStar

class Day18(input: String) : Puzzle(input) {


    
    override fun solvePartOne(): Int {
        var bytes = inputLines.map { it.split(",") }.map { it[0].toInt() to it[1].toInt() }.subList(0,1024).toSet()
        val (path, cost) = AStar().search(0 to 0, 70 to 70, AStar.Map(71, 71, listOf(bytes)))
        println(path)
        return path.size - 1
    }

    override fun solvePartTwo(): Int {
        var bytes = inputLines.map { it.split(",") }.map { it[0].toInt() to it[1].toInt() }
        var subBytes = bytes.subList(0,1024).toMutableList()
        for (byteIndex in 1024 .. bytes.lastIndex) {
            var byte = bytes[byteIndex]
            println(byte)
            subBytes.add(byte)
            val (path, cost) = AStar().search(0 to 0, 70 to 70, AStar.Map(71,71, listOf(subBytes.toSet())))
        }
        return 1
    }

}