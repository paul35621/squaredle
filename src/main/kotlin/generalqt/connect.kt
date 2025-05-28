package generalqt

import io.qt.core.QMetaObject

/*

The connect extension method is here defined, because the built-in connect method
doesn't always work. (compile time error, has something to do with types)

The connectAndExecute extension method is often useful.

*/


fun QMetaObject.AbstractPrivateSignal0.connect (function: () -> Unit): QMetaObject.Connection =
    connect({ function() })

fun <A> QMetaObject.AbstractPrivateSignal1<A>.connect (function: (A) -> Unit): QMetaObject.Connection =
    connect({a: A -> function(a) })

fun <A, B> QMetaObject.AbstractPrivateSignal2<A, B>.connect (function: (A, B) -> Unit): QMetaObject.Connection =
    connect({a: A, b: B -> function(a, b) })

fun <A, B, C> QMetaObject.AbstractPrivateSignal3<A, B, C>.connect (function: (A, B, C) -> Unit): QMetaObject.Connection =
    connect({a: A, b: B, c: C -> function(a, b, c) })


fun QMetaObject.AbstractPrivateSignal0.connectAndExecute (function: () -> Unit): QMetaObject.Connection =
    connect(function).also { function() }

fun <A> QMetaObject.AbstractPrivateSignal1<A>.connectAndExecute (function: () -> Unit): QMetaObject.Connection =
    connect(function).also { function() }

fun <A, B> QMetaObject.AbstractPrivateSignal2<A, B>.connectAndExecute (function: () -> Unit): QMetaObject.Connection =
    connect(function).also { function() }

fun <A, B, C> QMetaObject.AbstractPrivateSignal3<A, B, C>.connectAndExecute (function: () -> Unit): QMetaObject.Connection =
    connect(function).also { function() }
