import io.qt.widgets.QApplication
import kotlin.jvm.javaClass
import kotlin.system.exitProcess

val wordlist =
    object{}.javaClass.getResource("wordlist.txt")!!.readText().lines()
        .map { it.trim().uppercase() }
        .filter { word -> word.length >= 4 && word.all { char -> char in defaultAlphabet }}
        .toSet()

fun main(args: Array<String>) {
    QApplication.initialize(args)
    Window()
    exitProcess(QApplication.exec())
}
