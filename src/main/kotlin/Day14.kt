import java.io.File

class Day14(file: File) {
    private val input = file.readLines()

    fun part1() = input.transpose().shiftOsLeft().transpose().sumDistanceToEnd()

    fun part2(): Int {
        val cyclesToPerform = 1000000000
        val previousStates = mutableListOf(input)
        var cycleStart = 0

        while (previousStates.size < cyclesToPerform) {
            val newState = previousStates.last().cycle()

            if (newState in previousStates) {
                cycleStart = previousStates.indexOf(newState)
                break
            }

            previousStates.add(newState)
        }

        val cycleLength = previousStates.size - cycleStart

        val matchingCyclePosition = ((cyclesToPerform - cycleStart) % cycleLength) + cycleStart

        return previousStates[matchingCyclePosition].sumDistanceToEnd()
    }

    private fun List<String>.shiftOsLeft(): List<String> {
        val output = this.toMutableList().map { it.toMutableList() }
        this.forEachIndexed { rowIndex, row ->
            var insertIndex = 0
            row.forEachIndexed { columnIndex, char ->
                when (char) {
                    '#' -> {
                        insertIndex = columnIndex + 1
                    }

                    'O' -> {
                        output[rowIndex][columnIndex] = '.'
                        output[rowIndex][insertIndex] = 'O'
                        insertIndex++
                    }
                }
            }
        }

        return output.map { it.joinToString("") }
    }

    private fun List<String>.sumDistanceToEnd(): Int {
        return this.mapIndexed { index, string ->
            val distanceToEnd = this.size - index
            string.count { it == 'O' } * distanceToEnd
        }
            .sum()
    }

    private fun List<String>.cycle(): List<String> {
        val north = this.transpose().shiftOsLeft()
        val west = north.transpose().shiftOsLeft()
        val south = west.transpose().reverse().shiftOsLeft()
        val east = south.reverse().transpose().reverse().shiftOsLeft()

        return east.reverse()
    }
}

fun main() {
    val calculator = Day14(File("src/main/resources/day_14_input.txt"))
    println(calculator.part1()) // 113486
    println(calculator.part2()) // 104409
}