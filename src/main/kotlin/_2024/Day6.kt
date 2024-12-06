package com.bubber.aoc._2024

import com.bubber.aoc.Puzzle

class Day6(input: String) : Puzzle(input) {
    private val maxRowIndex = inputLines.size
    private val maxColumnIndex = inputLines[0].length
    var down = 1 to 0
    var right = 0 to 1
    var up = -1 to 0
    var left = 0 to -1
    val movements = listOf(up, right, down, left)

    val startingPosition: Pair<Int, Int> = run {
        val y = inputLines.indexOfFirst { it.contains("^") }
        y to inputLines[y].indexOf("^")
    }

    val obstacles: List<Pair<Int, Int>> = run {
        val obstacles: MutableList<Pair<Int, Int>> = mutableListOf()
        inputLines.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                if (char == '#') {
                    obstacles.add(y to x)
                }
            }
        }
        obstacles
    }

    override fun solvePartOne(): Int {
        var lines = inputLines.toMutableList()
        var sum = 0
        var position = startingPosition
        var turnCount = 0
        var direction = movements[0]
        var nextPosition = position

        while (true) {
            position = nextPosition
            lines[position.first] = lines[position.first].replaceRange(position.second, position.second+1, "O")
            nextPosition = position.let { it.first + direction.first to it.second + direction.second }
            if (!(nextPosition.first > -1 && nextPosition.second > -1 && nextPosition.first < maxColumnIndex && nextPosition.second < maxRowIndex)){
                break
            }
            if (lines[nextPosition.first][nextPosition.second] == '#') {
                direction = movements[++turnCount % movements.size]
                nextPosition = position
            }

        }

        for (line in lines) {
            sum += line.count { it == 'O' }
        }

        return sum
    }

    fun isLoopCausing(newObstacle : Pair<Int,Int>) : Boolean {
        if (startingPosition == newObstacle || obstacles.contains(newObstacle)) {
            return false
        }

        val lines = inputLines.toMutableList()
        lines[newObstacle.first] = lines[newObstacle.first].replaceRange(newObstacle.second, newObstacle.second+1, "#")
        var position = startingPosition
        var turnCount = 0
        var direction = movements[0]
        var nextPosition = position

     val obstacleList = mutableMapOf<Pair<Int,Int>, Int>()
     obstacleList.put(newObstacle, 0)
     for (obstacle in obstacles) {
         obstacleList.put(obstacle, 0)
     }

        while (true) {
            position = nextPosition
            nextPosition = position.let { it.first + direction.first to it.second + direction.second }
            if (!(nextPosition.first > -1 && nextPosition.second > -1 && nextPosition.first < maxColumnIndex && nextPosition.second < maxRowIndex)){
                return false
            }
            if (lines[nextPosition.first][nextPosition.second] == '#') {
                obstacleList.merge(nextPosition.first to nextPosition.second, 1, Int::plus)
                if(obstacleList.values.any { it > 3 }){
                    return true
                }

                direction = movements[++turnCount % movements.size]
                nextPosition = position
            }
        }
    }

    override fun solvePartTwo(): Int {
        var sum = 0
        inputLines.forEachIndexed { y, line ->
            line.forEachIndexed { x, _ ->
                if (isLoopCausing(x to y)) {
                    sum++
                }
            }
        }
    return sum
    }
}
