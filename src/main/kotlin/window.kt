import generalqt.SwitchWidget
import generalqt.connect
import io.qt.widgets.QMainWindow
import io.qt.widgets.QPushButton

class Window : QMainWindow() {
    init {
        val gridContainer = SwitchWidget(GridView(randomGrid()), autoDispose = true)

        val buttonNewGrid = QPushButton("New grid")
        buttonNewGrid.clicked.connect {
            gridContainer.widget = GridView(randomGrid())
        }

        val widgetMain = widgetVBox {
            addWidget(gridContainer)
            addWidget(buttonNewGrid)
        }

        // Window settings
        windowTitle = "Squaredle"
        setCentralWidget(widgetMain)
        resize(500, 400)
        show()
    }
}
