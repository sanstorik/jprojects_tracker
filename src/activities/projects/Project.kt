package activities.projects

import activities.Activity
import java.util.*

/**
 * Activity that tracks projects
 */
public class Project (
        public val projectName : String,
        _dateOfCreation: Calendar? = null,
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

    var dateOfCreation = (_dateOfCreation ?: Calendar.getInstance())!!
        private set

    override fun equals(other: Any?) =
        when {
            (this === other) -> true
            (javaClass != other?.javaClass) -> false
            else -> projectName == (other as Project).projectName
        }

    override fun hashCode() = Objects.hashCode(projectName)

    override fun toString() = projectName
}