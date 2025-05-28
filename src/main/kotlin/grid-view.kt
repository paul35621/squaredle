import generalqt.connect
import io.qt.widgets.QPushButton
import io.qt.widgets.QSizePolicy

fun layoutGrid(game: Game) = layoutGrid {
    for (position in game.grid.indices2d) {
        val button = QPushButton()
        button.setSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Expanding)
        button.clicked.connect {
            game.click(position)
        }

        game.updated.connectAndNow {
            button.enabled = game.canClick(position)
            button.text = game.grid.charAt(position).toString()
        }

        addWidget(button, position.x, position.y)
    }
}
