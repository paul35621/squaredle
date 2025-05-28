import io.qt.widgets.QApplication
import kotlin.system.exitProcess

val dictionary = loadDictionary()

fun main(args: Array<String>) {
    QApplication.initialize(args)
    Window()
    exitProcess(QApplication.exec())
}
