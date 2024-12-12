package com.bubber.aoc._2024

import com.bubber.aoc.Puzzle

class Day9(input: String) : Puzzle(input) {

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

    data class File(var index: Int, var size: Int)

    override fun solvePartTwo(): Long {
        val input = inputLines[0].map { it.toString().toInt() }
        var count = 0
        var sum = 0L
        val list = mutableListOf<Int?>()
        var pointers = mutableListOf<File>()
        for (i in input.indices) {
            if (i % 2 == 0) {
                pointers.add(File(list.lastIndex + 1, input[i]))
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

        println(pointers)
        println(list)

        pointers.reversed().forEach { file ->
            for (i in list.indices) {
                if (i > file.index) break
                if (list[i] == null) {
                    var size = 1
                    while (i + size < list.size && list[i + size] == null) {
                        size++
                    }
                    if (size >= file.size) {
                        for (a in 0..<file.size) {
                            list[i + a] = list[file.index]
                        }
                        for (a in 0..<file.size) {
                            list[file.index + a] = null
                        }
                        break
                    }
                }
            }

        }
        for (a in list.indices) {
            sum += list[a]?.times(a) ?: 0
        }
        return sum

    }

    fun getFile(list: List<File>, range: Int, size: Int): Int {
//        println(list)
        for (f in list.lastIndex downTo 0) {
            var file = list[f]
//            println("$range : size: $size : file $file")
            if (file.index > range && file.size <= size) {
                return f
            }
        }
        return -1
    }

}