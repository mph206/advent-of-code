import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

class Day15Test {
    private val calculator = Day15(File("src/test/resources/day_15_test_input.txt"))

    @Test
    fun `Part 1`() {
        assertEquals(1320, calculator.part1())
    }

    @Test
    fun `Part 2`() {
        assertEquals(145, calculator.part2())
    }
}