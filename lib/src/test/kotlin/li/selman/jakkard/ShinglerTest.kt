package li.selman.jakkard

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ShinglerTest {

    @Test
    fun shingle() {
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
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun shingleLengthLargerThanNumberOfTokens() {
        // given
        val shingler = Shingler()
        val inputSentence = listOf("The", "quick", "brown")

        // when
        val actual = shingler.shingle(inputSentence, 3)

        // then
        val expected = listOf(
            "The quick brown"
        )
        assertThat(expected).isEqualTo(actual)
    }
}
