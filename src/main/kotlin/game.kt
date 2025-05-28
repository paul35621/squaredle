
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

    // Emitted when any of the variables have changed
    val updated = PSignal<Unit>()


    /* Derived variables */

    val lastPosition get() =
        clickedPositions.lastOrNull()

    val canAddWord get() =
        currentWord in dictionary && currentWord !in wordsFound

    val currentWordNotEmpty get() =
        currentWord.isNotEmpty()


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
        updated.emit(Unit)
    }

    fun clearWord() {
        clickedPositions = emptyList()
        currentWord = ""
        updated.emit(Unit)
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
            updated.emit(Unit)
        }
    }

    fun newGame() {
        grid = randomGrid(gridSize)
        allWords = dictionary.findWords(grid).sorted()
        wordsFound = emptySet()
        clearWord()
    }
}
