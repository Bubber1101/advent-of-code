package com.bubber.aoc._2023

import com.bubber.aoc.Puzzle

class Day1(input: String) : Puzzle(input) {
    override fun solvePartOne(): Int {
        var sum = 0
        for (line in inputLines) {
            val characters = line.toCharArray()
            var numberString = ""
            for (char in characters) {
                if (char.isDigit()) {
                    numberString += char
                    break
                }
            }

            for (i in characters.size - 1 downTo 0) {
                if (characters[i].isDigit()) {
                    numberString += characters[i]
                    break
                }
            }
            sum += numberString.toInt()
        }
        return sum
    }

    override fun solvePartTwo(): Int {
        val mapDigitToWord = mapOf(
            "one" to "1",
            "two" to "2",
            "three" to "3",
            "four" to "4",
            "five" to "5",
            "six" to "6",
            "seven" to "7",
            "eight" to "8",
            "nine" to "9",
        )
        val digitList = listOf("one", "1", "two", "2", "three", "3", "four", "4", "five", "5", "six", "6", "seven", "7", "eight", "8", "nine", "9",)
        val wordList = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
        var sum = 0
        for (line in inputLines) {
            var numberString: String
            var first = line.findAnyOf(digitList)!!.second
            var last = line.findLastAnyOf(digitList)!!.second
            if (wordList.contains(first)) {
                first = mapDigitToWord.getValue(first)
            }
            if (wordList.contains(last)) {
                last = mapDigitToWord.getValue(last)
            }
            numberString = first + last
            sum += numberString.toInt()
        }
        return sum
    }
}