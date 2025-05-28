
class Game(val grid: Grid, val dictionary: Set<String>) {

    var wordsFound = emptySet<String>()
        private set

    var currentWord = ""
        private set

    var clickedPositions = emptyList<Position>()
        private set

    val lastPosition get() =
        clickedPositions.lastOrNull()

    val canAddWord get() =
        currentWord in dictionary && currentWord !in wordsFound

    fun canClick(position: Position) =
        grid.contains(position) && (
            lastPosition == null ||
            (position.isNeighbor(lastPosition!!) && position !in clickedPositions)
        )

    fun clearWord() {
        clickedPositions = emptyList()
        currentWord = ""
        updated.emit(Unit)
    }

    fun addWord() {
        if (!canAddWord) throw IllegalStateException()
        wordsFound += currentWord
        clearWord()
    }

    // Emitted when any of the variables have changed
    val updated = PSignal<Unit>()
}
