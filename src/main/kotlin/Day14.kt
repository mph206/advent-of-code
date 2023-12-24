import java.io.File

class Day14(file: File) {
    private val input = file.readLines()

    fun part1() = input.transpose().shift0sLeft().transpose().sumDistanceToEnd()

//    cycle tilts, N, W, S, E
//    Repeat 1000000000 times
    fun part2() = 1

    private fun List<String>.shift0sLeft(): List<String> {
        val output = this.toMutableList().map { it.toMutableList() }

        for (i in this.indices) {
            for (j in 0 until this[0].length) {
                when (val char = this[i][j]) {
                    'O' -> {
                        val moveToIndex = output[i].subList(0, j).indexOfLast { it in listOf('#', 'O') } + 1
                        output[i][j] = '.'
                        output[i][moveToIndex] = char
                    }
                    else -> output[i][j] = char
                }
            }
        }

        return output.map { it.joinToString("") }
    }

    private fun List<String>.sumDistanceToEnd() =
        this.mapIndexed { index, string ->
            val distanceToEnd = this.size - index
            string.count { it == 'O' } * distanceToEnd
        }
            .sum()
}

fun main() {
    val calculator = Day14(File("src/main/resources/day_14_input.txt"))
    println(calculator.part1()) //
    println(calculator.part2()) //
}