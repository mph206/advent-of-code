import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

class Day3Test {
    private val calculator = Day3(File("src/test/resources/day_3_test_input.txt"))
    @Test
    fun `Sums part numbers`() {
        assertEquals(4361, calculator.sumPartNumbers())
    }

    @Test
    fun `Sums gear ratios`() {
        assertEquals(467835, calculator.sumGearRatios())
    }
}