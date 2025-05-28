import generalqt.connectAndExecute
import generalqt.improvedSetVisible
import generalqt.layoutVBox
import io.qt.widgets.QCheckBox
import io.qt.widgets.QLabel
import io.qt.widgets.QPlainTextEdit

fun layoutWordList(game: Game) = layoutVBox {

    /***** Widgets *****/

    val labelFound = QLabel()

    val textFoundWords = QPlainTextEdit()
    textFoundWords.readOnly = true

    val textAllWords = QPlainTextEdit()
    textAllWords.readOnly = true

    val checkboxAllWords = QCheckBox("Show all words")
    checkboxAllWords.clicked.connectAndExecute {
        textAllWords.improvedSetVisible(checkboxAllWords.checked)
    }


    /***** Update widgets *****/

    var lastAllWords: List<String>? = null

    game.updated.connectAndNow {
        labelFound.text = "Found words: (${game.wordsFound.size} / ${game.allWords.size})"

        textFoundWords.plainText = game.wordsFound.joinToString("\n")

        if (lastAllWords !== game.allWords) {  // !== check for performance
            textAllWords.plainText = game.allWords.joinToString("\n")
            lastAllWords = game.allWords
        }
    }


    /***** Layout *****/

    addWidget(labelFound)
    addWidget(textFoundWords)
    addWidget(checkboxAllWords)
    addWidget(textAllWords)
}
