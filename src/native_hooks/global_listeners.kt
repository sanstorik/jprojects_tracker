package native_hooks

import activities.Activity
import org.jnativehook.GlobalScreen
import org.jnativehook.NativeHookException
import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.keyboard.NativeKeyListener
import org.jnativehook.mouse.NativeMouseEvent
import org.jnativehook.mouse.NativeMouseListener
import sun.plugin.dom.exception.InvalidStateException
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.collections.ArrayList

/**
 * Holder for all _listeners, that connects with activity,
 * use startActivity() to begin tracking and stopTracking()
 * to disable tracking. There exists only one copy for
 * single activity.
 *
 * If you want to create listener for class Day, you should check
 * if day passed, so you can create it for new Day, deleting
 * previous one.
 */
public class ActivityGlobalListener private constructor(private val _activity: Activity) {
    private val _listeners: MutableList<ListenerInitializer> = ArrayList()
    private var _isTracking = false

    init {
        if (_activity is NotListenable)
            throw IllegalArgumentException("$_activity couln't be listened")

        _listeners.add(ActivityKeyListener(_activity))
        _listeners.add(ActivityMouseListener(_activity))
        _listeners.add(ActivityFocusListener(_activity))
    }

    /**
     * Starts tracking
     * @throws InvalidStateException if already tracking
     */
    @Throws(InvalidStateException::class)
    public fun startTracking() {
        if (_isTracking)
            throw InvalidStateException("$_activity is already being tracked")

        _isTracking = true
        _listeners.forEach { it.init() }
    }

    /**
     * Stops tracking
     * @throws InvalidStateException if not tracking yet
     */
    @Throws(InvalidStateException::class)
    public fun stopTracking() {
        if (!_isTracking)
            throw InvalidStateException("$_activity is not being tracked yet")

        _isTracking = false
        _listeners.forEach { it.disable() }
    }

    /**
     * Stops trackings and clears it
     * from memory, so it can be collected.
     *
     * Use this is you won't need the same
     * listener for activity in the same
     * session.
     */
    public fun deleteAndClear() {
        if (_isTracking)
            stopTracking()

        _openedListeners.remove(this)
    }

    companion object Factory {

        @JvmStatic
        private val _openedListeners: MutableSet<ActivityGlobalListener> = HashSet()

        init {
            try {
                Logger.getLogger(GlobalScreen::class.java.`package`.name).level = Level.OFF
                GlobalScreen.registerNativeHook()
            } catch (e: NativeHookException) {
                throw InvalidStateException("Couldn't register native hook  - $e")
            }
        }

        /**
         * Creates new tracking connection on specific activity,
         * including key listener, mouse listener and other
         * tracking facilities.
         * @param activity activity to be tracked
         * @throws InvalidStateException if listener cannot be applied
         */
        @JvmStatic
        public fun of(activity: Activity): ActivityGlobalListener {
            if (!GlobalScreen.isNativeHookRegistered())
                throw InvalidStateException("Listener cannot be applied. Register it first.")

            val openedActivity = _openedListeners.firstOrNull { it._activity == activity }

            return if (openedActivity != null) {
                openedActivity
            } else {
                val listener = ActivityGlobalListener(activity)
                _openedListeners.add(listener)
                listener
            }
        }
    }
}

internal class ActivityKeyListener(private val _activity: Activity)
    : NativeKeyListener, ListenerInitializer {

    override fun nativeKeyTyped(p0: NativeKeyEvent?) {}

    override fun nativeKeyPressed(p0: NativeKeyEvent?) =
            _activity.onKeyPressed(NativeKeyEvent.getKeyText(p0?.keyCode!!))

    override fun nativeKeyReleased(p0: NativeKeyEvent?) {}

    override fun init() {
        GlobalScreen.addNativeKeyListener(this)
    }

    override fun disable() {
        GlobalScreen.removeNativeKeyListener(this)
    }
}

internal class ActivityMouseListener(private val _activity: Activity)
    : NativeMouseListener, ListenerInitializer {

    override fun nativeMousePressed(p0: NativeMouseEvent?) = _activity.onMouseClicked()

    override fun nativeMouseClicked(p0: NativeMouseEvent?) {}
    override fun nativeMouseReleased(p0: NativeMouseEvent?) {}

    override fun init() {
        GlobalScreen.addNativeMouseListener(this)
    }

    override fun disable() {
        GlobalScreen.removeNativeMouseListener(this)
    }
}

/**
 * API for opening and closing listeners
 */
internal interface ListenerInitializer {
    fun init()
    fun disable()
}

interface NotListenable { /*empty*/ }