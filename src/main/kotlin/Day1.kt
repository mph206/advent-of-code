import java.io.File

fun sumIntOfFirstAndLastDigitsOnEachLine(file: File, includeTextNumbers: Boolean): Int {
    val input = file.readLines()
    return input.sumOf { line ->
        val parsedInput = if (includeTextNumbers) line.convertSpelledDigitsToDigits() else line
        (parsedInput.first { it.isDigit() }.toString() + parsedInput.last { it.isDigit() }.toString()).toInt()
    }
}

fun String.convertSpelledDigitsToDigits(): String = this.mapIndexed { index, _ ->
    val spelledDigit = spelledDigitsToDigits.keys.firstOrNull { this.substring(index).startsWith(it) }
    if (spelledDigit != null) {
        spelledDigitsToDigits[spelledDigit] ?: throw Exception("Digit string not found")
    } else {
        this[index].toString()
    }
}.joinToString()

val spelledDigitsToDigits = mapOf(
    "one" to "1",
    "two" to "2",
    "three" to "3",
    "four" to "4",
    "five" to "5",
    "six" to "6",
    "seven" to "7",
    "eight" to "8",
    "nine" to "9"
)

fun main() {
    println(sumIntOfFirstAndLastDigitsOnEachLine(File("src/main/resources/day_1_input.txt"), false)) // 55208
    println(sumIntOfFirstAndLastDigitsOnEachLine(File("src/main/resources/day_1_input.txt"), true)) // 54578
}