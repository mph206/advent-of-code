import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

class Day13Test {
    private val calculator = Day13(File("src/test/resources/day_13_test_input.txt"))

    @Test
    fun `Part 1`() {
        assertEquals(405, calculator.part1())
    }

    @Test
    fun `Part 2`() {
        assertEquals(400, calculator.part2())
    }
}