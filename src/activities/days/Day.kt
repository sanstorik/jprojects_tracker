package activities.days

import activities.Activity
import activities.days.hour.HourActivity
import java.util.*

public data class Date(val day: Int, val month: Int, val year: Int) {
    override fun toString() = "$day.$month.$year"
}

/**
 * Class for tracking day activities
 */
public class Day (
        public val date: Date,
        _hourActivities: Set<HourActivity>? = null,
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

    private val hourActivities: MutableSet<HourActivity> = HashSet()
    private var _currentHour: Int? = null
    private lateinit var _currentHourActivity: HourActivity

    init {
        _hourActivities?.forEach {
            this.hourActivities.add(it)
        }

        if (_hourActivities == null) {
            for (i in 1..24) {
                this.hourActivities.add(HourActivity(hour = i))
            }
        }

        getCurrentHourActivity()
    }

    /**
     * @return hour activity that synchronises with current real hour
     */
    public fun getCurrentHourActivity(): HourActivity {
        if (_currentHour == null || _currentHour != Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
            _currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            _currentHourActivity = hourActivities.first { it.hour == _currentHour }
        }

        return _currentHourActivity
    }

    public fun getHourActivities(): Set<HourActivity> = hourActivities

    override fun onKeyPressed(key: String) {
        _currentHourActivity.onKeyPressed(key)
        super.onKeyPressed(key)
    }

    override fun onContextViewed(view: String, ms: Long) {
        _currentHourActivity.onContextViewed(view, ms)
        super.onContextViewed(view, ms)
    }

    override fun increaseTimeSpent(seconds: Int) {
        getCurrentHourActivity().increaseTimeSpent(seconds)
        super.increaseTimeSpent(seconds)
    }

    override fun increaseTimeStarts() {
        getCurrentHourActivity().increaseTimeStarts()
        super.increaseTimeStarts()
    }

    override fun onMouseClicked() {
        getCurrentHourActivity().onMouseClicked()
        super.onMouseClicked()
    }

    override fun increaseTimeSpentAfk(seconds: Int) {
        getCurrentHourActivity().increaseTimeSpentAfk(seconds)
        super.increaseTimeSpentAfk(seconds)
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