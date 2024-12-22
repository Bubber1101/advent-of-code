package com.bubber.aoc._2024

import com.bubber.aoc.Puzzle
import com.bubber.aoc.utils.AStar

class Day21(input: String) : Puzzle(input) {
    /*
    7,8,9
    4,5,6
    1,2,3
    #,0,A
     */

    var numberCoords = mapOf(
        "7" to (0 to 0),
        "8" to (1 to 0),
        "9" to (2 to 0),

        "4" to (0 to 1),
        "5" to (1 to 1),
        "6" to (2 to 1),

        "1" to (0 to 2),
        "2" to (1 to 2),
        "3" to (2 to 2),

        "0" to (1 to 3),
        "A" to (2 to 3),
    )

    /*
    #,^,A
    <.v,>
     */

    var arrowCoords = mapOf(
        "^" to (1 to 0),
        "A" to (2 to 0),

        "<" to (0 to 1),
        "v" to (1 to 1),
        ">" to (2 to 1),
    )

    var direction = mapOf(
        (0 to -1) to "^",
        (1 to 0) to ">",
        (0 to 1) to "v",
        (-1 to 0) to "<",
    )

    override fun solvePartOne(): Number {
        var list: MutableList<String> = mutableListOf()
        var position = numberCoords.get("A")!!
        var last = ' '
        var path = ""
        "029A".forEach {
            last = it
            path += findNumberPath(position, it.toString())
            position = numberCoords.get(it.toString())!!
        }
        println(path)
        position = arrowCoords.get("A")!!
        var path1 = ""
        path.forEach {
            path1 += findArrowPath(position, it.toString())
            position = arrowCoords.get(it.toString())!!
        }
        println(path1)
        position = arrowCoords.get("A")!!
        var path2 = ""
        path1.forEach {
            path2 += findArrowPath(position, it.toString())
            position = arrowCoords.get(it.toString())!!
        }
        println(path2)
        return path2.length
    }

    fun findNumberPath(position: Pair<Int, Int>, number: String): String {
        var string = ""
        val (path, _) = AStar().search(position, numberCoords.get(number)!!, AStar.Map(3, 4, listOf(setOf(0 to 3))))
        for (i in 0..path.lastIndex - 1) {
            string += direction[path[i + 1].first - path[i].first to path[i + 1].second - path[i].second]!!
        }
        return string + "A"
    }

    fun findArrowPath(position: Pair<Int, Int>, arrow: String): String {
        var string = ""
        val (path, _) = AStar().search(position, arrowCoords.get(arrow)!!, AStar.Map(3, 2, listOf(setOf(0 to 0))))
        for (i in 0..path.lastIndex - 1) {
            string += direction[path[i + 1].first - path[i].first to path[i + 1].second - path[i].second]!!
        }
        return string + "A"
    }

    override fun solvePartTwo(): Number {
        TODO("Not yet implemented")
    }
    /*
    <v<A>A<A>>^AvAA<^A>A<v<A>>^AvA^A<v<A>>^AvA<A>^A<v<A>^A>AvA^A<v<A>A>^AAAvA<^A>A

    <vA<AA>>^AvAA<^A>A<v<A>>^AvA^A<vA>^A<v<A>^A>AAvA^A<v<A>A>^AAAvA<^A>A
    v<<A>>^A<A>AvA<^AA>A<vAAA>^A
    <v<A>>^A<A>A<A>vA<^A>A<vAAA>^A
        <A^A>^^AvvvA
        <A^A^>^AvvvA

     */
}
