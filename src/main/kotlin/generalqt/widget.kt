package generalqt

import io.qt.core.Qt
import io.qt.widgets.QWidget

// Qt's setVisible method has the problem that it will make a window when
// calling setVisible(true), it will actually show a window, which is not desired.
fun QWidget.improvedSetVisible(visible: Boolean) {
    if (visible) {
        val parent = parentWidget()
        if (parent != null && parent.isVisible)
            show()
        else
            setAttribute(Qt.WidgetAttribute.WA_WState_Hidden, false)
    }
    else {
        hide()
    }
}
