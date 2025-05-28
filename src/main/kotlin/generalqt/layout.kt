package generalqt

import io.qt.widgets.*

/*

Here some functions and extension methods for prettier syntax when
creating layouts using QHBoxLayout, QVBoxLayout and QGridLayout.

*/


inline fun QBoxLayout.addHBox(stretch: Int = 0, function: QHBoxLayout.() -> Unit) =
    layoutHBox(function).also { addLayout(it, stretch) }

inline fun QBoxLayout.addVBox(stretch: Int = 0, function: QVBoxLayout.() -> Unit) =
    layoutVBox(function).also { addLayout(it, stretch) }

inline fun QBoxLayout.addGrid(stretch: Int = 0, function: QGridLayout.() -> Unit) =
    layoutGrid(function).also { addLayout(it, stretch) }


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
