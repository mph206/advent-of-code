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

private fun findMaxColourCubesPerGame(input: List<String>): Map<Int, Map<Colour, Int>> {
    return input.associate { line ->
        val gameNumber = line.split("Game ", ":")[1].toInt()
        val colourCounts = line.split(": ", "; ", ", ", " ").run { this.subList(2, this.size)}
        val maxColourCount = mutableMapOf(
            Colour.Red to 0,
            Colour.Green to 0,
            Colour.Blue to 0
        )

        colourCounts.forEachIndexed { idx, string ->
            val int = string.toIntOrNull()
            if (int != null) {
                val colour = colourCounts[idx + 1].toColour()
                if (int > maxColourCount[colour]!!) maxColourCount[colour] = int
            }
        }
        gameNumber to maxColourCount
    }
}

val availableCubesPerColour = mapOf(
    Colour.Red to 12,
    Colour.Green to 13,
    Colour.Blue to 14
)

enum class Colour {
    Red, Green, Blue;
}

private fun String.toColour() = when (this) {
    "red" -> Colour.Red
    "green" -> Colour.Green
    "blue" -> Colour.Blue
    else -> throw Exception("colour $this is unsupported")
}

fun main() {
    println(sumIdsOfValidGames(File("src/main/resources/day_2_input.txt"))) // 2795
    println(sumPowersOfMaxColourCubesPerGame(File("src/main/resources/day_2_input.txt"))) // 75561
}