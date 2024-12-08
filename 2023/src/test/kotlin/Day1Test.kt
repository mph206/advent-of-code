import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

class Day1Test {
    @Test
    fun `sums int derived from first and list digit in each line`() {
        assertEquals(sumIntOfFirstAndLastDigitsOnEachLine(File("src/test/resources/day_1_test_input.txt"), false), 142)
    }

    @Test
    fun `sums int derived from first and list digit in each line, including where digit is written as a string`() {
        assertEquals(sumIntOfFirstAndLastDigitsOnEachLine(File("src/test/resources/day_1_test_input(2).txt"), true), 281)
    }
}