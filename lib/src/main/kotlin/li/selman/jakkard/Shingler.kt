package li.selman.jakkard

class Shingler {

    /**
     * Returns `tokens.size - shingleSize + 1` number of shingles.
     */
    fun shingle(tokens: List<String>, shingleSize: Int): List<String> {
        val totalNumberOfShingles = tokens.size - shingleSize + 1
        val shingles = ArrayList<String>(totalNumberOfShingles)

        val idxLastTokenToStartFrom = totalNumberOfShingles - 1
        for (i in 0..idxLastTokenToStartFrom) {
            val shingle = tokens.subList(i, i + shingleSize).joinToString(separator = " ")
            shingles.add(shingle)
        }
        return shingles
    }
}
