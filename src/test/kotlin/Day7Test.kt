import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

class Day7Test {
    private val calculator = Day7(File("src/test/resources/day_7_test_input.txt"))
    @Test
    fun `Sums bids * ranking for the given hands`() {
        assertEquals(6440, calculator.part1())
    }

    @Test
    fun `Sums bids * ranking for the given hands, including Jokers (wildcards)`() {
        assertEquals(5905, calculator.part2())
    }
}