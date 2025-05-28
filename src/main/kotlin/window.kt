import generalqt.addHBox
import generalqt.addVBox
import generalqt.widgetVBox
import io.qt.core.Qt
import io.qt.gui.QKeyEvent
import io.qt.widgets.QMainWindow
import io.qt.widgets.QPushButton

private const val spacing = 10  // Pixels spacing

class Window(val game: Game) : QMainWindow() {

    init {
        /***** Widgets *****/

        val buttonRestart = QPushButton("Restart")
        buttonRestart.clicked.connect(game::restart)

        val buttonNewGame = QPushButton("New game")
        buttonNewGame.clicked.connect(game::newGame)


        /***** Update widgets *****/

        game.onChangeAndNow {
            buttonRestart.enabled = game.canRestart
        }


        /***** Layout *****/

        val widgetMain = widgetVBox {
            addHBox {
                addStretch()
                addWidget(buttonRestart)
                addWidget(buttonNewGame)
            }
            addSpacing(spacing)
            addHBox {
                addVBox {
                    addLayout(layoutGrid(game))
                    addSpacing(spacing)
                    addLayout(layoutCurrentWord(game))
                }
                addSpacing(spacing)
                addLayout(layoutWordList(game))
            }
        }


        /***** Window settings *****/

        windowTitle = "Squaredle"
        setCentralWidget(widgetMain)
        resize(500, 400)
        show()
    }


    /***** Keyboard shortcuts *****/

    // It would be nice to have Enter map to game.addWord(),
    // but that didn't work on the first attempt.

    override fun keyPressEvent(event: QKeyEvent) {
        when (event.key()) {
            Qt.Key.Key_Escape.value() -> game.clearWord()
            Qt.Key.Key_Backspace.value() -> game.backSpace()
            else -> super.keyPressEvent(event)
        }
    }

}
