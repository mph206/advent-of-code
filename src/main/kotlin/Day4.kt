import java.io.File
import kotlin.math.pow

class Day4(file: File) {
    private val input = file.readLines()

    fun part1(): Int = input.sumOf { 2.0.pow(countMatches(it) - 1).toInt() }

    fun part2(): Int {
        val cardsWon = mutableMapOf<Int, Int>()
        for (i in 1..input.size) {
            cardsWon[i] = 1
        }
        input.forEachIndexed { index, card ->
            val matches = countMatches(card)
            repeat(cardsWon[index + 1]!!) {
                for (i in 1..matches) {
                    if (cardsWon[index + 1] != null) cardsWon[index + 1 + i] = cardsWon[index + 1 + i]!!.plus(1)
                }
            }
        }
        return cardsWon.values.sum()
    }

    private fun countMatches(card: String): Int {
        val winningNumbers = card.split(":", "|")[1].split(" ").filter { it.isNotBlank() }
        val myNumbers = card.split(":", "|")[2].split(" ").filter { it.isNotBlank() }
        return winningNumbers.count { myNumbers.contains(it) }
    }
}

fun main() {
    val calculator = Day4(File("src/main/resources/day_4_input.txt"))
    println(calculator.part1()) // 21919
    println(calculator.part2()) // 9881048
}