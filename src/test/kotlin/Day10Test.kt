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

//    @Test
//    fun `Part 2`() {
//        assertEquals(2, calculator.part2())
//    }
}