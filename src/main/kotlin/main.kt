import io.qt.widgets.QApplication
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    QApplication.initialize(args)

    val dictionary = loadDictionary(
        lines = object{}.javaClass.getResource("wordlist.txt")!!.readText(),
        alphabet = ('A'..'Z').toList(),
        minimumWordLength = 4
    )

    val game = Game(gridSize = 4, dictionary)
    Window(game)

    exitProcess(QApplication.exec())
}
