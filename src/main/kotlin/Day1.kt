import java.io.File

fun sumIntOfFirstAndLastDigitsOnEachLine(file: File, includingTextDigits: Boolean): Int {
    val input = file.readLines()
    return input.sumOf { line ->
        val parsedInput = if (includingTextDigits) line.parseStringDigits() else line
        println((parsedInput.first { it.isDigit() }.toString() + parsedInput.last { it.isDigit() }.toString()).toInt())
        println("-")
        (parsedInput.first { it.isDigit() }.toString() + parsedInput.last { it.isDigit() }.toString()).toInt()
    }
}

fun String.parseStringDigits(): String {
    var newString = ""
    var pointer = 0
    while (pointer < this.length) {
        val remainingString = this.substring(pointer)
        val matchingDigitString = stringToDigit.keys.firstOrNull { remainingString.startsWith(it) }
        if (matchingDigitString != null) {
            newString += stringToDigit[matchingDigitString]
        } else {
            newString += this[pointer]
        }
        pointer++
    }
    return newString
}

val stringToDigit = mapOf(
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