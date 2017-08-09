package activities

import java.util.*

/**
 * Activity that tracks projects
 */
public class Project (
        public val projectName : String,
        _dateOfCreation : Calendar? = null,
        private var _keysClickedCount : Int = 0,
        private var _mouseTravelled : Int = 0,
        private var _mouseClickedCount : Int = 0,
        private var _timeSpentInSec : Int = 0,
        private var _timeSpentAfkInSec : Int = 0,
        private var _timerStartsCount : Int = 0,
        _focusContextMap : Map<String, Int>? = null
) : Activity (_keysClickedCount, _mouseTravelled,
        _mouseClickedCount, _timeSpentInSec,
        _timeSpentAfkInSec, _timerStartsCount,
        _focusContextMap) {
    lateinit var dateOfCreation : Calendar
        private set
    init {
        dateOfCreation =
                if(_dateOfCreation != null) _dateOfCreation
                else Calendar.getInstance()
    }

    override fun equals(other: Any?) =
        when {
            (this === other) -> true
            (javaClass != other?.javaClass) -> false
            else -> projectName == (other as Project).projectName
        }

    override fun hashCode() = Objects.hashCode(projectName)

    override fun toString(): String = projectName
}