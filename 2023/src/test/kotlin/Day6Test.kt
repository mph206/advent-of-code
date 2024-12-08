import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

class Day6Test {
    private val calculator = Day6(File("src/test/resources/day_6_test_input.txt"))
    @Test
    fun `Multiply winning scenarios for multiple races`() {
        assertEquals(288, calculator.part1())
    }

    @Test
    fun `Multiply winning scenarios for single big race`() {
        assertEquals(71503, calculator.part2())
    }
}