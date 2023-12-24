data class Coordinate(val y: Int, val x: Int)

fun List<String>.transpose(): List<String> {
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