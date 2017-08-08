package activities

import java.util.*

/**
 * Activity that tracks projects
 */
public class Project (
        public val projectName : String,
        private val _dateOfCreation : Calendar? = null,
        private var _keysClickedCount : Int = 0,
        private var _mouseTravelled : Int = 0,
        private var _mouseClickedCount : Int = 0,
        private var _timeSpentInSec : Int = 0,
        private var _timeSpentAfkInSec : Int = 0,
        private var _timerStartsCount : Int = 0,
        private val _focusContextMap : Map<String, Int>? = null
) : Activity (_keysClickedCount, _mouseTravelled,
        _mouseClickedCount, _timeSpentInSec,
        _timeSpentAfkInSec, _timerStartsCount,
        _focusContextMap) {

    lateinit var dateOfCreation : Calendar
       private set

    init {
        if (_dateOfCreation != null)
            dateOfCreation = _dateOfCreation
    }

}