package com.bubber.aoc._2024

import com.bubber.aoc.Puzzle

class Day14(input: String) : Puzzle(input) {
    companion object {
        val maxX = 101
        val maxY = 103

    }

    val nums = Regex("-?\\d{1,3}")


    data class Robot(var coords: Pair<Int, Int>, var velocity: Pair<Int, Int>) {

        fun getNextCoords(): Pair<Int, Int> {
            return getCoordsIn(1)
        }

        fun getCoordsIn(seconds: Int): Pair<Int, Int> {
            var nextX = (coords.first + velocity.first * seconds).let {
                val module = it % maxX
                if (module < 0) {
                    return@let maxX + module
                } else {
                    return@let module
                }
            }
            var nextY = (coords.second + velocity.second * seconds).let {
                val module = it % maxY
                if (module < 0) {
                    return@let maxY + module
                } else {
                    return@let module
                }
            }
            return nextX to nextY
        }

        fun move() {
            this.coords = getNextCoords()
        }
    }

    var robots = mutableListOf<Robot>()

    init {
        for (line in inputLines) {
            var params = nums.findAll(line).map { it.value.toInt() }.toList()
            robots.add(Robot(params[0] to params[1], params[2] to params[3]))
        }
    }

    //74797110 too low
    //225552000
    override fun solvePartOne(): Int {
        var quadrants = arrayOf(0, 0, 0, 0)
        robots.map { it.getCoordsIn(100) }
            .map {
                if (it.first == maxX / 2 || it.second == maxY / 2) return@map null
                if (it.first < maxX / 2) {
                    if (it.second < maxY / 2) {
                        return@map 0
                    } else {
                        return@map 2
                    }
                } else {
                    if (it.second < maxY / 2) {
                        return@map 1
                    } else {
                        return@map 3
                    }
                }
            }.filterNotNull()
            .forEach { quadrants[it]++ }
        return quadrants.reduce(Int::times)
    }


    override fun solvePartTwo(): Int {
        var count = 5000
        var regexp = Regex("(□{3,}.){3,}")
        var lineSeconds = mutableListOf<Int>()
        while (count < 20000) {
            var moved = robots.map { it.getCoordsIn(count) }

                var visualise = mutableListOf<MutableList<String>>()
                for (i in 0..<maxY) {
                    visualise.add("■".repeat(maxX).split("").toMutableList())
                }
                moved.forEach {
                    visualise[it.second][it.first] = "□"
                }
//            println(count)
            var x = ""
            for (l in visualise){
                x += l.reduce { a, b -> a + b } + '\n'
            }
            if (regexp.find(x) != null){
                lineSeconds.add(count)
            }
            count++
        }
        println(lineSeconds.joinToString(" ")) // I just looked at the image in the debugger for the first one.
        return lineSeconds.min()
    }

}