import java.io.File

class Day7(file: File) {
    private val input = file.readLines()

    fun part1(): Int {
        val sortedByType = input.map { line ->
            val (cards, bid) = line.split(" ").let { Pair(it[0], it[1].toInt()) }
            val uniqueCards = cards.toSet()
            val handType = when {
                uniqueCards.size == 1 -> HandType.FiveOfKind
                cards.any { card -> cards.count { it == card } == 4 } -> HandType.FourOfKind
                cards.any { card -> cards.count { it == card } == 3 } && uniqueCards.size == 2 -> HandType.FullHouse
                cards.any { card -> cards.count { it == card } == 3 } -> HandType.ThreeOfKind
                uniqueCards.size == 3 -> HandType.TwoPair
                uniqueCards.size == 4 -> HandType.OnePair
                else -> HandType.HighCard
            }
            val cardValues = cards.map { cardValues[it] }.toString()
            Hand(cards, handType, bid, cardValues)
        }.sortedBy { it }

        return sortedByType.mapIndexed { index, hand -> (index + 1) * hand.bid }.sum()
    }

    fun part2(): Int {
        return 1
    }

    val cardValues = mapOf(
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
    )
}

data class Hand(val hand: String, val handType: HandType, val bid: Int, val cardValues: String): Comparable<Hand> {
    override fun compareTo(other: Hand): Int = when {
        this.handType != other.handType -> this.handType.value compareTo other.handType.value
        this.cardValues != other.cardValues -> this.cardValues compareTo other.cardValues
        else -> 0
    }
}

enum class HandType {
    FiveOfKind, FourOfKind, FullHouse, ThreeOfKind, TwoPair, OnePair, HighCard;

    val value get() = when (this) {
        FiveOfKind -> 7
        FourOfKind -> 6
        FullHouse -> 5
        ThreeOfKind -> 4
        TwoPair -> 3
        OnePair -> 2
        HighCard -> 1
    }
}

fun main() {
    val calculator = Day7(File("src/main/resources/day_7_input.txt"))
    println(calculator.part1()) // 247815719
    println(calculator.part2()) //
}