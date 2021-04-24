package li.selman.jakkard

import java.util.stream.Collectors
import kotlin.random.Random

object MinHasher {

    // TODO(#API): Figure out a better way to configure seed for rnd
    var seed: Int? = null

    /**
     * Generates the MinHash for the shingles of a single document by calculating the minimum hash value for each
     * shingle.
     * Meaning, it applies the hash functions h_1 on each shingle and adds the lowest result to the list and does
     * the same for all the other hash functions.
     */
    fun hash(shingles: List<String>, numberOfHashes: Int): List<Int> {
        if (shingles.isEmpty()) return emptyList()

        // see https://stackoverflow.com/questions/19701052/how-many-hash-functions-are-required-in-a-minhash-algorithm/19711615#19711615
        return createHashNumbers(numberOfHashes)
            .parallelStream()
            // !! access is safe as list is not empty
            .map { hash -> shingles.map { it.hashCode() xor hash }.minOrNull()!! }
            .collect(Collectors.toList())
    }

    private fun createHashNumbers(numberOfHashes: Int): List<Int> {
        val tmpSeed = seed
        val rnd = if (tmpSeed != null) {
            Random(tmpSeed)
        } else {
            Random
        }

        return List(numberOfHashes) { rnd.nextInt() }
    }
}
