package li.selman.jakkard

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.test.assertTrue

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
}