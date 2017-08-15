package activities.days

import activities.Activity
import java.util.*

public data class Date(val day: Int, val month: Int, val year: Int) {
    override fun toString() = "$day.$month.$year"
}

/**
 * Activity that tracks day activities
 */
public class Day (
        public val date: Date,
        private var _timeActive: Long = 0,
        _keysClickedCount: Int = 0,
        _mouseClickedCount: Int = 0,
        _timeSpentInSec: Int = 0,
        _timeSpentAfkInSec: Int = 0,
        _timerStartsCount: Int = 0,
        _focusContextMap: Map<String, Long>? = null,
        _keysContextMap: Map<String, Int>? = null
)
    : Activity(_keysClickedCount, _mouseClickedCount,
        _timeSpentInSec, _timeSpentAfkInSec,
        _timerStartsCount, _focusContextMap,
        _keysContextMap) {

    val timeActive
        get() = _timeActive

    /**
     * Increase time active in one day
     * @param ms time in milliseconds
     */
    public fun increaseTimeActive(ms: Long) {
        _timeActive += ms
    }

    override fun equals(other: Any?) =
            when {
                (this === other) -> true
                (javaClass != other?.javaClass) -> false
                else -> date == (other as Day).date
            }

    override fun hashCode() = Objects.hashCode(date)

    override fun toString() = date.toString()
}