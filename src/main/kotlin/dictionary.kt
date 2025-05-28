
class Dictionary(val wordSet: Set<String>) {

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

fun loadDictionary() =
    Dictionary(
        object{}.javaClass.getResource("wordlist.txt")!!.readText().lines()
            .map { it.trim().uppercase() }
            .filter { word -> word.length >= 4 && word.all { char -> char in defaultAlphabet }}
            .toSet()
    )
