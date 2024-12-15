import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

class Day16Test {
    private val calculator = Day16(File("src/test/resources/day_16_test_input.txt"))

    @Test
    fun `Part 1`() {
        assertEquals(46, calculator.part1())
    }

    @Test
    fun `Part 2`() {
        assertEquals(51, calculator.part2())
    }
}