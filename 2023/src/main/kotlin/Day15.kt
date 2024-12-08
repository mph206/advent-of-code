import java.io.File

class Day15(file: File) {
    private val input = file.readLines().first().split(",")

    fun part1() = input.sumOf { it.calculateHashValue() }

    fun part2() = input.sortLenses().sum()

    private fun String.calculateHashValue() = this.fold(0) { accumulator, char ->
        (accumulator + char.code) * 17 % 256
    }

    private fun List<String>.sortLenses(): Map<Int, List<String>> {
        val boxes = (0..255).associateWith { mutableListOf<String>() }

        this.forEach { row ->
            val operation = if (row.last().isDigit()) row[row.length - 2] else row[row.length - 1]
            when (operation) {
                '-' -> {
                    val label = row.substring(0, row.length - 1)
                    val boxToModify = label.calculateHashValue()
                    boxes[boxToModify]?.removeIf { it.startsWith(label) }
                        ?: throw Exception("Unable to remove label $label from box $boxToModify")
                }

                '=' -> {
                    val label = row.substring(0, row.length - 2)
                    val boxToModify = label.calculateHashValue()
                    val indexToReplace = boxes[boxToModify]?.indexOfFirst { it.startsWith(label) }
                        ?: throw Exception("Unable to find index to Replace as label $label isn't found in box $boxToModify")
                    when (indexToReplace > -1) {
                        true -> boxes[boxToModify]!![indexToReplace] = row
                        false -> boxes[boxToModify]!!.add(row)
                    }
                }
            }
        }
        return boxes
    }

    private fun Map<Int, List<String>>.sum(): Int = this.map { entry ->
        entry.value.mapIndexed { index, lens ->
            (entry.key + 1) * (index + 1) * (lens.last().digitToIntOrNull() ?: 0)
        }.sum()
    }.sum()
}

fun main() {
    val calculator = Day15(File("src/main/resources/day_15_input.txt"))
    println(calculator.part1()) // 509152
    println(calculator.part2()) // 244403
}