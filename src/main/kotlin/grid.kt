
data class Position(val x: Int, val y: Int) {
    init {
        if (x < 0 || y < 0) throw IllegalArgumentException("Invalid coordinates: $x, $y")
    }

    fun isNeighbor(other: Position): Boolean {
        val dx = x - other.x
        val dy = y - other.y
        return dx >= -1 && dx <= 1 && dy >= -1 && dy <= 1 && this != other
    }
}

data class Grid(
    val size: Int,               // width and height
    val chars: List<List<Char>>  // chars[x][y]
) {
    val indices1d = 0 until size
    val positions = indices1d.flatMap { x -> indices1d.map { y -> Position(x, y) } }

    init {
        // Check input
        if (size < 1) {
            throw IllegalArgumentException("Invalid size argument: $size")
        }
        if (chars.size != size || chars.any { it.size != size }) {
            throw IllegalArgumentException("Invalid chars argument: not $size x $size")
        }
    }

    operator fun contains(pos: Position) =
        pos.x < size && pos.y < size

    fun charAt(pos: Position): Char {
        if (pos !in this) throw IllegalArgumentException()
        return chars[pos.x][pos.y]
    }

    fun neighbors(pos: Position) = buildList(8) {
        for (dx in -1..1) {
            val x = pos.x + dx
            if (x in indices1d) {
                for (dy in -1..1) {
                    if (dx != 0 || dy != 0) {
                        val y = pos.y + dy
                        if (y in indices1d) add(Position(x, y))
                    }
                }
            }
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
