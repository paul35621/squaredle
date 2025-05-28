import io.qt.widgets.QPushButton
import io.qt.widgets.QSizePolicy
import io.qt.widgets.QWidget

class GridView(val grid: Grid) : QWidget() {

    init {
        setLayoutGrid {
            for ((x, y) in grid.indices2d) {
                val button = QPushButton(grid.chars[x][y].toString())
                button.setSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Expanding)
                addWidget(button, x, y)
            }
        }
    }

}
