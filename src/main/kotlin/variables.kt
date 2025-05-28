import io.qt.core.QObject
import java.lang.Exception
import kotlin.reflect.KProperty

// Throw this while handling a signal to disconnect it
class DisconnectException : Exception()

class Connection<T> (val signal: PSignal<T>, val function: (T) -> Unit) {
    fun disconnect() {
        signal.removeConnection(this)
    }
}

class PSignal<T> {
    private val connections = mutableListOf<Connection<T>>()

    fun connect(slot: (T) -> Unit): Connection<T> =
        Connection(this, slot).also(connections::add)

    fun connect(slot: () -> Unit): Connection<T> =
        connect { _: T -> slot() }

    fun connect(signal: PSignal<Unit>): Connection<T> =
        connect { _ -> signal.emit(Unit) }

    fun <O : QObject> connectToQObject(qObject: O, slot: O.(T) -> Unit): Connection<T> {
        val c = Connection(this) { qObject.slot(it) }
        connections.add(c)
        qObject.destroyed.connect(c::disconnect)
        return c
    }

    fun connectAndNow(slot: () -> Unit): Connection<T> {
        slot()
        return connect(slot)
    }

    fun emit(value: T) {
        with (connections.listIterator()) {
            while (hasNext()) {
                try {
                    next().function(value)
                }
                catch (e: DisconnectException) {
                    remove()
                }
            }
        }
    }

    // Do not call this while this connection is being executed,
    // throw DisconnectException instead.
    fun removeConnection(connection: Connection<T>) {
        connections.remove(connection)
    }
}
