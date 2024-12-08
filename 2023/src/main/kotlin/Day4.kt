import java.io.File
import kotlin.math.pow

class Day4(file: File) {
    private val input = file.readLines()

    fun part1() = input.sumOf { card -> 2.0.pow(card.countMatches() - 1).toInt() }

    fun part2(): Int {
        val cardsWon = mutableMapOf<Int, Int>().apply { putAll(input.indices.map { it to 1 }) }

        input.forEachIndexed { index, card ->
            val matches = card.countMatches()
            1.rangeTo(matches).forEach {
                val cardNumber = index + it
                if (cardsWon.containsKey(cardNumber)) {
                    cardsWon[cardNumber] = cardsWon[cardNumber]!!.plus(cardsWon[index]!!)
                }
            }
        }
        return cardsWon.values.sum()
    }

    private fun String.countMatches(): Int {
        val winningNumbers = this.split(":", "|")[1].split(" ").filter { it.isNotBlank() }
        val myNumbers = this.split(":", "|")[2].split(" ").filter { it.isNotBlank() }
        return winningNumbers.count { myNumbers.contains(it) }
    }
}

fun main() {
    val calculator = Day4(File("src/main/resources/day_4_input.txt"))
    println(calculator.part1()) // 21919
    println(calculator.part2()) // 9881048
}