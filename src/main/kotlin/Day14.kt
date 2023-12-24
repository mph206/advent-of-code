import com.google.common.base.Stopwatch
import java.io.File
import java.util.concurrent.TimeUnit

class Day14(file: File) {
    private val input = file.readLines()

    fun part1(): Int {
        val stopwatch = Stopwatch.createStarted()
            val answer = input.transpose().shift0sLeft().transpose().sumDistanceToEnd()
        println("Total ${stopwatch.elapsed(TimeUnit.MILLISECONDS)}")
        return answer
    }

    fun part2(): Int {
        val output = input.apply {
            for (i in 0..1000000000) {
                this.cycle()
            }
        }
        return output.sumDistanceToEnd()
    }

    private fun List<String>.shift0sLeft(): List<String> {
        val watch = Stopwatch.createStarted()
        val output = this.map { row ->
            var string = ""
            var oCount = 0
            var dotCount = 0

            for (char in row) {
                when (char) {
                    '#' -> {
                        repeat(oCount) { string += 'O' }
                        repeat(dotCount) { string += '.' }
                        string += '#'
                        oCount = 0
                        dotCount = 0
                    }
                    'O' -> oCount ++
                    '.' -> dotCount++
                }
            }
            repeat(oCount) { string += 'O' }
            repeat(dotCount) { string += '.' }
            string
        }

        println("shift0s ${watch.elapsed(TimeUnit.MILLISECONDS)}")
        return output
    }

    private fun List<String>.sumDistanceToEnd(): Int {
        val watch = Stopwatch.createStarted()
        val output = this.mapIndexed { index, string ->
            val distanceToEnd = this.size - index
            string.count { it == 'O' } * distanceToEnd
        }
            .sum()
        println("sumDistance ${watch.elapsed(TimeUnit.MILLISECONDS)}")
        return output
    }


    //    cycle tilts, N, W, S, E
    private fun List<String>.cycle(): List<String> {
        val north = input.transpose().shift0sLeft()
        val west = north.transpose().shift0sLeft().transpose()
        val south = west.transpose().shift0sLeft().transpose()
        val east = south.transpose().shift0sLeft().transpose()
        return east
    }
}

fun main() {
    val calculator = Day14(File("src/main/resources/day_14_input.txt"))
    println(calculator.part1()) // 113486
//    println(calculator.part2()) //
}