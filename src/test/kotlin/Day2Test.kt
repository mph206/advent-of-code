import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

class Day2Test {
    @Test
    fun `Can sum ids of valid games`() {
        assertEquals(8, sumIdsOfValidGames(File("src/test/resources/day_2_test_input.txt")))
    }

    @Test
    fun `Sums powers of each game's max cubes for each colour`() {
        assertEquals(2286, sumPowersOfMaxColourCubesPerGame(File("src/test/resources/day_2_test_input.txt")))
    }
}