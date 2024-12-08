import java.io.File

class Day6(file: File) {
    private val input = file.readLines()

    fun part1(): Long {
        val (times, distances) = getTimesAndDistances()
        return productOfWinningScenarios(times, distances)
    }

    fun part2(): Long {
        val (times, distances) = getSingleTimeAndDistance()
        return productOfWinningScenarios(times, distances)
    }

    private fun productOfWinningScenarios(times: List<Long>, distances: List<Long>): Long =
        times.mapIndexed { index, time ->
            var winningCount = 0L
            for (i in 1 until time) {
                val buttonPressedTime = i * 1
                val travelledLength = buttonPressedTime * (time - i)
                if (travelledLength > distances[index]) {
                    winningCount++
                }
            }
            winningCount
        }.reduce { acc, item -> acc * item }

    private fun getTimesAndDistances(): Pair<List<Long>, List<Long>> {
        val parsedInput = input.map { line -> line.split(" ").filter { it.isNotBlank() } }
        val times = parsedInput[0].takeLast(parsedInput[0].size - 1).map { it.toLong() }
        val distances = parsedInput[1].takeLast(parsedInput[1].size - 1).map { it.toLong() }
        return times to distances
    }

    private fun getSingleTimeAndDistance(): Pair<List<Long>, List<Long>> {
        val parsedInput = input.map { line ->
            line.split(":", " ").filterIndexed { index, item -> item.isNotBlank() && index != 0 }.joinToString("")
        }
        val times = listOf(parsedInput.first().toLong())
        val distances = listOf(parsedInput.last().toLong())
        return times to distances

    }
}

fun main() {
    val calculator = Day6(File("src/main/resources/day_6_input.txt"))
    println(calculator.part1()) // 3316275
    println(calculator.part2()) // 27102791
}