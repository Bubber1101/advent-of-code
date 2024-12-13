package com.bubber.aoc._2024

import com.bubber.aoc.Puzzle
import com.google.ortools.Loader
import com.google.ortools.linearsolver.MPSolver


class Day13(input: String) : Puzzle(input) {

    data class Machine(val aParams: Pair<Int, Int>, val bParams: Pair<Int, Int>, var x: Long, var y: Long)

    val nums = Regex("\\d{1,3}")
    val prizePlaces = Regex("\\d{1,8}")
    val machines = mutableListOf<Machine>()

    init {
        var i = 0
        while (i < inputLines.size) {
            val buttonA = nums.findAll(inputLines[i++]).map { it.value.toInt() }.toList()
            val buttonB = nums.findAll(inputLines[i++]).map { it.value.toInt() }.toList()
            val prize = prizePlaces.findAll(inputLines[i++]).map { it.value.toLong() }.toList()
            machines.add(
                Machine(
                    buttonA.first() to buttonA.last(),
                    buttonB.first() to buttonB.last(),
                    prize.first(),
                    prize.last()
                )
            )
            i++
        }
    }

    override fun solvePartOne(): Long {
        var sum = 0L
        Loader.loadNativeLibraries();
        for (machine in machines) {
            val solution = mixedIntegerProgramming(machine)
            if (solution.first > 0) {
                sum += solution.first * 3 + solution.second * 1
            }
        }

        return sum
    }

    override fun solvePartTwo(): Number {
        var sum = 0L
        for (machine in machines) {
            machine.x+=10000000000000
            machine.y+=10000000000000
        }

        Loader.loadNativeLibraries();
        for (machine in machines) {
            val solution = mixedIntegerProgramming(machine)
            if (solution.first > 0) {
                sum += solution.first * 3 + solution.second * 1
            }
        }
        return sum
    }

    //I now see that there's just one solution to each of those so using a solver is an overkill but oh well.
    private fun mixedIntegerProgramming(machine: Machine): Pair<Long, Long> {
        val solver = MPSolver.createSolver("SCIP")

        //vars
        val a = solver.makeIntVar(0.0, Double.MAX_VALUE, "a")
        val b = solver.makeIntVar(0.0, Double.MAX_VALUE, "b")

        // x = a*94 + b * 22
        // y = a*34 + b * 67
        val x1 = solver.makeConstraint(machine.x.toDouble(), machine.x.toDouble(), "x")
        x1.setCoefficient(a, machine.aParams.first.toDouble())
        x1.setCoefficient(b, machine.bParams.first.toDouble())

        val y1 = solver.makeConstraint(machine.y.toDouble(), machine.y.toDouble(), "y")
        y1.setCoefficient(a, machine.aParams.second.toDouble())
        y1.setCoefficient(b, machine.bParams.second.toDouble())

        val objective = solver.objective()
        objective.setCoefficient(a, 1.0)
        objective.setCoefficient(b, 1.0)
        objective.setMaximization()

        val resultStatus = solver.solve()

        if (resultStatus == MPSolver.ResultStatus.OPTIMAL) {
            return a.solutionValue().toLong() to b.solutionValue().toLong()
        }
        return -1L to -1L
    }
}