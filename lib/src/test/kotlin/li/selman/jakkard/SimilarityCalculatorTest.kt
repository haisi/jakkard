package li.selman.jakkard

import li.selman.jakkard.SimilarityCalculator.jaccard
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.byLessThan

import org.junit.jupiter.api.Test

internal class SimilarityCalculatorTest {

    @Test
    fun emptySetSimilarity() {
        assertThat(jaccard<Any>(emptyList(), emptyList())).isEqualTo(0.0)
        assertThat(jaccard(listOf(1, 2, 3), emptyList())).isEqualTo(0.0)
        assertThat(jaccard(emptyList(), listOf(1, 2, 3))).isEqualTo(0.0)
    }

    @Test
    fun noSimilarity() {
        assertThat(jaccard(listOf(1, 2, 3), listOf(4, 5, 6))).isEqualTo(0.0)
    }

    @Test
    fun fullSimilarity() {
        assertThat(jaccard(listOf(1), listOf(1))).isEqualTo(1.0)
        assertThat(jaccard(listOf(1, 2, 3), listOf(1, 2, 3))).isEqualTo(1.0)
        assertThat(jaccard(listOf("A", "B", "C"), listOf("A", "B", "C"))).isEqualTo(1.0)
    }

    @Test
    fun partialSimilarity() {
        partialSimilarityAssertions(listOf("A"), listOf("A", "B", "C"), 0.33)
        partialSimilarityAssertions(listOf("A", "B"), listOf("A", "B", "C"), 0.66)
    }

    private fun <T> partialSimilarityAssertions(x: List<T>, y: List<T>, expected: Double, leeway: Double = 0.01) {
        // regular
        assertThat(jaccard(x, y)).isEqualTo(expected, byLessThan(leeway))
        // re-ordering parameters must have no side-effect
        assertThat(jaccard(y, x)).isEqualTo(expected, byLessThan(leeway))
        // shuffling the lists must have no side-effect
        assertThat(jaccard(y.shuffled(), x.shuffled())).isEqualTo(expected, byLessThan(leeway))
        // duplicate elements in the list have no side-effect on the set-union and set-intersection
        assertThat(jaccard(y + y, x)).isEqualTo(expected, byLessThan(leeway))
        assertThat(jaccard(y, x + x)).isEqualTo(expected, byLessThan(leeway))
        assertThat(jaccard(y + y, x + x)).isEqualTo(expected, byLessThan(leeway))
    }
}
