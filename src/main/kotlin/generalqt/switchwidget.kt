package generalqt

import io.qt.widgets.QStackedWidget
import io.qt.widgets.QWidget

open class SwitchWidget<T : QWidget>(initialWidget: T? = null, val autoHide: Boolean = false, val autoDispose: Boolean = false) : QStackedWidget() {

    var widget: T? = null
        set(value) {
            if (field != null) {
                removeWidget(field)
                if (autoDispose) field!!.disposeLater()
            }
            if (value != null) addWidget(value)
            field = value
            if (autoHide) improvedSetVisible(value != null)
        }

    init {
        widget = initialWidget
    }
}
