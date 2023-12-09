import java.io.File

class Day7(file: File) {
    private val input = file.readLines()

    fun part1(): Int = calculateHandScores(withJoker = false)

    fun part2(): Int = calculateHandScores(withJoker = true)

    private fun calculateHandScores(withJoker: Boolean) =
        input.map { line ->
            val (cards, bid) = line.split(" ").let { Pair(it[0], it[1].toInt()) }
            val handType = if (withJoker) categoriseHandWithJokers(cards) else categoriseHand(cards)
            val cardValues = cards.map { it.getCardValue(withJoker) }.toString()
            Hand(cards, handType, bid, cardValues)
        }
            .sortedBy { it }
            .mapIndexed { index, hand -> (index + 1) * hand.bid }
            .sum()

    private fun categoriseHand(cards: String) =
        cards.groupBy { it }.map { it.value.size }.sortedDescending().getHandType()

    private fun categoriseHandWithJokers(cards: String): HandType {
        val jokerCount = cards.count { it == 'J' }
        val cardCounts = cards.filter { it != 'J' }.groupBy { it }.map { it.value.size }.toMutableList()
        val maxNonJokerCount = cardCounts.maxOfOrNull { it } ?: 0
        if (jokerCount > 0 && jokerCount != 5) {
            cardCounts[cardCounts.indexOfFirst { it == maxNonJokerCount }] += jokerCount
        } else if (jokerCount == 5) {
            cardCounts.add(jokerCount)
        }
        cardCounts.sortDescending()
        return cardCounts.getHandType()
    }

    private fun List<Int>.getHandType() = when (this) {
        listOf(5) -> HandType.FiveOfKind
        listOf(4, 1) -> HandType.FourOfKind
        listOf(3, 2) -> HandType.FullHouse
        listOf(3, 1, 1) -> HandType.ThreeOfKind
        listOf(2, 2, 1) -> HandType.TwoPair
        listOf(2, 1, 1, 1) -> HandType.OnePair
        else -> HandType.HighCard
    }

    private fun Char.getCardValue(withJoker: Boolean) = mutableMapOf(
        '2' to 'a',
        '3' to 'b',
        '4' to 'c',
        '5' to 'd',
        '6' to 'e',
        '7' to 'f',
        '8' to 'g',
        '9' to 'h',
        'T' to 'i',
        'J' to 'j',
        'Q' to 'k',
        'K' to 'l',
        'A' to 'm',
    ).apply { if (withJoker) this['J'] = '*' }.toMap()[this]
}

data class Hand(val hand: String, val handType: HandType, val bid: Int, val cardValues: String) : Comparable<Hand> {
    override fun compareTo(other: Hand): Int = when {
        this.handType != other.handType -> this.handType.ordinal compareTo other.handType.ordinal
        this.cardValues != other.cardValues -> this.cardValues compareTo other.cardValues
        else -> 0
    }
}

enum class HandType {
    HighCard, OnePair, TwoPair, ThreeOfKind, FullHouse, FourOfKind, FiveOfKind;
}

fun main() {
    val calculator = Day7(File("src/main/resources/day_7_input.txt"))
    println(calculator.part1()) // 247815719
    println(calculator.part2()) // 248747492
}