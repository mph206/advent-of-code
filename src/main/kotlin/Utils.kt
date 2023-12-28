import com.google.common.base.Stopwatch
import java.util.concurrent.TimeUnit

data class Coordinate(val y: Int, val x: Int)

enum class Direction {
    NORTH, EAST, SOUTH, WEST
}

fun List<String>.transpose(): List<String> {
//    val watch = Stopwatch.createStarted()
    val rowSize = this.size
    val columnSize = this[0].length
    val transposedList = MutableList(columnSize) { CharArray(rowSize) }
    for (i in 0 until rowSize) {
        for (j in 0 until columnSize) {
            transposedList[j][i] = this[i][j]
        }
    }
    val new = transposedList.map { it.joinToString("") }
//    println("Transpose: ${watch.elapsed(TimeUnit.MILLISECONDS)}")
    return new
}

fun List<String>.reverse(): List<String> {
//    val watch = Stopwatch.createStarted()
    //    println("Reverse: ${watch.elapsed(TimeUnit.MILLISECONDS)}")
    return map { it.reversed() }
}