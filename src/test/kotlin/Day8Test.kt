import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

class Day8Test {
    @Test
    fun `1st`() {
        val calculator = Day8(File("src/test/resources/day_8_test_input(1).txt"))
        assertEquals(2, calculator.part2())
    }

    @Test
    fun `part 1 - example 2`() {
        val calculator = Day8(File("src/test/resources/day_8_test_input(2).txt"))
        assertEquals(6, calculator.part2())
    }

    @Test
    fun `part2`() {
        val calculator = Day8(File("src/test/resources/day_8_test_input(3).txt"))
        assertEquals(6, calculator.part2())
    }
}