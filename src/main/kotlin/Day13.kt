import java.io.File

class Day13(file: File) {
    private val input = file.readLines()

    fun part1() = parseInput().sumLinesBeforeReflectionLine()

    fun part2() = parseInput().sumLinesBeforeReflectionLine(true)

    private fun parseInput(): List<List<String>> {
        val output = mutableListOf<List<String>>()
        val currentGrid = mutableListOf<String>()
        input.forEach { row ->
            when (row.isEmpty()) {
                true -> {
                    output.add(currentGrid.toList())
                    currentGrid.clear()
                }
                false -> currentGrid.add(row)
            }
        }
        output.add(currentGrid.toList())
        return output
    }

    private fun List<List<String>>.sumLinesBeforeReflectionLine(hasSmudge: Boolean = false): Int =
        this.sumOf { grid ->
            grid.findMirrorIndex(hasSmudge).let { it?.times(100) } ?: grid.transpose().findMirrorIndex(hasSmudge) ?: 0
        }

    private fun List<String>.findMirrorIndex(hasSmudge: Boolean): Int? {
        val gridsToSearch = when (hasSmudge) {
            true -> buildSmudgePossibilities(this)
            false -> listOf(this)
        }

        val invalidIndex = if (hasSmudge) listOf(this).findIndex( null) else null

        return gridsToSearch.findIndex(invalidIndex)
    }

    private fun List<List<String>>.findIndex(invalidIndex: Int?): Int? {
        this.forEach { grid ->
            for (index in 1 until grid.size) {
                val previous = grid.subList(0, index).reversed().joinToString("")
                val next = grid.subList(index, grid.size).joinToString("")
                if ((previous.startsWith(next) || next.startsWith(previous))
                    && index != invalidIndex) {
                    return index
                }
            }
        }
        return null
    }

    private fun buildSmudgePossibilities(grid: List<String>): List<List<String>> {
        return grid.flatMapIndexed { rowIndex, row ->
            row.mapIndexed { charIndex, char ->
                val output = grid.toMutableList().map { it.toMutableList() }
                output[rowIndex][charIndex] = if (char == '#') '.' else '#'
                output.map { it.joinToString("") }
            }
        }
    }

    private fun List<String>.transpose(): List<String> {
        val rowSize = this.size
        val columnSize = this[0].length
        val transposedList = MutableList(columnSize) { "" }
        for (i in 0 until rowSize) {
            for (j in 0 until columnSize) {
                transposedList[j] = transposedList[j] + this[i][j]
            }
        }
        return transposedList
    }
}

fun main() {
    val calculator = Day13(File("src/main/resources/day_13_input.txt"))
    println(calculator.part1()) // 33735
    println(calculator.part2()) // 38063
}