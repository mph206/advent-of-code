import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

class Day5Test {
    private val calculator = Day5(File("src/test/resources/day_5_test_input.txt"))
    @Test
    fun `Finds lowest location`() {
        assertEquals(35L, calculator.part1())
    }

//    @Test
//    fun ``() {
//        assertEquals(30, calculator.part2())
//    }
}