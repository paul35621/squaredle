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

interface Watchable <T> {
    val onChange: PSignal<T>
}

class WatchableValue<T>(initialValue: T): Watchable<T> {
    override val onChange = PSignal<T>()

    var value = initialValue
        private set (newValue) {
            if (field != newValue) {
                field = newValue
                onChange.emit(newValue)
            }
        }

    fun nowAndOnChange(slot: (T) -> Unit): Connection<T> {
        slot(value)
        return onChange.connect(slot)
    }

    fun <U> map(f: (T) -> U): WatchableValue<U> {
        val v = WatchableValue(f(value))
        onChange.connect { newValue -> v.value = f(newValue) }
        return v
    }

    // Used for delegate, do not call directly
    operator fun getValue(thisRef: Any?, property: KProperty<*>) = value

    // Used for delegate, do not call directly
    operator fun setValue(thisRef: Any?, property: KProperty<*>, newValue: T) {
        value = newValue
    }
}

open class Variable<T> (initialValue: T) : Watchable<T> {
    open var value: T = initialValue
        set(newValue) {
            if (field != newValue) {
                beforeChange.emit(field)
                field = newValue
                onChange.emit(newValue)
            }
        }

    val beforeChange = PSignal<T>()
    override val onChange = PSignal<T>()

    fun nowAndOnChange(slot: (T) -> Unit): Connection<T> =
        onChange.connect(slot).apply { function(value) }

    fun <O : QObject> nowAndOnChange(qObject: O, slot: O.(T) -> Unit): Connection<T> =
        onChange.connectToQObject(qObject, slot).apply { function(value) }

    fun <U> map(f: (T) -> U): Variable<U> {
        val v = Variable(f(value))
        onChange.connect { _ -> v.value = f(value) }
        return v
    }
}

/**
 * An instance of this object can either refer to an other variable,
 * or have a value on its own.
 * - When setting a value, the reference variable is updated,
 *   so the reference is two-way.
 * - Set source to null to lose the reference
 * - Setting the source copies the value from the source variable.
 */
class VariableReference<T> (initialValue: T) : Variable<T>(initialValue) {
    private var connection: Connection<T>? = null

    var source: Variable<T>? = null
        set(newSource) {
            if (newSource != field) {
                connection?.disconnect()
                field = newSource
                connection = newSource?.nowAndOnChange {
                    super.value = it
                }
            }
        }

    constructor(source: Variable<T>) : this(source.value) {
        this.source = source
    }

    override var value: T
        get() = super.value
        set(newValue) {
            super.value = newValue
            source?.value = newValue
        }
}

class WatchableList<T : Watchable<*>> (private val items: MutableList<T> = mutableListOf()) : Iterable<T>,
    Watchable<Unit> {
    override val onChange = PSignal<Unit>()  // Item added, removed, or one of the items has changed
    val onAdd = PSignal<Pair<Int, T>>()
    val onRemove = PSignal<Pair<Int, T>>()

    private val onChangeConnections = mutableListOf<Connection<*>>()

    val size: Int get() =
        items.size

    init {
        for (i in items) {
            onChangeConnections += i.onChange.connect { _ -> onChange.emit(Unit) }
        }
        onAdd.connect(onChange)
        onRemove.connect(onChange)
    }

    operator fun get(index: Int) =
        items[index]

    fun insert(index: Int, item: T) {
        items.add(index, item)
        onChangeConnections.add(index, item.onChange.connect { _ -> onChange.emit(Unit) })
        onAdd.emit(Pair(index, item))
    }

    operator fun plusAssign(item: T) {
        insert(items.size, item)
    }

    fun remove(index: Int) {
        val item = items.removeAt(index)
        onChangeConnections.removeAt(index).disconnect()
        onRemove.emit(Pair(index, item))
    }

    override fun iterator(): Iterator<T> =
        items.iterator()
}