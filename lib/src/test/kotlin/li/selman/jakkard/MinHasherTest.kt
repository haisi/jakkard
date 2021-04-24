package li.selman.jakkard

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.*
import kotlin.math.abs

/**
 * Testing MinHashes is rather difficult as we "randomly" generating many hash-functions, hashing string-tokens and
 * then picking the lowest hash-value — which itself is not really determinable by humans.
 *
 * However, we can use logic and heuristic based tests.
 * For the logic based test we test certain properties we know must be right.
 * For the heuristic based test, we use text-sources (for example Wikipedia articles), mutate them all slightly and
 * compare them.
 *
 * In a sense this is a plagiarism test, where we expect the slightly mutated texts to be similar as they originate
 * from the same source.
 *
 * Furthermore, we can compare the similarity of the MinHash version with the Shingles version.
 * The Shingles can be considered as the baseline for the accuracy.
 * Whereas the MinHash is used to reduce time and space complexity compare to Shingles — especially in combinations
 * with locality sensitive hashing.
 *
 * Also, we can tweak the parameters. For example increasing the number of hash-functions should increase the accuracy.
 */
internal class MinHasherTest {

    // TODO(#test): Create a test which calculates the MinHash for the same file and calcs the similarity → 1.0

    // TODO(#test): Create a test where we increase the number of hashes and see if delta to shingles get smaller

    @Test
    fun tokenize() {
        val classLoader: ClassLoader = this::class.java.classLoader
        val tokens1 = readTokensFromFile(File(classLoader.getResource("wiki_erasmus_mutation_1.txt").file))
        val tokens2 = readTokensFromFile(File(classLoader.getResource("wiki_erasmus_mutation_2.txt").file))

        val shingler = Shingler()
        val shingles1 = shingler.shingle(tokens1, 5)
        val shingles2 = shingler.shingle(tokens2, 5)

        val minHashes1 = MinHasher.hash(shingles1, 100)
        val minHashes2 = MinHasher.hash(shingles2, 100)

        val singlesSimilarity = SimilarityCalculator.jaccard(shingles1, shingles2)
        val minHashSimilarity = SimilarityCalculator.jaccard(minHashes1, minHashes2)
        val delta = abs(singlesSimilarity - minHashSimilarity)
        assertThat(minHashSimilarity).isGreaterThan(0.5)
        assertThat(delta).isLessThan(0.1)

        println("Shingles similarity (slow): $singlesSimilarity")
        println("MinHash similarity (fast): $minHashSimilarity")
        println("Delta: $delta")
    }

    private fun readTokensFromFile(file: File): List<String> {
        val tokens = mutableListOf<String>()
        FileInputStream(file).reader(Charsets.UTF_8).use { reader ->
            val streamTokenizer = StreamTokenizer(reader)
            while (streamTokenizer.nextToken() != StreamTokenizer.TT_EOF) {
                if(streamTokenizer.ttype == StreamTokenizer.TT_WORD) {
                    tokens.add(streamTokenizer.sval)
                }
            }
        }
        return tokens
    }
}
