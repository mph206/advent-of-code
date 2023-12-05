import java.io.File
import kotlin.math.pow

class Day5(file: File) {
    private val input = file.readLines()
    val seeds = input.first().substring(7).split(" ").mapNotNull { it.toLongOrNull() }
    val seedToSoil = buildMap("seed", "soil")
    val soilToFertilizer = buildMap("soil", "fertilizer")
    val fertilizerToWater = buildMap("fertilizer", "water")
    val waterToLight = buildMap("water", "light")
    val lightToTemperature = buildMap("light", "temperature")
    val temperatureToHumidity = buildMap("temperature", "humidity")
    val humidityToLocation = buildMap("humidity", "location")

    private fun buildMap(from: String, to: String): Map<Long, Long> {
        val section = input.indexOfFirst { it.contains("$from-to-$to") }
        val end = input.subList(section + 1, input.size - 1).takeWhile { it.isNotBlank() }
        val map = mutableMapOf<Long, Long>()
        end.forEach { line ->
            val (dest, start, range) = line.split(" ").mapNotNull { it.toLongOrNull() }
            // TODO: too inefficient for full data. Could we just have the Map Key as a range? Or simplify some other way?
            for (i in 0 until range) {
                map[start + i] = dest + i
            }
        }
        return map
    }

    fun part1(): Long {
        val locations = seeds.map { seed ->
            val soil = seedToSoil.getOrDefault(seed, seed)
            val fertilizer = soilToFertilizer.getOrDefault(soil, soil)
            val water = fertilizerToWater.getOrDefault(fertilizer, fertilizer)
            val light = waterToLight.getOrDefault(water, water)
            val temperature = lightToTemperature.getOrDefault(light, light)
            val humidity = temperatureToHumidity.getOrDefault(temperature, temperature)
            val location = humidityToLocation.getOrDefault(humidity, humidity)
            location
        }
        return locations.minOrNull() ?: throw Exception("No minimum found")
    }

    fun part2(): Int {
        return 1
    }
}

fun main() {
    val calculator = Day5(File("src/main/resources/day_5_input.txt"))
    println(calculator.part1()) //
    println(calculator.part2()) //
}