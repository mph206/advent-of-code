import com.google.common.base.Stopwatch
import java.util.concurrent.TimeUnit

data class Coordinate(val y: Int, val x: Int)

fun List<String>.transpose(): List<String> {
    val watch = Stopwatch.createStarted()
    val rowSize = this.size
    val columnSize = this[0].length
    val transposedList = MutableList(columnSize) { "" }
    for (i in 0 until rowSize) {
        for (j in 0 until columnSize) {
            transposedList[j] = transposedList[j] + this[i][j]
        }
    }
    println("Transpose: ${watch.elapsed(TimeUnit.MILLISECONDS)}")
    return transposedList
}

fun List<String>.reverse(): List<String> {
    val watch = Stopwatch.createStarted()
    val output = this.map { it.reversed() }
    println("Reverse: ${watch.elapsed(TimeUnit.MILLISECONDS)}")
    return output
}