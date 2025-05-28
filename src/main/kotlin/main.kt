import io.qt.widgets.QApplication
import io.qt.widgets.QMessageBox

fun main(args: Array<String>) {
    QApplication.initialize(args)
    QMessageBox.information(null, "QtJambi", "Hello World!")
    QApplication.shutdown()
}
