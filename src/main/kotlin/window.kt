import generalqt.addHBox
import generalqt.addVBox
import generalqt.widgetVBox
import io.qt.core.Qt
import io.qt.gui.QKeyEvent
import io.qt.widgets.QMainWindow
import io.qt.widgets.QPushButton

const val spacing = 10

class Window : QMainWindow() {
    val game = Game(4, dictionary)

    init {
        val buttonNewGame = QPushButton("New game")
        buttonNewGame.clicked.connect(game::newGame)

        val widgetMain = widgetVBox {
            addHBox {
                addStretch()
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

        // Window settings
        windowTitle = "Squaredle"
        setCentralWidget(widgetMain)
        resize(500, 400)
        show()
    }

    override fun keyPressEvent(event: QKeyEvent) {
        when (event.key()) {
            Qt.Key.Key_Escape.value() -> game.clearWord()
            Qt.Key.Key_Backspace.value() -> game.backSpace()
            else -> super.keyPressEvent(event)
        }
    }
}
