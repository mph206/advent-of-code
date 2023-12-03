import java.io.File

class Day3(file: File) {
    private val input = file.readLines()

    fun sumPartNumbers(): Int = input.flatMapIndexed { lineIndex, line ->
        line.mapIndexed { charIndex, char ->
            if (char.isDigit() && (charIndex == 0 || !line[charIndex - 1].isDigit())) {
                val currentNumber = buildNumber(charIndex, line, lineIndex)
                val surroundingCoordinates = buildSurroundingCoordinates(input, currentNumber.coordinates)
                return@mapIndexed currentNumber.value to isAdjacentToSymbol(surroundingCoordinates)
            }
            null
        }.filterNotNull()
    }.filter { it.second }.sumOf { it.first }

    fun sumGearRatios(): Int {
        val asteriskNumbers = mutableMapOf<Coordinate, MutableList<Int>>()
        input.forEachIndexed { lineIndex, line ->
            line.forEachIndexed { charIndex, char ->
                if (char.isDigit() && (charIndex == 0 || !line[charIndex - 1].isDigit())) {
                    val currentNumber = buildNumber(charIndex, line, lineIndex)
                    val surroundingCoordinates = buildSurroundingCoordinates(input, currentNumber.coordinates)
                    findAdjacentAsterisks(surroundingCoordinates).forEach {
                        asteriskNumbers[it]?.add(currentNumber.value) ?: asteriskNumbers.put(it,
                            mutableListOf(currentNumber.value))
                    }
                }
            }
        }
        return asteriskNumbers.filter { it.value.size > 1 }.map { it.value.reduce { acc, i -> acc * i } }.sum()
    }

    private fun buildNumber(charIndex: Int, line: String, lineIndex: Int): NumberWithCoordinates {
        var index = charIndex
        var num = ""
        val coordinates = mutableListOf<Coordinate>()
        while (line.length > index && line[index].isDigit()) {
            num += line[index]
            coordinates.add(Coordinate(lineIndex, index))
            index++
        }
        return NumberWithCoordinates(num.toInt(), coordinates)
    }

    private fun buildSurroundingCoordinates(input: List<String>, numberCoordinates: List<Coordinate>) =
        numberCoordinates
            .flatMap { coordinate ->
                listOf(Coordinate(coordinate.y - 1, coordinate.x - 1),
                    Coordinate(coordinate.y - 1, coordinate.x),
                    Coordinate(coordinate.y - 1, coordinate.x + 1),
                    Coordinate(coordinate.y, coordinate.x - 1),
                    Coordinate(coordinate.y, coordinate.x + 1),
                    Coordinate(coordinate.y + 1, coordinate.x - 1),
                    Coordinate(coordinate.y + 1, coordinate.x),
                    Coordinate(coordinate.y + 1, coordinate.x + 1))
            }
            .toSet()
            .filter { it.x >= 0 && it.y >= 0 && it.x < input.first().length && it.y < input.size }

    private fun isAdjacentToSymbol(coordinates: List<Coordinate>): Boolean =
        coordinates.any { !input[it.y][it.x].isDigit() && !input[it.y][it.x].isLetter() && input[it.y][it.x] != '.' }

    private fun findAdjacentAsterisks(coordinates: List<Coordinate>): List<Coordinate> =
        coordinates.filter { input[it.y][it.x] == '*' }
}

data class NumberWithCoordinates(val value: Int, val coordinates: List<Coordinate>)

fun main() {
    val calculator = Day3(File("src/main/resources/day_3_input.txt"))
    println(calculator.sumPartNumbers()) // 507214
    println(calculator.sumGearRatios()) // 72553319
}