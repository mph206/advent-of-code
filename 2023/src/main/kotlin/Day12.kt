import java.io.File
import kotlin.math.abs

class Day12(file: File) {
    private val input = file.readLines()

    fun part1(): Int {
        /*
        Sum the count of possible arrangements of broken # and working . springs
        ? values are unknown. Groups are always separate by at least one working spring
        Numbers at end of line are the number of broken springs
         */
        val input = parseInput()
        val possibleArrangeements = calculateArrangements(input)
        return possibleArrangeements.values.sum()
    }

    private fun parseInput(): List<Record> = input.map { row ->
        val (first, last) = row.split(" ")
        Record(first, last.map { char -> char.code })
    }

    private fun calculateArrangements(input: List<Record>): Map<Record, Int> {
//        Algorithm for calculating all possible combinations given some fixed values
        return mapOf(Record("1", listOf(1)) to 1)
    }
}

data class Record(val record: String, val brokenSpringCounts: List<Int>)


fun main() {
    val calculator = Day12(File("src/main/resources/day_12_input.txt"))
    println(calculator.part1()) //
}