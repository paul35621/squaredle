import io.qt.widgets.QApplication
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    QApplication.initialize(args)
    Window()
    exitProcess(QApplication.exec())
}
