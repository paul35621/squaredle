import generalqt.layoutHBox
import io.qt.widgets.QLayout
import io.qt.widgets.QLineEdit
import io.qt.widgets.QPushButton

fun layoutCurrentWord(game: Game): QLayout {

    /***** Widgets *****/

    val line = QLineEdit()
    line.readOnly = true

    val buttonAdd = QPushButton("Add")
    buttonAdd.clicked.connect(game::addWord)

    val buttonBackspace = QPushButton("Backspace")
    buttonBackspace.clicked.connect(game::backSpace)

    val buttonClear = QPushButton("Clear")
    buttonClear.clicked.connect(game::clearWord)


    /***** Update widgets *****/

    game.onChangeAndNow {
        line.text = game.currentWord
        buttonAdd.enabled = game.canAddWord
        buttonBackspace.enabled = game.currentWordNotEmpty
        buttonClear.enabled = game.currentWordNotEmpty
    }


    /***** Layout *****/

    return layoutHBox {
        addWidget(line)
        addWidget(buttonAdd)
        addWidget(buttonBackspace)
        addWidget(buttonClear)
    }
}
