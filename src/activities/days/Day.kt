package activities.days

import activities.Activity
import activities.days.hour.HourActivity
import java.util.*

public data class Date(val day: Int, val month: Int, val year: Int) {
    override fun toString() = "$day.$month.$year"
}

/**
 * Activity that tracks day activities
 */
public class Day (
        public val date: Date,
        _currentHourActivity: Set<HourActivity>? = null,
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

    private val _hourActivities: MutableSet<HourActivity> = HashSet()

    init {
        _currentHourActivity?.forEach {
            _hourActivities.add(it)
        }

        for (i in 1..24) {
            _hourActivities.add(HourActivity(hour = i))
        }
        //_currentHourActivity ?: HourActivity(Calendar.getInstance().get(Calendar.HOUR_OF_DAY))
    }

    override fun increaseTimeSpent(seconds: Int) {
        Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        super.increaseTimeSpent(seconds)
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