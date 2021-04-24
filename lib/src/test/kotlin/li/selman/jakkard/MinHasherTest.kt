package li.selman.jakkard

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.*

/**
 * Testing MinHashes is rather difficult as we "randomly" generating many hash-functions, hashing string-tokens and
 * then picking the lowest hash-value — which itself is not really determinable by humans.
 * However, we can logic and heuristic based tests.
 * For the logic based test we test certain properties we know must be right.
 * For the heuristic based test, we use text-sources (for example Wikipedia articles), mutate them all slightly and
 * compare them.
 * In a sense this is a plagiarism test, where we expect the slightly mutated texts to be similar as they originate
 * from the same source.
 */
internal class MinHasherTest {

    // TODO(#test): Create a test which calculates the MinHash for the same file and calcs the similarity → 1.0

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

        val similarity = SimilarityCalculator.jaccard(minHashes1, minHashes2)
        assertThat(similarity).isGreaterThan(0.5)
        println(similarity)
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
