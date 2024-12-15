package com.bubber.aoc._2024

import com.bubber.aoc.Puzzle

class Day15(input: String) : Puzzle(input) {

    val instructionMap = mapOf(
        '^' to Direction.UP,
        '>' to Direction.RIGHT,
        '<' to Direction.LEFT,
        'v' to Direction.DOWN,
    )

    var warehouse = arrayOf<Array<Int>>()
    val instructions: String
    val mapDimensions: Pair<Int, Int>
    var robotPosition: Pair<Int, Int>

    init {
        val map = inputLines.subList(0, inputLines.indexOf(""))
        instructions = inputLines.subList(inputLines.indexOf("") + 1, inputLines.size).reduce(String::plus)
        mapDimensions = map[0].length to map.size
        warehouse = map.map {
            it.map {
                when (it) {
                    '.' -> 0
                    '@' -> 3
                    'O' -> 1
                    '#' -> 2
                    else -> -1
                }
            }.toTypedArray()
        }.toTypedArray()

        val initY = warehouse.indexOfFirst { it.contains(3) }
        val initX = warehouse[initY].indexOfFirst { it == 3 }
        robotPosition = initX to initY
    }

    fun move(position: Pair<Int, Int>, direction: Direction): Boolean {
        val it = warehouse[position.second][position.first]
        if (it == 2) {
            return false
        }
        val nextPosition = position.first + direction.coords.first to position.second + direction.coords.second
        if (warehouse[nextPosition.second][nextPosition.first] == 0 || move(nextPosition, direction)) {
            warehouse[nextPosition.second][nextPosition.first] = it
            warehouse[position.second][position.first] = 0
            return true
        }
        return false
    }

    override fun solvePartOne(): Number {
        var sum = 0L
        var direction: Direction
        for (instruction in instructions) {
            direction = instructionMap[instruction]!!
            if(move(robotPosition, direction)){
                robotPosition = robotPosition.first + direction.coords.first to robotPosition.second + direction.coords.second
            }
        }

        warehouse.forEachIndexed { y, line ->
            line.forEachIndexed { x, point ->
                if (point == 1){
                  sum += y * 100 + x
                }
            }
        }
        return sum
    }


    override fun solvePartTwo(): Int {
        return 0
    }

}