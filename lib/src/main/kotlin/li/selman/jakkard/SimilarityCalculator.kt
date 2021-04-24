package li.selman.jakkard

object SimilarityCalculator {

    fun <T> jaccard(a: List<T>, b: List<T>): Double {
        if (a.isEmpty() || b.isEmpty()) {
            return 0.0
        }
        val intersection: Double = a.intersect(b).size.toDouble()
        val union: Double = a.union(b).size.toDouble()
        return 1.0 * intersection / union
    }
}
