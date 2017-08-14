package activities

import java.util.*

/**
 * Class for keeping track of visited contexts and time spent
 * on them. In charge of their analyses.
 */
public class FocusContextAnalyzer (_map: Map<String, Long>? = null) {
    private val _analyser : ContextAnalyzer<String, Long> = ContextAnalyzer(_map)

    /**
     * Adding time to a particular application/window etc.
     * @param seconds time in miliseconds
     */
    fun addTimeSpentOnContext(contextName: String, ms: Long) {
        _analyser.addValue(contextName, ms)
    }

    fun getVisitedContexts() = _analyser.getContexts()

    fun sort(comparator: (one: String, two: String) -> Int) = _analyser.sort(comparator)
}

/**
 * Class for keeping track of pressed keys
 * on certain activity. In charge of their analyses.
 */
public class KeyContextAnalyzer(_map: Map<String, Int>? = null, private var _keysClicked : Int) {
    private val _analyser : ContextAnalyzer<String, Int> = ContextAnalyzer(_map)
    var keysClicked : Int
        private set(it) { _keysClicked = it }
        get() = _keysClicked

    /**
     * Increase keys pressed on specific activity by one
     * @param key string representation of key
     */
    fun increaseKeysPressed(key: String) {
        _analyser.addValue(key, 1)
        _keysClicked++
    }

    fun getVisitedContexts() = _analyser.getContexts()

    fun sort(comparator: (one: String, two: String) -> Int) = _analyser.sort(comparator)
}


private class ContextAnalyzer <K, V> (_map: Map<K, V>?) where V : Number {
    private val _contexts : MutableMap<K, V> = HashMap()
    init {
        if (_map != null) _contexts.putAll(_map)
    }

    fun addValue(key: K, value: V) {
        if (_contexts.containsKey(key)) {
            val existentValue = _contexts.getValue(key)
            _contexts.put(key, (existentValue.toDouble() + value.toDouble()) as V);
        } else {
            _contexts.put(key, value);
        }
    }

    fun getContexts() : Map<K,V> = _contexts

    fun sort(comparator: (one : K, two : K) -> Int ) = _contexts.toSortedMap(Comparator(comparator))
}
