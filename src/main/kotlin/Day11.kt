import java.io.File
import kotlin.math.abs

class Day11(file: File) {
    private val input = file.readLines()

    fun part1(): Int =
        input
            .duplicateEmptyRowsAndColumns()
            .findCharacterCoordinates('#')
            .buildPairs()
            .findDistances()
            .sum()

    fun part2(): Int {
        return 1
    }

    private fun List<String>.duplicateEmptyRowsAndColumns(): List<String> {
        val mutableInput = this.map { it.toMutableList() }.toMutableList()
        val emptyRows = this.mapIndexedNotNull { index, row -> if (!row.contains('#')) index else null }
            .mapIndexed { index, i -> i + index }
        val emptyColumns = this.first().mapIndexedNotNull { index, _ ->
            val column = this.map { it[index] }
            if (!column.contains('#')) index else null
        }.mapIndexed { index, i -> i + index }
        val rowLength = this.first().length
        emptyRows.forEach { mutableInput.add(it, MutableList(rowLength) { '.' }) }
        emptyColumns.forEach { mutableInput.forEach { row -> row.add(it, '.') } }
        return mutableInput.map { it.joinToString("") }
    }

    private fun List<String>.findCharacterCoordinates(character: Char): List<Coordinate> =
        this.mapIndexed { lineIndex, line ->
            line.mapIndexedNotNull { charIndex, char ->
                if (char == character) Coordinate(lineIndex, charIndex) else null
            }
        }.flatten()

    private fun List<Coordinate>.buildPairs(): List<Pair<Coordinate, Coordinate>> =
        this.flatMapIndexed { currentItemIndex, currentCoordinate ->
            this.mapIndexedNotNull { index, otherCoordinate ->
                if (index > currentItemIndex) Pair(currentCoordinate, otherCoordinate) else null
            }
        }

    private fun List<Pair<Coordinate, Coordinate>>.findDistances(): List<Int> =
        this.map {
            (abs(it.second.y - it.first.y) + abs(it.second.x - it.first.x))
        }
}


fun main() {
    val calculator = Day11(File("src/main/resources/day_11_input.txt"))
    println(calculator.part1()) // 9214785
    println(calculator.part2()) //
}