import java.io.File

fun sumIdsOfValidGames(file: File): Int {
    val input = file.readLines()
    val maxColourCubesPerGame = findMaxColourCubesPerGame(input)
    val validGames = findValidGames(maxColourCubesPerGame)
    return validGames.filter { !it.value }.map { it.key }.sum()
}

fun sumPowersOfMaxColourCubesPerGame(file: File): Int {
    val input = file.readLines()
    val maxColourCubesPerGame = findMaxColourCubesPerGame(input)
    return maxColourCubesPerGame.map { game -> game.value.values.reduce { acc, i -> acc * i } }.sum()
}

private fun findValidGames(maxColours: Map<Int, Map<Colour, Int>>): Map<Int, Boolean> {
    val validGames =
        maxColours.map { game -> game.key to game.value.any { it.value > availableCubesPerColour[it.key]!! } }.toMap()
    return validGames.filter { !it.value }
}

private fun findMaxColourCubesPerGame(input: List<String>): Map<Int, Map<Colour, Int>> = input.associate { line ->
    val gameNumber = line.split("Game ", ":")[1].toInt()
    val colourCounts = line.split(": ", "; ", ", ", " ").run { this.subList(2, this.size) }

    val maxColourCount = colourCounts.withIndex().fold(mutableMapOf(
        Colour.RED to 0,
        Colour.GREEN to 0,
        Colour.BLUE to 0
    )) { accumulator, indexedColourOrCount ->
        val int = indexedColourOrCount.value.toIntOrNull() ?: return@fold accumulator
        val colour = Colour.valueOf(colourCounts[indexedColourOrCount.index + 1].uppercase())
        if (int > accumulator[colour]!!) {
            accumulator[colour] = int
        }
        accumulator
    }
    gameNumber to maxColourCount
}

val availableCubesPerColour = mapOf(
    Colour.RED to 12,
    Colour.GREEN to 13,
    Colour.BLUE to 14
)

enum class Colour { RED, GREEN, BLUE }

fun main() {
    println(sumIdsOfValidGames(File("src/main/resources/day_2_input.txt"))) // 2795
    println(sumPowersOfMaxColourCubesPerGame(File("src/main/resources/day_2_input.txt"))) // 75561
}