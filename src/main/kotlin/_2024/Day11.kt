package com.bubber.aoc._2024

import com.bubber.aoc.Puzzle

class Day11(input: String) : Puzzle(input) {

    override fun solvePartOne(): Int {
        var sum = 0
        var stones = inputLines[0].split(" ").map(String::toLong)
        val temp = mutableListOf<Int>()

        for(i in 1..25){
            stones = blink(stones)
            println(":" + i + " " + stones.size)
        }


        return stones.size
    }

    fun blink(stones: List<Long>): List<Long> {
        var temp = mutableListOf<Long>()
        for (stone in stones) {
            when {
                stone == 0L -> temp.add(1)
                stone.toString().length and 1 == 0 -> {
                    val string = stone.toString()
                    temp.add(string.subSequence(0,string.length/2).toString().toLong())
                    temp.add(string.subSequence(string.length/2, string.length).toString().toLong())
                }
                else -> {temp.add(stone*2024)}
            }
        }
        return temp

    }

    override fun solvePartTwo(): Int {
        var sum = 0
        return sum
    }

}