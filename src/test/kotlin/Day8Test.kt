import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

class Day8Test {
    @Test
    fun `1st`() {
        val calculator = Day8(File("src/test/resources/day_8_test_input(1).txt"))
        assertEquals(2, calculator.part1())
    }

    @Test
    fun `Multiply winning scenarios for multiple races`() {
        val calculator = Day8(File("src/test/resources/day_8_test_input(2).txt"))
        assertEquals(6, calculator.part1())
    }

//    @Test
//    fun `Multiply winning scenarios for single big race`() {
//        assertEquals(71503, calculator.part2())
//    }
}