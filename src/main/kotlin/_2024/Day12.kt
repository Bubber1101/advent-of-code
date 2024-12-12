package com.bubber.aoc._2024

import com.bubber.aoc.Puzzle

class Day12(input: String) : Puzzle(input) {

    data class Plot(
        val crop: String,
        var areaId: Int = -1,
        val fences: MutableList<Int> = mutableListOf(),
        val coords: Pair<Int, Int>
    )

    /**   1
     *    _
     * 4| A | 2
     *    _
     *    3
     */
    private var fenceSides = mapOf(
        1 to 0 to 3, //down
        0 to 1 to 2, // right
        0 to -1 to 4, // left
        -1 to 0 to 1 // up
    )

    var plotMap = mutableMapOf<Int, List<Plot>>()
    var plots = mutableListOf<MutableList<Plot>>()

    private fun prepare() {
        plotMap = mutableMapOf()
        plots = mutableListOf()
        var areaIdCount = 0
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
                if (plot.areaId == -1) {
                    var sameCropPlotAreaIds =
                        neighbors
                            .filter { it.crop == plot.crop && it.areaId != -1 }
                            .map { it.areaId }
                    if (sameCropPlotAreaIds.isNotEmpty()) {
                        plot.areaId = sameCropPlotAreaIds.first()
                    } else {
                        plot.areaId = ++areaIdCount
                    }
                }

                //add Fence to different crop

                neighbors.filter { it.crop != plot.crop }
                    .map { it.coords.first - plot.coords.first to it.coords.second - plot.coords.second }
                    .map { fenceSides.get(it) }.filterNotNull().forEach { plot.fences.add(it) }

                plotMap.merge(plot.areaId, mutableListOf(plot), { a, b -> a + b })
                flood(plot.areaId, plot.coords, plot.crop)
            }
        }
    }

    private fun flood(areaId: Int, coords: Pair<Int, Int>, crop: String) {
        getPointsAround(coords).filter(::isLegal).map { plots[it.first][it.second] }
            .filter { it.crop == crop && it.areaId != areaId }.forEach {
                it.areaId = areaId
                flood(areaId, it.coords, crop)
            }
    }

    override fun solvePartOne(): Int {
        var sum = 0
        prepare()
        for ((_, plotList) in plotMap) {
            sum += plotList.size * plotList.map { it.fences.count() }.sum()
//            println("Crop: ${plotList.first().crop}, ${plotList.size} * ${plotList.map { it.fences.count() }.sum()}")
        }
        return sum
    }

    override fun solvePartTwo(): Int {
        var sum = 0
        prepare()
        for ((_, plotList) in plotMap) {
            var sides = 0
            val fenceDirectionToCoords =
                plotList.map { it to it.fences }
                    .flatMap { (plot, fences) -> fences.map { it to plot.coords } }
                    .groupBy { it.first }
            for ((dir, coordList) in fenceDirectionToCoords) {
                var previous = -1 to -1
                var cordslist = coordList
                if (dir and 1 == 0) {
                    cordslist = coordList.sortedBy { it.second.second }
                }
                for (coords in cordslist) {
                    val curr = coords.second
                    if (dir and 1 == 1) {
                        if (previous.first != curr.first || previous.second != curr.second - 1) {
                            sides++
                        }
                    } else {
                        if (previous.second != curr.second || previous.first != curr.first - 1) {
                            sides++
                        }
                    }
                    previous = curr
                }
            }
            sum += plotList.size * sides
//            println("Crop: ${plotList.first().crop}, ${plotList.size} * ${sides}")
        }
        return sum
    }
}