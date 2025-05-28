
class Game(val gridSize: Int, val dictionary: Dictionary) {

    /* State variables */

    var grid: Grid = randomGrid(gridSize)
        private set

    var allWords: List<String> = dictionary.findWords(grid).sorted()
        private set

    var wordsFound = emptySet<String>()
        private set

    var currentWord = ""
        private set

    var clickedPositions = emptyList<Position>()
        private set


    /* Derived variables */

    val lastPosition get() =
        clickedPositions.lastOrNull()

    val canAddWord get() =
        currentWord in dictionary && currentWord !in wordsFound

    val currentWordNotEmpty get() =
        currentWord.isNotEmpty()


    /* Mechanism for reporting changes to the view */

    private val listeners = mutableListOf<() -> Unit>()

    fun onChange(f: () -> Unit) {
        listeners += f
    }

    fun onChangeAndNow(f: () -> Unit) {
        listeners += f
        f()
    }

    private fun reportChange() {
        for (f in listeners) f()
    }


    /* Functions */

    fun canClick(position: Position) =
        position in grid && (
            lastPosition == null ||
            (position.isNeighbor(lastPosition!!) && position !in clickedPositions)
        )

    fun click(position: Position) {
        if (!canClick(position)) throw IllegalStateException()
        clickedPositions += position
        currentWord += grid.charAt(position)
        reportChange()
    }

    fun clearWord() {
        clickedPositions = emptyList()
        currentWord = ""
        reportChange()
    }

    fun addWord() {
        if (canAddWord) {
            wordsFound += currentWord
            clearWord()
        }
    }

    fun backSpace() {
        if (currentWordNotEmpty) {
            clickedPositions = clickedPositions.dropLast(1)
            currentWord = currentWord.dropLast(1)
            reportChange()
        }
    }

    fun newGame() {
        grid = randomGrid(gridSize)
        allWords = dictionary.findWords(grid).sorted()
        wordsFound = emptySet()
        clearWord()
    }
}
