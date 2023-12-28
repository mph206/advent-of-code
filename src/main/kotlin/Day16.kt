import java.io.File

class Day16(file: File) {
    private val input = file.readLines()

    fun part1() = buildRayPath().count()

//     TODO simplify buildRayPath function
//    Part 2: Ray can start from any side in any direction, find most energized path
    fun part2() = 1

    private fun buildRayPath(): List<Coordinate> {
        val visited = mutableListOf<DirectedCoordinate>()

        val coordinatesToProcess = mutableSetOf(DirectedCoordinate(0, 0, Direction.EAST))

        while (coordinatesToProcess.isNotEmpty()) {
            val currentCoordinate = coordinatesToProcess.firstOrNull() ?: return visited.map { Coordinate(it.y, it.x) }.distinct()

            if (currentCoordinate.isOutOfRange() || currentCoordinate in visited) {
                coordinatesToProcess.remove(currentCoordinate)
                continue
            }

            visited.add(currentCoordinate)

            when (input[currentCoordinate.y][currentCoordinate.x]) {
                '\\' -> when (currentCoordinate.direction) {
                    Direction.NORTH -> coordinatesToProcess.add(DirectedCoordinate(currentCoordinate.y, currentCoordinate.x - 1, Direction.WEST))
                    Direction.EAST -> coordinatesToProcess.add(DirectedCoordinate(currentCoordinate.y + 1, currentCoordinate.x, Direction.SOUTH))
                    Direction.SOUTH -> coordinatesToProcess.add(DirectedCoordinate(currentCoordinate.y, currentCoordinate.x + 1, Direction.EAST))
                    Direction.WEST -> coordinatesToProcess.add(DirectedCoordinate(currentCoordinate.y - 1, currentCoordinate.x, Direction.NORTH))
                }

                '/' -> when (currentCoordinate.direction) {
                    Direction.NORTH -> coordinatesToProcess.add(DirectedCoordinate(currentCoordinate.y, currentCoordinate.x + 1, Direction.EAST))
                    Direction.EAST -> coordinatesToProcess.add(DirectedCoordinate(currentCoordinate.y - 1, currentCoordinate.x, Direction.NORTH))
                    Direction.SOUTH -> coordinatesToProcess.add(DirectedCoordinate(currentCoordinate.y, currentCoordinate.x - 1, Direction.WEST))
                    Direction.WEST -> coordinatesToProcess.add(DirectedCoordinate(currentCoordinate.y + 1, currentCoordinate.x, Direction.SOUTH))
                }

                '|' -> when (currentCoordinate.direction) {
                    Direction.NORTH -> coordinatesToProcess.add(
                        DirectedCoordinate(
                            currentCoordinate.y - 1,
                            currentCoordinate.x,
                            Direction.NORTH
                        )
                    )

                    Direction.EAST, Direction.WEST -> coordinatesToProcess.addAll(
                        listOf(
                            DirectedCoordinate(currentCoordinate.y - 1, currentCoordinate.x, Direction.NORTH),
                            DirectedCoordinate(currentCoordinate.y + 1, currentCoordinate.x, Direction.SOUTH)
                        )
                    )

                    Direction.SOUTH -> coordinatesToProcess.add(
                        DirectedCoordinate(
                            currentCoordinate.y + 1,
                            currentCoordinate.x,
                            Direction.SOUTH
                        )
                    )
                }

                '-' -> when (currentCoordinate.direction) {
                    Direction.NORTH, Direction.SOUTH -> coordinatesToProcess.addAll(
                        listOf(
                            DirectedCoordinate(currentCoordinate.y, currentCoordinate.x + 1, Direction.EAST),
                            DirectedCoordinate(currentCoordinate.y, currentCoordinate.x - 1, Direction.WEST)
                        )
                    )

                    Direction.EAST -> coordinatesToProcess.add(DirectedCoordinate(currentCoordinate.y, currentCoordinate.x + 1, Direction.EAST))
                    Direction.WEST -> coordinatesToProcess.add(DirectedCoordinate(currentCoordinate.y, currentCoordinate.x - 1, Direction.WEST))
                }

                '.' -> coordinatesToProcess.add(getNextPosition(currentCoordinate))
            }
            coordinatesToProcess.remove(currentCoordinate)
        }

        return visited.map { Coordinate(it.y, it.x) }.distinct()
    }

    private fun DirectedCoordinate.isOutOfRange() = this.y !in input.indices || this.x !in 0 until input[0].length

    private fun getNextPosition(coordinate: DirectedCoordinate): DirectedCoordinate {
        return when (coordinate.direction) {
            Direction.NORTH -> DirectedCoordinate(coordinate.y - 1, coordinate.x, coordinate.direction)
            Direction.EAST -> DirectedCoordinate(coordinate.y, coordinate.x + 1, coordinate.direction)
            Direction.SOUTH -> DirectedCoordinate(coordinate.y + 1, coordinate.x, coordinate.direction)
            Direction.WEST -> DirectedCoordinate(coordinate.y, coordinate.x - 1, coordinate.direction)
        }
    }
}

    data class DirectedCoordinate(val y: Int, val x: Int, val direction: Direction)

fun main() {
    val calculator = Day16(File("src/main/resources/day_16_input.txt"))
    println(calculator.part1()) // 6605
//    println(calculator.part2()) //
}