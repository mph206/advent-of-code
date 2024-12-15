import java.io.File
import kotlin.math.abs

class Day11(file: File) {
    private val input = file.readLines()

    fun sumDistancesBetweenChars(expansionFactor: Int, char: Char): Long =
        input
            .findCharacterCoordinates(char)
            .offsetCoordinatesByExpansionFactor((expansionFactor))
            .buildPairs()
            .findDistances()
            .sum()

    private fun List<Coordinate>.offsetCoordinatesByExpansionFactor(expansionFactor: Int): List<Coordinate> {
        val emptyRows = input.mapIndexedNotNull { index, row -> if (!row.contains('#')) index else null }
        val emptyColumns = input.first().mapIndexedNotNull { index, _ ->
            val column = input.map { it[index] }
            if (!column.contains('#')) index else null
        }
        return this.map { coordinate ->
            val previousRowCount = emptyRows.count { it < coordinate.y }
            val previousColumnCount = emptyColumns.count { it < coordinate.x }
            Coordinate(
                coordinate.y + previousRowCount * (expansionFactor - 1).coerceAtLeast(0),
                coordinate.x + previousColumnCount * (expansionFactor - 1).coerceAtLeast(0)
            )
        }
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

    private fun List<Pair<Coordinate, Coordinate>>.findDistances(): List<Long> =
        this.map {
            (abs(it.second.y - it.first.y) + abs(it.second.x - it.first.x)).toLong()
        }
}


fun main() {
    val calculator = Day11(File("src/main/resources/day_11_input.txt"))
    println(calculator.sumDistancesBetweenChars(2, '#')) // 9214785
    println(calculator.sumDistancesBetweenChars(1000000, '#')) // 613686987427
}