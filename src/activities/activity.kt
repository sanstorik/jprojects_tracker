package activities

import java.util.*
import kotlin.collections.HashMap

/**
 * Base class for all activities which we
 * are going to track. Holds all basic
 * information about user actions.
 */
public abstract class Activity (
        private var _keysClickedCount : Int = 0,
        private var _mouseTravelled : Int = 0,
        private var _mouseClickedCount : Int = 0,
        private var _timeSpentInSec : Int = 0,
        private var _timeSpentAfkInSec : Int = 0,
        private var _timerStartsCount : Int = 0,
        _focusContextMap : Map<String, Int>? = null
) {
    lateinit var focusContextAnalyzer : FocusContextAnalyzer
        private set

    init {
        focusContextAnalyzer = FocusContextAnalyzer(_focusContextMap)
    }

    var keysClickedCount : Int
        private set(it) { _keysClickedCount = it }
        get() = _keysClickedCount

    /**
     * Distance of mouse movements in pixels
     */
    var mouseTravelled : Int
        private set(it) { _mouseTravelled = it }
        get() = _mouseTravelled
    var mouseClickedCount : Int
        private set(it) { _mouseClickedCount = it }
        get() = _mouseClickedCount
    var timeSpentInSec : Int
        private set(it) { _timeSpentInSec = it }
        get() = _timeSpentInSec

    /**
     * Time when used didn't perform any action
     * in the last 30 seconds.
     */
    var timeSpentAfkInSec : Int
        private set(it) { _timeSpentAfkInSec = it }
        get() = _timeSpentAfkInSec
    var timerStartsCount : Int
        private set(it) { _timerStartsCount = it}
        get() = _timeSpentAfkInSec

    public fun keyClicked(key : Char) = _keysClickedCount++

    public fun mouseClicked() = _mouseClickedCount++

    public fun increaseTimeStarts() = _timerStartsCount++

    public fun increaseMouseTravelled(distance : Int) {
        _mouseTravelled += distance
    }

    public fun increaseTimeSpent(seconds : Int) {
        _timeSpentInSec += seconds
    }

    public fun increaseTimeSpentAfk(seconds : Int) {
        _timeSpentAfkInSec += seconds
    }
}


/**
 * Class for keeping track of visited and time spent
 * on certain contexts. In charge of their analyses.
 */
public class FocusContextAnalyzer (_map : Map<String, Int>?) {
    private val _visitedContexts : HashMap<String, Int> = HashMap()
    init {
        if (_map != null) _visitedContexts.putAll(_map)
    }


    /**
     * Adding time to a particular application/window etc.
     * @param seconds time in seconds
     */
    fun addTimeSpentOnContext(contextName : String, seconds : Int) {
        if (_visitedContexts.containsKey(contextName)) {
            val value = _visitedContexts.getValue(contextName)
            _visitedContexts.put(contextName, value + seconds);
        } else {
            _visitedContexts.put(contextName, seconds);
        }
    }

    fun getVisitedContexts() = Collections.unmodifiableMap(_visitedContexts)

    fun sort(comparable: Comparator<String>) = _visitedContexts.toSortedMap(comparable)

}