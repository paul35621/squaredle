
data class Grid(
    val size: Int,               // width and height
    val chars: List<List<Char>>  // chars[x][y]
) {
    val indices1d = 0 until size
    val indices2d = indices1d.flatMap { x -> indices1d.map { y -> Pair(x, y) } }

    init {
        // Check input
        if (size < 1) {
            throw IllegalArgumentException("Invalid size argument: $size")
        }
        if (chars.size != size || chars.any { it.size != size }) {
            throw IllegalArgumentException("Invalid chars argument: not $size x $size")
        }
    }
}

const val defaultGridSize = 4
val defaultAlphabet = ('A'..'Z').toList()

fun randomGrid(size: Int = defaultGridSize, alphabet: Collection<Char> = defaultAlphabet) = Grid(
    size,
    List(size) {
        List(size) { alphabet.random() }
    }
)
