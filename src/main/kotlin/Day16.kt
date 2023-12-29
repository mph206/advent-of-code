import java.io.File

class Day16(file: File) {
    private val input = file.readLines()

    fun part1() = buildRayPath(DirectedCoordinate(0, 0, Direction.EAST)).count()

    fun part2() = getPossibleStartingCoordinates().map { buildRayPath(it) }.maxOf { it.count() }

    private fun buildRayPath(startingCoordinate: DirectedCoordinate): Set<Coordinate> {
        val visited = mutableSetOf<DirectedCoordinate>()
        val coordinatesToProcess = mutableSetOf(startingCoordinate)

        while (coordinatesToProcess.isNotEmpty()) {
            val currentCoordinate = coordinatesToProcess.first()

            if (currentCoordinate.isOutOfRange() || currentCoordinate in visited) {
                coordinatesToProcess.remove(currentCoordinate)
                continue
            }
            visited.add(currentCoordinate)

            coordinatesToProcess.addAll(getNextCoordinates(currentCoordinate))
            coordinatesToProcess.remove(currentCoordinate)
        }

        return visited.map { Coordinate(it.y, it.x) }.toSet()
    }

    private fun getNextCoordinates(currentCoordinate: DirectedCoordinate): List<DirectedCoordinate> {
        return when (input[currentCoordinate.y][currentCoordinate.x]) {
            '\\' -> when (currentCoordinate.direction) {
                Direction.NORTH -> listOf(Direction.WEST.toDirectedCoordinate(currentCoordinate))
                Direction.EAST -> listOf(Direction.SOUTH.toDirectedCoordinate(currentCoordinate))
                Direction.SOUTH -> listOf(Direction.EAST.toDirectedCoordinate(currentCoordinate))
                Direction.WEST -> listOf(Direction.NORTH.toDirectedCoordinate(currentCoordinate))
            }

            '/' -> when (currentCoordinate.direction) {
                Direction.NORTH -> listOf(Direction.EAST.toDirectedCoordinate(currentCoordinate))
                Direction.EAST -> listOf(Direction.NORTH.toDirectedCoordinate(currentCoordinate))
                Direction.SOUTH -> listOf(Direction.WEST.toDirectedCoordinate(currentCoordinate))
                Direction.WEST -> listOf(Direction.SOUTH.toDirectedCoordinate(currentCoordinate))
            }

            '|' -> when (currentCoordinate.direction) {
                Direction.NORTH -> listOf(Direction.NORTH.toDirectedCoordinate(currentCoordinate))
                Direction.EAST, Direction.WEST -> listOf(
                    Direction.NORTH.toDirectedCoordinate(currentCoordinate),
                    Direction.SOUTH.toDirectedCoordinate(currentCoordinate)
                )

                Direction.SOUTH -> listOf(Direction.SOUTH.toDirectedCoordinate(currentCoordinate))
            }

            '-' -> when (currentCoordinate.direction) {
                Direction.NORTH, Direction.SOUTH -> listOf(
                    Direction.EAST.toDirectedCoordinate(currentCoordinate),
                    Direction.WEST.toDirectedCoordinate(currentCoordinate)
                )

                Direction.EAST -> listOf(Direction.EAST.toDirectedCoordinate(currentCoordinate))
                Direction.WEST -> listOf(Direction.WEST.toDirectedCoordinate(currentCoordinate))
            }

            else -> listOf(currentCoordinate.direction.toDirectedCoordinate(currentCoordinate))
        }
    }

    private fun DirectedCoordinate.isOutOfRange() = this.y !in input.indices || this.x !in 0 until input[0].length

    private fun Direction.toDirectedCoordinate(currentCoordinate: DirectedCoordinate) = when (this) {
        Direction.NORTH -> DirectedCoordinate(currentCoordinate.y - 1, currentCoordinate.x, Direction.NORTH)
        Direction.EAST -> DirectedCoordinate(currentCoordinate.y, currentCoordinate.x + 1, Direction.EAST)
        Direction.SOUTH -> DirectedCoordinate(currentCoordinate.y + 1, currentCoordinate.x, Direction.SOUTH)
        Direction.WEST -> DirectedCoordinate(currentCoordinate.y, currentCoordinate.x - 1, Direction.WEST)
    }

    private fun getPossibleStartingCoordinates() = buildList {
        addAll(List(input.size) { DirectedCoordinate(it, 0, Direction.EAST) })
        addAll(List(input.size) { DirectedCoordinate(it, input.size - 1, Direction.WEST) })
        addAll(List(input.first().length) { DirectedCoordinate(0, it, Direction.SOUTH) })
        addAll(List(input.first().length) { DirectedCoordinate(input.first().length - 1, it, Direction.NORTH) })
    }
}

data class DirectedCoordinate(val y: Int, val x: Int, val direction: Direction)

fun main() {
    val calculator = Day16(File("src/main/resources/day_16_input.txt"))
    println(calculator.part1()) // 6605
    println(calculator.part2()) // 6766
}