import java.io.File
import java.lang.Exception
import kotlin.math.floor

class Day10(file: File) {
    private val input = file.readLines()

    fun part1(): Int {
        val visited = buildLoop()
        return (floor(visited.toSet().count().toDouble()) / 2).toInt()
    }

    fun part2(): Int {
        val visited = buildLoop()
        val corners = listOf('F', 'J', 'L', '7')
        val otherSidePairs = mapOf('F' to 'J', 'L' to '7', '7' to 'L', 'J' to 'F')
        val sameSidePairs = mapOf('L' to 'J', 'F' to '7', 'J' to 'L', '7' to 'F')
        val startPosition = findStartPosition()
        val startChar = startPosition.findCharFromExits(startPosition.findStartExits())
        return input.mapIndexed { lineIndex, line ->
            var isInsideLoop = false
            val cornersToProcess = mutableListOf<Char>()
            var groundCount = 0
            for ((charIndex, char) in line.withIndex()) {
                val actualChar = if (char == 'S') startChar else char
                when {
                    Coordinate(lineIndex, charIndex) !in visited && isInsideLoop -> groundCount++
                    Coordinate(lineIndex, charIndex) in visited -> {
                        when (actualChar) {
                            in corners -> {
                                val otherSidePair = otherSidePairs[actualChar]
                                val sameSidePair = sameSidePairs[actualChar]
                                when {
                                    otherSidePair != null && cornersToProcess.contains(otherSidePair) -> {
                                        cornersToProcess.remove(otherSidePair)
                                        isInsideLoop = !isInsideLoop
                                    }
                                    sameSidePair != null && cornersToProcess.contains(sameSidePair) -> {
                                        cornersToProcess.remove(sameSidePair)
                                    }
                                    else -> cornersToProcess.add(actualChar)
                                }
                            }
                            '|' -> isInsideLoop = !isInsideLoop
                        }
                    }
                }
            }
            groundCount
        }.sum()
    }

    private fun buildLoop(): List<Coordinate> {
        val startingPosition = findStartPosition()
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
        return visited
    }

    private fun findStartPosition(): Coordinate {
        val startingPositionY = input.indexOfFirst { it.contains('S') }
        val startingPositionX = input[startingPositionY].indexOf('S')
        return Coordinate(startingPositionY, startingPositionX)
    }

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
        return surrChars.associateWith { it.findExits() }
            .filter { coord -> coord.value != null && coord.value?.any { it == this } ?: throw Exception("Can't find start") }.keys.toList()
    }

    private fun Coordinate.findCharFromExits(exits: List<Coordinate>): Char {
        if (exits.size != 2) throw Exception("Size must be 2 to map to Char")
        val relativeExits = exits.map { Coordinate(it.y - this.y, it.x - this.x) }
        return possibleExits.filterValues { it.containsAll(relativeExits) }.keys.first()
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
    println(calculator.part2()) // 449
}