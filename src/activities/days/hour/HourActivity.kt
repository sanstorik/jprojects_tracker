package activities.days.hour

import activities.Activity
import java.util.*


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
        _keysContextMap) {

    override fun equals(other: Any?) =
            when {
                this === other -> true
                this.javaClass != other?.javaClass -> false
                else -> this.hour == (other as HourActivity).hour
            }

    override fun hashCode() = Objects.hashCode(hour)
}