package generalqt

import io.qt.core.QMetaObject
import io.qt.core.QObject
import java.lang.ref.WeakReference

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


/********** connectWithWeakReference **********/

fun <O, A, B> QMetaObject.AbstractPrivateSignal2<A, B>.connectWithWeakReference (obj: O, function: O.(A, B) -> Unit): QMetaObject.Connection {
    val ref = WeakReference(obj)
    lateinit var connection: QMetaObject.Connection
    connection = connect { a, b ->
        val o = ref.get()
        if (o == null) disconnect(connection) else o.function(a, b)
    }
    return connection
}


/********** connectToQObject **********/

// Remove connection when the object is deleted.

fun <O : QObject, A, B> QMetaObject.AbstractPrivateSignal2<A, B>.connectToQObject(obj: O, function: O.(A, B) -> Unit): QMetaObject.Connection {
    val connection = connect { a, b ->
        obj.function(a, b)
    }
    obj.destroyed.connect {
        disconnect(connection)
    }
    return connection
}
