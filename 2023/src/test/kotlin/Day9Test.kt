import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

class Day9Test {
    private val calculator = Day9(File("src/test/resources/day_9_test_input.txt"))

    @Test
    fun `Part 1`() {
        assertEquals(114, calculator.part1())
    }

    @Test
    fun `Part 2`() {
        assertEquals(2, calculator.part2())
    }
}