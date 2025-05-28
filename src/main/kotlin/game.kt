
/*

This class represents the state of the game.

Actually, the state of the application, because the "New game" button
will keep the same game object and just reset the variables.

The view can use the onChange and onChangeAndNow methods to
be notified about changes in the state.

 */

class Game(val gridSize: Int, val dictionary: Dictionary) {

    /* State variables */

    var grid: Grid = randomGrid(gridSize, dictionary.alphabet)
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

    val canRestart get() =
        currentWordNotEmpty || wordsFound.isNotEmpty()

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
        grid = randomGrid(gridSize, dictionary.alphabet)
        allWords = dictionary.findWords(grid).sorted()
        restart()
    }

    fun restart() {
        wordsFound = emptySet()
        clearWord()
    }
}
