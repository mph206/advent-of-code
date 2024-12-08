import java.io.File

class Day8(file: File) {
    private val input = file.readLines()

    fun part1() = calculateStepsToEnd("AAA", "ZZZ")

    fun part2() = calculateStepsToEnd("A", "Z")

    private fun calculateStepsToEnd(startNodeEndsWith: String, finishNodeEndsWith: String): Long {
        val instructions = input[0]
        val nodes = input.subList(2, input.size)
            .associate { line -> line.split(" = (", ", ", ")").let { it[0] to Pair(it[1], it[2]) } }
        var visited = 0
        var instructionIndex = 0
        val processingNodes = nodes.keys.filter { it.endsWith(startNodeEndsWith) }.toMutableList()
        val finishingNodesVisitCount = nodes.keys.filter { it.endsWith(finishNodeEndsWith) }.associateWith { 0 }.toMutableMap()
        while (true) {
            if (instructionIndex >= instructions.length) {
                instructionIndex = 0
            }
            when (instructions[instructionIndex]) {
                'L' -> processingNodes.forEachIndexed { index, node ->
                    processingNodes[index] = nodes[node]?.first
                        ?: throw Exception("No left instruction for ${nodes[node]} at $instructionIndex")
                }
                'R' -> processingNodes.forEachIndexed { index, node ->
                    processingNodes[index] = nodes[node]?.second
                        ?: throw Exception("No right instruction for ${nodes[node]} at $instructionIndex")
                }
            }
            instructionIndex++
            visited++

            val finishingNodesFound = finishingNodesVisitCount.keys.intersect(processingNodes)
            if (finishingNodesFound.isNotEmpty()) {
                finishingNodesFound.forEach { node ->
                    if (finishingNodesVisitCount[node] == 0) {
                        finishingNodesVisitCount[node] = visited
                    }
                }
                if (finishingNodesVisitCount.values.all { it > 0 }) {
                    val loops = finishingNodesVisitCount.values.map { it.toLong() }
                    return findLeastCommonMultiple(loops)
                }
            }
        }
    }

    private fun findLeastCommonMultiple(numbers: List<Long>): Long {
        var result = numbers[0]
        for (i in 1 until numbers.size) {
            result = findLeastCommonMultiple(result, numbers[i])
        }
        return result
    }

    private fun findLeastCommonMultiple(a: Long, b: Long): Long {
        val largestNumber = if (a > b) a else b
        val maxLcm = a * b
        var lcm = largestNumber
        while (lcm <= maxLcm) {
            if (lcm % a == 0L && lcm % b == 0L) {
                return lcm
            }
            lcm += largestNumber
        }
        return maxLcm
    }
}

fun main() {
    val calculator = Day8(File("src/main/resources/day_8_input.txt"))
    println(calculator.part1()) // 16531
    println(calculator.part2()) // 24035773251517
}