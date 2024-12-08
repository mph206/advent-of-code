import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

class Day14Test {
    private val calculator = Day14(File("src/test/resources/day_14_test_input.txt"))

    @Test
    fun `Part 1`() {
        assertEquals(136, calculator.part1())
    }

    @Test
    fun `Part 2`() {
        assertEquals(64, calculator.part2())
    }
}