import com.google.common.base.Stopwatch
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
        val seedRanges = seeds.mapIndexedNotNull { index, number ->
            if (index % 2 == 0) number.until(number + seeds[index + 1]) else null
        }

        seedRanges.forEach { range ->
            var (destinationName, destinationRanges) = findDestinationNameAndValue("seed", listOf(range))
            while (destinationName != null) {
                val output = findDestinationNameAndValue(destinationName, destinationRanges)
                destinationName = output.first
                destinationRanges = output.second
            }
            minSeedValue =
                min(destinationRanges.minOf { it.first }, minSeedValue ?: destinationRanges.minOf { it.first })
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

    private fun findDestinationNameAndValue(
        sourceName: String,
        sourceRanges: List<LongRange>,
    ): Pair<String?, List<LongRange>> {
        val stopwatch = Stopwatch.createStarted()

        val sectionStart = input.indexOfFirst { it.startsWith("$sourceName-to") }
        if (sectionStart < 0) return Pair(null, sourceRanges)
        val row = input.subList(sectionStart + 1, input.size).takeWhile { it.isNotBlank() }
        val outputRanges: MutableList<LongRange> = mutableListOf()
        val toProcess = sourceRanges.toMutableList()
        println("parsedInput in ${stopwatch.elapsed()}")

        while (toProcess.isNotEmpty()) {
            run findDestinationValue@{
                val processingRange = toProcess.lastOrNull() ?: return@findDestinationValue
                row.forEach { line ->
                    val (destinationStart, sourceStart, range) = line.split(" ").mapNotNull { it.toLongOrNull() }
                    val destinationRange = sourceStart..sourceStart + range
                    val missingRanges = getMissingRanges(processingRange, destinationRange)
                    println("Got missing ranges in ${stopwatch.elapsed()}")
                    val missingRangesSize = missingRanges.sumOf { it.last - it.first + 1 }
                    val diff = destinationStart - sourceStart
                    when {
                        (missingRanges.isEmpty()) -> {
                            // all of source range is in destination range
                            val shiftedRange = (processingRange.first + diff)..(processingRange.last + diff)
                            outputRanges.add(shiftedRange)
                            toProcess.remove(processingRange)
                            return@findDestinationValue
                        }
                        missingRangesSize != processingRange.count().toLong() -> {
                            // some of start range is in destination range
                            missingRanges.forEach { missingRange ->
                                val overlap = getOverlap(processingRange, destinationRange)
                                val shiftedRange = (overlap.first + diff)..(overlap.last + diff)
                                outputRanges.add(shiftedRange)
                                toProcess.remove(processingRange)
                                toProcess.add(missingRange)
                            }
                            return@findDestinationValue
                        }
                    }
                println("Finished row in ${stopwatch.elapsed()}")
                }
                // not found in destination range
                outputRanges.add(processingRange)
                toProcess.remove(processingRange)
                println("Found destination value: ${stopwatch.elapsed()}")
            }
        }
        val destinationName = input[sectionStart].split("$sourceName-to-", " ")[1]
        return Pair(destinationName, outputRanges)
    }

    private fun getMissingRanges(processingRange: LongRange, destinationRange: LongRange): List<LongRange> {
        val missingRangeStart = if (processingRange.first < destinationRange.first) processingRange.first else null
        val missingRangeEnd =
            if (processingRange.last < destinationRange.first) processingRange.last else destinationRange.first - 1
        val missingRangeStart2 =
            if (processingRange.first < destinationRange.last) destinationRange.last + 1 else processingRange.first
        val missingRangeEnd2 = if (processingRange.last > destinationRange.last) processingRange.last else null
        val missingRanges = mutableListOf<LongRange>()
        if (missingRangeStart != null) {
            missingRanges.add(missingRangeStart..missingRangeEnd)
        }
        if (missingRangeEnd2 != null) {
            missingRanges.add(missingRangeStart2..missingRangeEnd2)
        }
        return missingRanges
    }

    private fun getOverlap(processingRange: LongRange, destinationRange: LongRange): LongRange {
        val overlapStart = when {
            destinationRange.first > processingRange.first -> destinationRange.first
            else -> processingRange.first
        }
        val overlapEnd = when {
            destinationRange.last < processingRange.last -> destinationRange.last
            else -> processingRange.last
        }
        return overlapStart..overlapEnd
    }
}

fun main() {
    val calculator = Day5(File("src/main/resources/day_5_input.txt"))
    println(calculator.part1()) // 196167384
    println(calculator.part2()) // 125742457 is too high
}