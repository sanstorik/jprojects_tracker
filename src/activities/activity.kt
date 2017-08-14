package activities

/**
 * Base class for all activities which we
 * are going to track. Holds all basic
 * information about user actions.
 */
public abstract class Activity (
        _keysClickedCount: Int = 0,
        private var _mouseClickedCount: Int = 0,
        private var _timeSpentInSec: Int = 0,
        private var _timeSpentAfkInSec: Int = 0,
        private var _timerStartsCount: Int = 0,
        _focusContextMap: Map<String, Long>? = null,
        _keysContextMap: Map<String, Int>? = null
) {
    val focusContextAnalyzer: FocusContextAnalyzer = FocusContextAnalyzer(_focusContextMap)
    val keysContextAnalyser: KeyContextAnalyzer = KeyContextAnalyzer(_keysContextMap, _keysClickedCount)

    /**
     * Distance of mouse movements in pixels
     */
    var mouseClickedCount: Int
        private set(it) { _mouseClickedCount = it }
        get() = _mouseClickedCount
    var timeSpentInSec: Int
        private set(it) { _timeSpentInSec = it }
        get() = _timeSpentInSec

    /**
     * Time when used didn't perform any action
     * in the last 30 seconds.
     */
    var timeSpentAfkInSec: Int
        private set(it) { _timeSpentAfkInSec = it }
        get() = _timeSpentAfkInSec
    var timerStartsCount: Int
        private set(it) { _timerStartsCount = it}
        get() = _timeSpentAfkInSec


    public fun mouseClicked() {
        _mouseClickedCount++
    }

    public fun increaseTimeStarts() {
        _timerStartsCount++
    }

    public fun increaseTimeSpent(seconds: Int) {
        _timeSpentInSec += seconds
    }

    public fun increaseTimeSpentAfk(seconds: Int) {
        _timeSpentAfkInSec += seconds
    }
}