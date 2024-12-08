data class Coordinate(val y: Int, val x: Int)

enum class Direction {
    NORTH, EAST, SOUTH, WEST
}

fun List<String>.transpose(): List<String> {
    val rowSize = this.size
    val columnSize = this[0].length
    val transposedList = MutableList(columnSize) { CharArray(rowSize) }
    for (i in 0 until rowSize) {
        for (j in 0 until columnSize) {
            transposedList[j][i] = this[i][j]
        }
    }
    return transposedList.map { it.joinToString("") }
}

fun List<String>.reverse(): List<String> = this.map { it.reversed() }
