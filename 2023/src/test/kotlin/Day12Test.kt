import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

class Day12Test {
    private val calculator = Day12(File("src/test/resources/day_12_test_input.txt"))

    @Test
    fun `Part 1`() {
        assertEquals(21, calculator.part1())
    }

//    @Test
//    fun `Part 2 - case 1`() {
//        assertEquals(1030L, calculator.sumDistancesBetweenChars(10, '#'))
//    }
//
//    @Test
//    fun `Part 2 - case 2`() {
//        assertEquals(8410L, calculator.sumDistancesBetweenChars(100, '#'))
//    }
}