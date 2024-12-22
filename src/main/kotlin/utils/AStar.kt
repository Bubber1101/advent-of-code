package com.bubber.aoc.utils

import kotlin.math.abs

/**
 * based on rosetta stone implementation example for kotlin
 */
class AStar {
    abstract class Grid(private val barriers: List<Set<Pair<Int, Int>>>) {

        open fun heuristicDistance(start: Pair<Int, Int>, finish: Pair<Int, Int>): Int {
            val dx = abs(start.first - finish.first)
            val dy = abs(start.second - finish.second)
            return (dx + dy) + (-2) * minOf(dx, dy)
        }

        fun inBarrier(position: Pair<Int, Int>) = barriers.any { it.contains(position) }

        abstract fun getNeighbours(position: Pair<Int, Int>): List<Pair<Int, Int>>

        open fun moveCost(from: Pair<Int, Int>, to: Pair<Int, Int>) = if (inBarrier(to)) 99999999 else 1
    }

    class Map(width: Int, height: Int, barriers: List<Set<Pair<Int, Int>>>) : Grid(barriers) {

        private val heightRange: IntRange = (0 until height)
        private val widthRange: IntRange = (0 until width)

        private val validMoves = listOf(Pair(1, 0), Pair(-1, 0), Pair(0, 1), Pair(0, -1))

        override fun getNeighbours(position: Pair<Int, Int>): List<Pair<Int, Int>> = validMoves
            .map { Pair<Int, Int>(position.first + it.first, position.second + it.second) }
            .filter { inGrid(it) }

        private fun inGrid(it: Pair<Int, Int>) = (it.first in widthRange) && (it.second in heightRange)
    }


    public fun search(start: Pair<Int, Int>, finish: Pair<Int, Int>, grid: Grid): Pair<List<Pair<Int, Int>>, Int> {

        fun generatePath(
            currentPos: Pair<Int, Int>,
            cameFrom: kotlin.collections.Map<Pair<Int, Int>, Pair<Int, Int>>
        ): List<Pair<Int, Int>> {
            val path = mutableListOf(currentPos)
            var current = currentPos
            while (cameFrom.containsKey(current)) {
                current = cameFrom.getValue(current)
                path.add(0, current)
            }
            return path.toList()
        }

        val openVertices = mutableSetOf(start)
        val closedVertices = mutableSetOf<Pair<Int, Int>>()
        val costFromStart = mutableMapOf(start to 0)
        val estimatedTotalCost = mutableMapOf(start to grid.heuristicDistance(start, finish))

        val cameFrom = mutableMapOf<Pair<Int, Int>, Pair<Int, Int>>()

        while (openVertices.size > 0) {

            val currentPos = openVertices.minBy { estimatedTotalCost.getValue(it) }!!

            if (currentPos == finish) {
                val path = generatePath(currentPos, cameFrom)
                return Pair(path, estimatedTotalCost.getValue(finish))
            }

            openVertices.remove(currentPos)
            closedVertices.add(currentPos)

            grid.getNeighbours(currentPos)
                .filterNot { closedVertices.contains(it) }
                .forEach { neighbour ->
                    val score = costFromStart.getValue(currentPos) + grid.moveCost(currentPos, neighbour)
                    if (score < costFromStart.getOrDefault(neighbour, 99999999)) {
                        if (!openVertices.contains(neighbour)) {
                            openVertices.add(neighbour)
                        }
                        cameFrom.put(neighbour, currentPos)
                        costFromStart.put(neighbour, score)
                        estimatedTotalCost.put(neighbour, score + grid.heuristicDistance(neighbour, finish))
                    }
                }

        }

        throw IllegalArgumentException("No Path from Start $start to Finish $finish")
    }
}