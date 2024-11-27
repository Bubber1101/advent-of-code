package com.bubber.aoc

class Day3(input: String) : Puzzle(input) {

    data class PartNumber(val number: String, val start: Pair<Int, Int>, val end: Pair<Int, Int>)


    val maxRowIndex = inputLines.size
    val maxColumnIndex = inputLines[0].length
    //relative indexes
    val before = arrayOf(Pair(0, -1), Pair(-1, -1), Pair(1, -1))
    val after = arrayOf(Pair(0, 1), Pair(-1, 1), Pair(1, 1))
    val aboveAndBelow = arrayOf(Pair(1, 0), Pair(-1, 0))

    //Too low: 526341
    //Too high: 529964
    //wrong: 527322
    //good: 528819
    override fun solvePartOne(): Int {
        var sum = 0
        var numbers : Array<PartNumber> = arrayOf()
        var tempNumber = ""

        inputLines.forEachIndexed { i, row ->
            row.forEachIndexed { j, element ->
                if (element.isDigit()) {
                    tempNumber += element
                } else {
                    if (tempNumber != "") {
                        numbers = numbers.plus(PartNumber(tempNumber, Pair(i, j-tempNumber.length), Pair(i,j-1)))
                    }
                    tempNumber = ""
                }

                if (j == maxColumnIndex-1){
                    if (tempNumber != "") {
                        numbers = numbers.plus(PartNumber(tempNumber, Pair(i, j-tempNumber.length+1), Pair(i,j)))
                    }
                    tempNumber = ""
                }
            }
        }
        return numbers.filter { verifyNumber(it.number, it.start.first, it.start.second) }.map { it.number.toInt() }.sum()

//        return sum
    }

    fun verifyNumber(num: String, i: Int, j: Int) : Boolean {
        var indexes: Array<Pair<Int, Int>> = arrayOf()


        before.forEach { indexes = indexes.plus(Pair(it.first + i, it.second + j)) }
        after.forEach { indexes = indexes.plus(Pair(it.first + i, it.second + j + num.length - 1)) }
        for (x in 0..num.length - 1) {
            aboveAndBelow.forEach {
                indexes = indexes.plus(Pair(it.first + i, it.second + j + x))
            }
        }
        var distinct = indexes.distinct()
            .filter { pair -> pair.first > -1 && pair.second > -1 && pair.first < maxRowIndex && pair.second < maxColumnIndex }

        print("Element at [$i][$j] is $num [")
//        distinct.forEach {
//            print("[${it.first},${it.second} = ${inputLines[it.first][it.second]} ]")
//        }
//        println("]")
        println(distinct.map { inputLines[it.first][it.second] }.filter { it != '.' }.filter{ !it.isDigit() })
        return distinct.map { inputLines[it.first][it.second] }.filter { it != '.' }.filter{ !it.isDigit() }.isNotEmpty()
    }

    override fun solvePartTwo(): Int {
        var sum = 0
        return sum
    }
}