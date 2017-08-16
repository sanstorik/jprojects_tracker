package activities.days

import database.ConnectionHolder
import database.DaysConnectionJson
import java.util.*
import kotlin.concurrent.timerTask

object Days: ConnectionHolder<Day>("res/databases/daysJson.json", DaysConnectionJson()) {
    private val _days: MutableSet<Day> = HashSet()
    public val currentDay by lazy { createCurrentDayIfAbsent() }

    init {
        initStartingConfiguration()
    }

    public fun sort(comparator: (one: Day, two: Day) -> Int) = _days.toSortedSet(Comparator(comparator))

    override fun import(fileName: String) {
        _days.addAll(_connection.read(fileName))
    }

    public fun saveChanges() = _connection.save(_days, _defaultHolderName)

    public fun getDays(): Set<Day> = _days

    /**
     * Get cached value from set, but if it is
     * absent, creates current day and returns it
     */
    private fun createCurrentDayIfAbsent(): Day {
        val calendar = Calendar.getInstance()
        val curDate = Date(calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR))

        val cachedDay = _days.firstOrNull { it.date == curDate }
        return if (cachedDay == null) {
            val day = Day(curDate)
            _days.add(day)
            day
        } else {
            cachedDay
        }
    }
}