import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

class Day4Test {
    private val calculator = Day4(File("src/test/resources/day_4_test_input.txt"))
    @Test
    fun `Calculates points won on scratch cards`() {
        assertEquals(13, calculator.part1())
    }

    @Test
    fun `Calculates scratch cards won`() {
        assertEquals(30, calculator.part2())
    }
}