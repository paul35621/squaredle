
/**
 * Load a dictionary, one word per line.
 * Invalid lines are filtered and lines are converted to uppercase.
 */
fun loadDictionary(lines: String, alphabet: Collection<Char>, minimumWordLength: Int) =
    Dictionary(
        alphabet,
        lines
            .lines()
            .map { it.trim().uppercase() }
            .filter { word -> word.length >= minimumWordLength && word.all { char -> char in alphabet }}
            .toSet()
    )


class Dictionary(val alphabet: Collection<Char>, val wordSet: Set<String>) {

    // For example, if wordSet is {"hi", "hello"} then prefixes
    // will become {"h", "hi", "he", "hel", "hell", "hello"}.
    private val prefixes = buildSet {
        addAll(wordSet)
        for (word in wordSet) {
            for (end in 1 until word.length - 1) {
                add(word.substring(0, end))
            }
        }
    }

    operator fun contains(word: String) =
        word in wordSet

    fun findWords(grid: Grid) = buildSet {
        fun addWords(clicked: List<Position>, word: String, lastPos: Position) {
            if (word in prefixes) {
                if (word in wordSet) {
                    add(word)
                }
                for (newPos in grid.neighbors(lastPos)) {
                    if (newPos !in clicked) {
                        addWords(clicked + newPos, word + grid.charAt(newPos), newPos)
                    }
                }
            }
        }

        for (pos in grid.positions) {
            val clicked = listOf(pos)
            val word = grid.charAt(pos).toString()
            addWords(clicked, word, pos)
        }
    }

}
