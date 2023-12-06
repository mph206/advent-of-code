import java.io.File
import java.lang.Long.min

class Day5(file: File) {
    private val input = file.readLines()

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

    fun part2(): Long {
        val seeds = input.first().substring(7).split(" ").mapNotNull { it.toLongOrNull() }
        var minSeedValue: Long? = null
        val seedRanges = seeds.mapIndexed { index, number ->
            if (index % 2 == 0) number.until(number + seeds[index + 1]) else null
        }.filterNotNull()

        seedRanges.forEach { range ->
            var (destinationName, destinationRanges) = findDestinationNameAndValue("seed", listOf(range))
            while (destinationName != null) {
                val output = findDestinationNameAndValue(destinationName, destinationRanges)
                destinationName = output.first
                destinationRanges = output.second
            }
            minSeedValue = min(destinationRanges.minOf { it.first }, minSeedValue ?: destinationRanges.minOf { it.first })
            println("$range done!")
        }
        return minSeedValue ?: throw Exception("No minimum found")
    }

    private fun findDestinationNameAndValue(sourceName: String, sourceValue: Long): Pair<String?, Long> {
        val sectionStart = input.indexOfFirst { it.startsWith("$sourceName-to") }
        if (sectionStart < 0) return Pair(null, sourceValue)
        val row = input.subList(sectionStart + 1, input.size).takeWhile { it.isNotBlank() }
        var value: Long? = null

        run findDestinationValue@{
            row.forEach { line ->
                val (destinationStart, sourceStart, range) = line.split(" ").mapNotNull { it.toLongOrNull() }
                if (sourceValue in (sourceStart..sourceStart + range)) {
                    value = destinationStart + sourceValue - sourceStart
                    return@findDestinationValue
                }
            }
        }
        val destinationName = input[sectionStart].split("$sourceName-to-", " ")[1]
        return Pair(first = destinationName, second = value ?: sourceValue)
    }

    private fun findDestinationNameAndValue(sourceName: String, sourceRanges: List<LongRange>): Pair<String?, List<LongRange>> {
        val sectionStart = input.indexOfFirst { it.startsWith("$sourceName-to") }
        if (sectionStart < 0) return Pair(null, sourceRanges)
        val row = input.subList(sectionStart + 1, input.size).takeWhile { it.isNotBlank() }
        val outputRanges: MutableList<LongRange> = mutableListOf()
        val toProcess = sourceRanges.toMutableList()

        while (toProcess.isNotEmpty()) {
            run findDestinationValue@{
                val processingRange = toProcess.lastOrNull() ?: return@findDestinationValue
                row.forEach { line ->
                    val (destinationStart, sourceStart, range) = line.split(" ").mapNotNull { it.toLongOrNull() }
                    val destinationRange = sourceStart..sourceStart + range
                    val missingItems = processingRange.subtract(destinationRange)
                    val missingRangeStart = if (processingRange.first < destinationRange.first) processingRange.first else 0
                    val missingRangeEnd = if (processingRange.last < destinationRange.first) processingRange.last else destinationRange.first
                    val missingRangeStart2 = if (processingRange.first > destinationRange.last) processingRange.first else destinationRange.last
                    val missingRangeEnd2 = if (missingRangeStart2 != 0L) processingRange.last else 0
//                    if (missingRangeStart < missingRangeEnd)
                    val diff = destinationStart - sourceStart
                    when {
                        (missingItems.isEmpty()) -> {
                            // all of source range is in destination range
                            val shiftedRange = (processingRange.first + diff)..(processingRange.last + diff)
                            outputRanges.add(shiftedRange)
                            toProcess.remove(processingRange)
                            return@findDestinationValue
                        }
                        missingItems.size != processingRange.count() -> {
                            // some of start range is in destination range
                            // TODO make the overlap more efficient as sets are too much calculation
                            val overlap = processingRange.intersect(destinationRange)
                            val shiftedRange = (overlap.first() + diff)..(overlap.last() + diff)
                            outputRanges.add(shiftedRange)
                            toProcess.remove(processingRange)
                            toProcess.add(missingItems.first()..missingItems.last())
                            return@findDestinationValue
                        }
                    }
                }
                // not found in destination range
                outputRanges.add(processingRange)
                toProcess.remove(processingRange)
            }
        }
        val destinationName = input[sectionStart].split("$sourceName-to-", " ")[1]
        return Pair(destinationName, outputRanges)
    }
}

fun main() {
    val calculator = Day5(File("src/main/resources/day_5_input.txt"))
    println(calculator.part1()) // 196167384
    println(calculator.part2()) //
}