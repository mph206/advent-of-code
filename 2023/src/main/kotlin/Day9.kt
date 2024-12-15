import java.io.File

class Day9(file: File) {
    private val input = file.readLines()

    fun part1(): Int {
        val parsedInput = input.map { line -> line.split(" ").map { it.toInt() } }
        return parsedInput.sumOf { it.findNextValueInSequence() }
    }

    fun part2() =
        input.map { line -> line.split(" ").map { it.toInt() }.reversed() }
            .sumOf { it.findNextValueInSequence() }

    private fun List<Int>.findNextValueInSequence(): Int {
        val diffs = mutableListOf(this)
        while (diffs.last().sum() != 0) {
            val newDiffs = diffs.last().mapIndexed { index, i ->
                when (index == diffs.last().size - 1) {
                    true -> null
                    false -> diffs.last()[index + 1] - i
                }
            }.filterNotNull()
            diffs.add(newDiffs)
        }
        return diffs.sumOf { it.last() }
    }
}


fun main() {
    val calculator = Day9(File("src/main/resources/day_9_input.txt"))
    println(calculator.part1()) // 1479011877
    println(calculator.part2()) // 973
}