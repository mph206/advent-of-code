import java.io.File


class Day5(file: File) {
    private val input = file.readLines()

    private fun findDestinationNameAndValue(sourceName: String, sourceValue: Long): Pair<String?, Long> {
        val sectionStart = input.indexOfFirst { it.contains("$sourceName-to") }
        if (sectionStart < 0) return Pair(null, sourceValue)
        val destinationName = input[sectionStart].split("$sourceName-to-", " ").getOrNull(1)
        val row = input.subList(sectionStart + 1, input.size).takeWhile { it.isNotBlank() }
        var value: Long? = null

        row.forEach { line ->
            val (dest, start, range) = line.split(" ").mapNotNull { it.toLongOrNull() }
            if (start < sourceValue && start + range >= sourceValue) {
                value = sourceValue + dest - start
            }
        }
        return Pair(first = destinationName, second = value ?: sourceValue)
    }

    fun part1(): Long {
        val seeds = input.first().substring(7).split(" ").mapNotNull { it.toLongOrNull() }
        val locations = seeds.map { seed ->
            var (destinationName, destinationValue) = findDestinationNameAndValue("seed", seed)
            while (destinationName != null) {
                val output = findDestinationNameAndValue(destinationName, destinationValue)
                destinationName = output.first
                destinationValue = output.second
            }
            destinationValue
        }
        return locations.minOrNull() ?: throw Exception("No minimum found")
    }

    fun part2(): Int {
        return 1
    }
}

fun main() {
    val calculator = Day5(File("src/main/resources/day_5_input.txt"))
    println(calculator.part1()) // 196167384
    println(calculator.part2()) //
}