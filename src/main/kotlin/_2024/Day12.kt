package com.bubber.aoc._2024

import com.bubber.aoc.Puzzle

class Day12(input: String) : Puzzle(input) {

    /**   1
     *    _
     * 4| A | 2
     *    _
     *    3
     */

    data class Plot(
        val crop: String,
        var areaId: Int = -1,
        val fences: MutableList<Int> = mutableListOf(),
        val coords: Pair<Int, Int>
    )

    var fenceSides = mapOf(
        1 to 0 to 3, //down
        0 to 1 to 2, // right
        0 to -1 to 4, // left
        -1 to 0 to 1 // up
    )

    var reverseFence = mapOf(
        3 to 1,
        1 to 3,
        2 to 4,
        4 to 2
    )

    var plotMap = mutableMapOf<Int, List<Plot>>()

    override fun solvePartOne(): Int {
        var areaIdCount = 0
        var sum = 0
        var plots = mutableListOf<MutableList<Plot>>()
        inputLines.forEachIndexed { j, line ->
            plots.add(mutableListOf())
            line.forEachIndexed { i, char ->
                plots[j].add(Plot(crop = char.toString(), coords = Pair(j, i)))
            }
        }

        plots.forEachIndexed { j, line ->
            line.forEachIndexed { i, plot ->
                var neighborCoords = getPointsAround(plot.coords)
                //add map border fences
                neighborCoords.filterNot(::isLegal)
                    .map { it.first - plot.coords.first to it.second - plot.coords.second }
                    .map { fenceSides.get(it) }.filterNotNull().forEach { plot.fences.add(it) }
                var neighbors = neighborCoords.filter(::isLegal).map { plots[it.first][it.second] }

                //add areaId

                var sameCropPlotAreaIds =
                    neighbors
                        .filter { it.crop == plot.crop && it.areaId != -1 }
                        .map { it.areaId }
                if (sameCropPlotAreaIds.isNotEmpty()) {
                    plot.areaId = sameCropPlotAreaIds.first()

                } else {
                    plot.areaId = ++areaIdCount
                }

                //add Fence to different crop

                neighbors.filter{ it.crop != plot.crop}
                    .map { it.coords.first - plot.coords.first to it.coords.second - plot.coords.second }
                    .map { fenceSides.get(it) }.filterNotNull().forEach { plot.fences.add(it) }

                plotMap.merge(plot.areaId, mutableListOf(plot), {a, b -> a + b})
            }
        }

        for ( (_, plotList) in plotMap)
        {
            sum += plotList.size * plotList.map { it.fences.count() }.sum()
            println("Crop: ${plotList.first().crop}, ${plotList.size} * ${plotList.map { it.fences.count() }.sum()}")
        }
        return sum
    }

    override fun solvePartTwo(): Long {
        var sum = 0L

        return sum
    }
}