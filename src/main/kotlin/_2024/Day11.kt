package com.bubber.aoc._2024

import com.bubber.aoc.Puzzle

class Day11(input: String) : Puzzle(input) {

    var cheat = mutableMapOf<Pair<Int,Long>, Long>()
    override fun solvePartOne(): Long {
        cheat = mutableMapOf()
        var sum = 0L
        for (stone in inputLines[0].split(" ").map(String::toLong)) {
            sum++
            sum+= blink(stone, 0, 25)}
        return sum
    }

    fun blink(stone: Long, count: Int, max: Int): Long {
        var sum = 0L
        if (count==max) return sum
        if(cheat.containsKey(count to stone)){
            return cheat[count to stone]!!
        }
        when {
            stone == 0L -> sum += blink(1,count + 1, max)
            stone.toString().length and 1 == 0 -> {
                val string = stone.toString()
                sum++
                sum += blink(string.subSequence(0,string.length/2).toString().toLong(), count + 1, max)
                sum += blink(string.subSequence(string.length/2, string.length).toString().toLong(), count + 1, max)
            }
            else -> {sum += blink(stone*2024, count + 1, max)}

        }
            cheat[count to stone] = sum

        return sum
    }

    override fun solvePartTwo(): Long {
        cheat = mutableMapOf()
        var sum = 0L
        for (stone in inputLines[0].split(" ").map(String::toLong)) {
                sum++
                sum+= blink(stone, 0, 75)}
        return sum
    }
}