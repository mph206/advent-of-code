import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

class Day10Test {

    @Test
    fun `Part 1, example 1`() {
        val calculator = Day10(File("src/test/resources/day_10_test_input(1).txt"))
        assertEquals(4, calculator.part1())
    }

    @Test
    fun `Part 1, example 2`() {
        val calculator = Day10(File("src/test/resources/day_10_test_input(2).txt"))
        assertEquals(8, calculator.part1())
    }

    @Test
    fun `Part 2 - example 1`() {
        val calculator = Day10(File("src/test/resources/day_10_test_input(3).txt"))
        assertEquals(4, calculator.part2())
    }

    @Test
    fun `Part 2 - example 2`() {
        val calculator = Day10(File("src/test/resources/day_10_test_input(4).txt"))
        assertEquals(4, calculator.part2())
    }

    @Test
    fun `Part 2 - example 3`() {
        val calculator = Day10(File("src/test/resources/day_10_test_input(5).txt"))
        assertEquals(8, calculator.part2())
    }

    @Test
    fun `Part 2 - example 4`() {
        val calculator = Day10(File("src/test/resources/day_10_test_input(6).txt"))
        assertEquals(10, calculator.part2())
    }
}