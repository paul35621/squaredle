import io.qt.core.QSize
import io.qt.core.Qt
import io.qt.core.Qt.Orientation
import io.qt.widgets.*

/******************** QBoxLayout / QGridLayout / QSplitter ********************/

inline fun QBoxLayout.addHBox(stretch: Int = 0, function: QHBoxLayout.() -> Unit) =
    QHBoxLayout().apply(function).also { addLayout(it, stretch) }

inline fun QBoxLayout.addVBox(stretch: Int = 0, function: QVBoxLayout.() -> Unit) =
    QVBoxLayout().apply(function).also { addLayout(it, stretch) }

inline fun QBoxLayout.addGrid(stretch: Int = 0, function: QGridLayout.() -> Unit) =
    QGridLayout().apply(function).also { addLayout(it, stretch) }

inline fun QBoxLayout.addHSplitter(stretch: Int = 0, function: QSplitter.() -> Unit) =
    QSplitter(Orientation.Horizontal).apply(function).also { addWidget(it, stretch) }

inline fun QBoxLayout.addVSplitter(stretch: Int = 0, function: QSplitter.() -> Unit) =
    QSplitter(Orientation.Vertical).apply(function).also { addWidget(it, stretch) }

fun QVBoxLayout.addSeparator() {
    val frame = QFrame()
    frame.frameShape = QFrame.Shape.HLine
    frame.frameShadow = QFrame.Shadow.Sunken
    addSpacing(10)
    addWidget(frame)
    addSpacing(10)
}

fun QBoxLayout.addLabel(text: String, stretch: Int = 0, wordWrap: Boolean = false, selectable: Boolean = false): QLabel {
    val label = QLabel(text)
    if (wordWrap) label.wordWrap = true
    if (selectable) label.setTextInteractionFlags(Qt.TextInteractionFlag.TextSelectableByMouse)
    addWidget(label, stretch)
    return label
}

fun QGridLayout.addRow(label: String?, widget1: QWidget, widget2: QWidget? = null) {
    val row = rowCount()
    if (label != null) addWidget(QLabel(label), row, 0, Qt.AlignmentFlag.AlignTop)
    addWidget(widget1, row, 1)
    if (widget2 != null) addWidget(widget2, row, 2)
}

fun QGridLayout.addRow(label: String?, layout: QLayout) {
    val row = rowCount()
    if (label != null) addWidget(QLabel(label), row, 0, Qt.AlignmentFlag.AlignTop)
    addLayout(layout, row, 1)
}

inline fun QSplitter.addHBox(function: QHBoxLayout.() -> Unit) =
    widgetHBox(function).also(::addWidget)

inline fun QSplitter.addVBox(function: QVBoxLayout.() -> Unit) =
    widgetVBox(function).also(::addWidget)

fun QSplitter.addLayout(layout: QLayout) =
    addWidget(QWidget().apply { setLayout(layout) })

inline fun QSplitter.addHSplitter(function: QSplitter.() -> Unit) =
    QSplitter(Orientation.Horizontal).apply(function).also(::addWidget)

inline fun QSplitter.addVSplitter(function: QSplitter.() -> Unit) =
    QSplitter(Orientation.Vertical).apply(function).also(::addWidget)

inline fun widgetHBox(function: QHBoxLayout.() -> Unit): QWidget =
    QWidget().also { QHBoxLayout(it).function() }

inline fun widgetVBox(function: QVBoxLayout.() -> Unit): QWidget =
    QWidget().also { QVBoxLayout(it).function() }

inline fun widgetGrid(function: QGridLayout.() -> Unit): QWidget =
    QWidget().also { QGridLayout(it).function() }

inline fun layoutHBox(function: QHBoxLayout.() -> Unit) =
    QHBoxLayout().apply(function)

inline fun layoutVBox(function: QVBoxLayout.() -> Unit) =
    QVBoxLayout().apply(function)

inline fun layoutGrid(function: QGridLayout.() -> Unit) =
    QGridLayout().apply(function)

inline fun QWidget.setLayoutHBox(function: QHBoxLayout.() -> Unit) =
    QHBoxLayout(this).apply(function)

inline fun QWidget.setLayoutVBox(function: QVBoxLayout.() -> Unit) =
    QVBoxLayout(this).apply(function)

inline fun QWidget.setLayoutGrid(function: QGridLayout.() -> Unit) =
    QGridLayout(this).apply(function)


/******************** Big buttons ********************/

enum class ButtonSize(val fixedHeight: Int, val iconWidth: Int, val fontSize: Int, val space: Boolean) {
    MEDIUM(40, 24, 16, true), BIG(80, 32, 18, true), BIGGEST(100, 48, 20, true);

    val iconSize = QSize(iconWidth, iconWidth)
    val styleSheet = "font-size: ${fontSize}px;"
}

fun QPushButton.setSize(size: ButtonSize) {
    setFixedHeight(size.fixedHeight)
    iconSize = size.iconSize
    styleSheet = size.styleSheet
    if (size.space) text = " $text"
}


/******************** Other ********************/

operator fun QSize.get(orientation: Orientation): Int =
    when(orientation) {
        Orientation.Horizontal -> width()
        Orientation.Vertical -> height()
    }

open class Panel(val name: String, val bold: Boolean = false) : QGroupBox(name) {
    init {
        setAlignment(Qt.AlignmentFlag.AlignHCenter.value())
        isFlat = true
        if (bold) styleSheet = "QGroupBox {font-weight: bold;}"
    }
}
