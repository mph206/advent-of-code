import java.io.File

class Day8(file: File) {
    private val input = file.readLines()

    fun part1(): Int {
        val instructions = input[0]
        val nodes = input.subList(2, input.size).map { line -> line.split(" = (", ", ", ")").let { it[0] to Pair(it[1], it[2]) } }.toMap()
        var visited = 0
        var finished = false
        var instructionIndex = 0
        var node = "AAA"
        while (!finished) {
            if (instructionIndex >= instructions.length) {
                instructionIndex = 0
            }
            if (instructions[instructionIndex] == 'L') {
                node = nodes[node]?.first ?: throw Exception("No left instruction for ${nodes[node]} at $instructionIndex")
            } else if (instructions[instructionIndex] == 'R') {
                node = nodes[node]?.second ?: throw Exception("No right instruction for ${nodes[node]} at $instructionIndex")
            }
            instructionIndex++
            visited++
            if (node == "ZZZ") {
                finished = true
            }
        }
        return visited
    }

//    fun part2(): Int {
//        return productOfWinningScenarios(times, distances)
//    }


}



fun main() {
    val calculator = Day8(File("src/main/resources/day_8_input.txt"))
    println(calculator.part1()) //
//    println(calculator.part2()) // 27102791
}