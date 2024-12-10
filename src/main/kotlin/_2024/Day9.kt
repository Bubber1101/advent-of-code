package com.bubber.aoc._2024

import com.bubber.aoc.Puzzle

class Day9(input: String) : Puzzle(input) {

    //249258318 too low
    //496358138 too low

    override fun solvePartOne(): Long {
        val input = inputLines[0].map { it.toString().toInt() }
        var count = 0
        var sum = 0L
        val list = mutableListOf<Int?>()
        for (i in input.indices) {
            if (i % 2 == 0) {
                for(size in 1..input[i]){
                    list.addLast(count)
                }
                count++
            } else {
                for(size in 1..input[i]){
                    list.addLast(null)
                }
            }
        }

        var a = 0
        var z = list.lastIndex

        while(a<z) {
            val num = list[a]
            if (num == null) {
                var last: Int? = null
                while (last == null && a < z) {
                    last = list.removeLast()
                    z--
                }
                list[a] = last
            }
            sum += list[a]?.times(a)?: 0
            a++
        }


        return sum
    }

    override fun solvePartTwo(): Long {
        val input = inputLines[0].map { it.toString().toInt() }
        var count = 0
        var sum = 0L
        val list = mutableListOf<Int?>()
        for (i in input.indices) {
            if (i % 2 == 0) {
                for(size in 1..input[i]){
                    list.addLast(count)
                }
                count++
            } else {
                for(size in 1..input[i]){
                    list.addLast(null)
                }
            }
        }

        return sum

    }

}