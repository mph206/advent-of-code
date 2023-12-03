import java.io.File

class Day3(file: File) {
    private val input = file.readLines()
    private val asterisksWithNumbers = mutableMapOf<Coordinate, MutableList<Int>>()

    fun part1(): Int {
        val matrix = input.flatMapIndexed { lineIndex, line ->
            line.mapIndexed { charIndex, char ->
                when {
                    char.isDigit() && (charIndex == 0 || !line[charIndex - 1].isDigit()) -> {
                        val number = buildNumber(charIndex, line, lineIndex)
                        val surrounding = buildSurroundingCoordinates(input, number.second)
                        val surroundContainsSymbols = isAdjacentToSymbol(input, surrounding, number.first.toInt())
                        number.first.toInt() to surroundContainsSymbols
                    }
                    else -> null
                }
            }.filterNotNull()
        }
        return matrix.filter { it.second }.sumOf { it.first }
    }

    fun part2(): Int {
        val asterisks = input.flatMapIndexed { lineIndex, line -> line.mapIndexed { charIndex, char ->
            if (char == '*') Coordinate(lineIndex, charIndex) else null
            }
        }.filterNotNull().forEach {
            asterisksWithNumbers[it] = mutableListOf()
        }
        part1()
        return asterisksWithNumbers.filter { it.value.size > 1 }.map { it.value.reduce { acc, i -> acc * i } }.sum()
    }

    private fun buildNumber(charIndex: Int, line: String, lineIndex: Int): Pair<String, List<Coordinate>> {
        var index = charIndex
        var num = ""
        val position = mutableListOf<Coordinate>()
        while (line.length > index && line[index].isDigit()) {
            num += line[index]
            position.add(Coordinate(lineIndex, index))
            index++
        }
        return Pair(num, position)
    }

    private fun buildSurroundingCoordinates(input: List<String>, numberCoordinates: List<Coordinate>): List<Coordinate> {
        val surrounds = mutableSetOf<Coordinate>()
        val lineLength = input.first().length
        numberCoordinates.forEach { coordinate ->
            run {
                surrounds.add(Coordinate(coordinate.y - 1, coordinate.x))
                surrounds.add(Coordinate(coordinate.y + 1, coordinate.x))
                surrounds.add(Coordinate(coordinate.y - 1, coordinate.x - 1))
                surrounds.add(Coordinate(coordinate.y, coordinate.x - 1))
                surrounds.add(Coordinate(coordinate.y + 1, coordinate.x - 1))
                surrounds.add(Coordinate(coordinate.y - 1, coordinate.x + 1))
                surrounds.add(Coordinate(coordinate.y, coordinate.x + 1))
                surrounds.add(Coordinate(coordinate.y + 1, coordinate.x + 1))
            }
        }
        return surrounds.filter { it.x >= 0 && it.y >= 0 && it.x < lineLength && it.y < input.size }
    }

    private fun isAdjacentToSymbol(input: List<String>, coordinates: List<Coordinate>, number: Int): Boolean {
        coordinates.filter { input[it.y][it.x] == '*' }.forEach { coord -> asterisksWithNumbers[coord]?.add(number) }
        return coordinates.any { !input[it.y][it.x].isDigit() && !input[it.y][it.x].isLetter() && input[it.y][it.x] != '.' }
    }
}

fun main() {
    val calculator = Day3(File("src/main/resources/day_3_input.txt"))
    println(calculator.part1()) // 507214
    println(calculator.part2()) // 72553319
}