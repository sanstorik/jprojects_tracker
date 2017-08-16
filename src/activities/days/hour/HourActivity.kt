package activities.days.hour

import activities.Activity
import native_hooks.NotListenable
import java.util.*


/**
 * Activity that tracks data
 * for only one hour
 */
public class HourActivity (
        public val hour: Int,
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
        _keysContextMap), NotListenable {

    override fun onKeyPressed(key: String) {
        keysContextAnalyser.increaseKeysPressed(key)
    }

    override fun onContextViewed(view: String, ms: Long) {
        focusContextAnalyzer.addTimeSpentOnContext(view, ms)
    }

    override fun equals(other: Any?) =
            when {
                this === other -> true
                this.javaClass != other?.javaClass -> false
                else -> this.hour == (other as HourActivity).hour
            }

    override fun hashCode() = Objects.hashCode(hour)

    override fun toString() = hour.toString()
}