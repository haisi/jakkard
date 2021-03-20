package li.selman.jakkard

import kotlin.test.Test
import kotlin.test.assertEquals

internal class ShinglerTest {

    @Test fun shingle() {
        // given
        val shingler = Shingler()
        val inputSentence = listOf("The", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog")

        // when
        val actual = shingler.shingle(inputSentence, 5)

        // then
        val expected = listOf(
                "The quick brown fox jumps",
                "quick brown fox jumps over",
                "brown fox jumps over the",
                "fox jumps over the lazy",
                "jumps over the lazy dog"
        )
        assertEquals(expected, actual)
    }

    @Test fun `shingle length larger lhan number of tokens`() {
        // given
        val shingler = Shingler()
        val inputSentence = listOf("The", "quick", "brown")

        // when
        val actual = shingler.shingle(inputSentence, 3)

        // then
        val expected = listOf(
            "The quick brown"
        )
        assertEquals(expected, actual)
    }
}
