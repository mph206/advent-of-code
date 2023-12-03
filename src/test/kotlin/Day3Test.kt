import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

class Day3Test {
    private val calculator = Day3(File("src/test/resources/day_3_test_input.txt"))
    @Test
    fun `part 1`() {
        assertEquals(4361, calculator.part1())
    }

    @Test
    fun `part 2`() {
        assertEquals(467835, calculator.part2())
    }
}