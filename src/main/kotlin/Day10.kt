import java.io.File
import java.lang.Exception
import kotlin.math.floor

class Day10(file: File) {
    private val input = file.readLines()

    fun part1(): Int {
        val startingPositionY = input.indexOfFirst { it.contains('S') }
        val startingPositionX = input[startingPositionY].indexOf('S')
        val startingPosition = Coordinate(startingPositionY, startingPositionX)
        var nextPosition = startingPosition.findStartExits().last()
        val visited = mutableListOf(startingPosition)
        var hasNext = true
        while (hasNext) {
            visited.add(nextPosition)
            when (val next = nextPosition.findNext(visited[visited.size - 2])) {
                null -> hasNext = false
                else -> nextPosition = next
            }
        }
        return (floor(visited.toSet().count().toDouble()) / 2).toInt()
    }

    fun part2() = 1

    private fun Coordinate.findNext(previous: Coordinate) = this.findExits()?.firstOrNull { it != previous }

    private fun Coordinate.findExits(): List<Coordinate>? {
        val char = input[this.y][this.x]
        val possibleRelativeExits = possibleExits[char]
        return possibleRelativeExits?.map { Coordinate(this.y + it.y, this.x + it.x) }
    }

    private fun Coordinate.findStartExits(): List<Coordinate> {
        if (input[this.y][this.x] != 'S') throw Exception("Char ${input[this.y][this.x]} is not an S")
        val surroundingRelativeCoordinates = listOf(
            Coordinate(-1, 0),
            Coordinate(0, -1),
            Coordinate(0, 1),
            Coordinate(1, 0),
        )
        val surrChars = surroundingRelativeCoordinates
            .map {
                Coordinate(this.y + it.y, this.x + it.x)
            }
            .filter { it.y >= 0 && it.y < input.size && it.x >= 0 && it.x < input.first().length }
        return surrChars.associateWith { it.findExits() }.filter { coord -> coord.value != null && coord.value?.any { it == this } ?: throw Exception("Can't find start") }.keys.toList()
    }

    private val possibleExits = mapOf(
        '|' to listOf(Coordinate(-1, 0), Coordinate(1, 0)),
        '-' to listOf(Coordinate(0, -1), Coordinate(0, 1)),
        'L' to listOf(Coordinate(-1, 0), Coordinate(0, 1)),
        'J' to listOf(Coordinate(-1, 0), Coordinate(0, -1)),
        '7' to listOf(Coordinate(1, 0), Coordinate(0, -1)),
        'F' to listOf(Coordinate(1, 0), Coordinate(0, 1))
    )

}


fun main() {
    val calculator = Day10(File("src/main/resources/day_10_input.txt"))
    println(calculator.part1()) // 6806
    println(calculator.part2()) //
}