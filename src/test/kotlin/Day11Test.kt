import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

class Day11Test {
    val calculator = Day11(File("src/test/resources/day_11_test_input.txt"))

    @Test
    fun `Part 1`() {
        assertEquals(374, calculator.part1())
    }
}