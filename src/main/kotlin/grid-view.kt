import generalqt.connect
import generalqt.layoutGrid
import io.qt.widgets.QPushButton
import io.qt.widgets.QSizePolicy

fun layoutGrid(game: Game) = layoutGrid {
    for (position in game.grid.positions) {
        val button = QPushButton()
        button.setSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Expanding)
        button.clicked.connect {
            game.click(position)
        }

        game.onChangeAndNow {
            button.enabled = game.canClick(position)
            button.text = game.grid.charAt(position).toString()
        }

        addWidget(button, position.x, position.y)
    }
}
