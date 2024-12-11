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
                for (size in 1..input[i]) {
                    list.addLast(count)
                }
                count++
            } else {
                for (size in 1..input[i]) {
                    list.addLast(null)
                }
            }
        }

        var a = 0
        var z = list.lastIndex

        while (a < z) {
            val num = list[a]
            if (num == null) {
                var last: Int? = null
                while (last == null && a < z) {
                    last = list.removeLast()
                    z--
                }
                list[a] = last
            }
            sum += list[a]?.times(a) ?: 0
            a++
        }


        return sum
    }

    data class File(val index: Int, var size: Int)


    //12438825691928 too high
    //8315915781040 too high
    //3484 too low
    override fun solvePartTwo(): Long {
        val input = inputLines[0].map { it.toString().toInt() }
        var count = 0
        var sum = 0L
        val list = mutableListOf<Int?>()
        val pointers = mutableListOf<File>()
        for (i in input.indices) {
            if (i % 2 == 0) {
                pointers.add(File(list.lastIndex+1, input[i]))
                for (size in 1..input[i]) {
                    list.addLast(count)
                }
                count++
            } else {
                for (size in 1..input[i]) {
                    list.addLast(null)
                }
            }
        }

        println(list)

        var tempA = 0
        var tempB = -1
        for (i in list.indices) {

            if(list[i] == null){
                var size = 1
                while (i+size < list.size && list[i+size] == null) {
                    size++
                }
                var fileIndex = getFile(pointers, i, size)
                if (fileIndex > -1) {
                    var file = pointers[fileIndex]
                    for(a in 0..file.size-1){
                        list[i+a] = list[file.index]
                    }
                    for(a in 0..file.size-1){
                        list[file.index+a] = null
                    }
                    pointers.sortBy { file.index }
                }
            }
            sum += list[i]?.times(i) ?: 0

        }

        println(list)
        return sum

    }

    fun getFile(list: List<File>, range: Int, size: Int): Int {
        for (f in list.lastIndex downTo range) {
            var file = list[f]
            if (file.index > range && file.size <= size) {
                return f
            }
        }
        return -1
    }

}