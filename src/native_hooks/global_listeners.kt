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
import kotlin.concurrent.timerTask

public class ActivityGlobalListener private constructor(private val _activity: Activity) {
    private val keyListenerInitializer: ListenerInitializer = ActivityKeyListener(_activity)
    private val mouseListenerInitializer: ListenerInitializer = ActivityMouseListener(_activity)
    private val focusListenerInitializer: ListenerInitializer = ActivityFocusListener(_activity)
    private var isTracking = false

    /**
     * Stops tracking
     * @throws InvalidStateException if already tracking
     */
    @Throws(InvalidStateException::class)
    public fun startTracking() {
        if (isTracking)
            throw InvalidStateException("$_activity is already being tracked")

        isTracking = true
        keyListenerInitializer.init()
        mouseListenerInitializer.init()
        focusListenerInitializer.init()
    }

    /**
     * Stops tracking
     * @throws InvalidStateException if not tracking yet
     */
    @Throws(InvalidStateException::class)
    public fun stopTracking() {
        if (!isTracking)
            throw InvalidStateException("$_activity is not being tracked yet")

        isTracking = false;
        keyListenerInitializer.disable()
        mouseListenerInitializer.disable()
        focusListenerInitializer.disable()
    }


    companion object Factory {

        @JvmStatic
        private val openedListeners : MutableSet<ActivityGlobalListener> = HashSet<ActivityGlobalListener>()
        init {
            try {
                Logger.getLogger(GlobalScreen::class.java.`package`.name).level = Level.OFF
                GlobalScreen.registerNativeHook()
            } catch (e : NativeHookException) {
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
        public fun init(activity : Activity) : ActivityGlobalListener {
            if (!GlobalScreen.isNativeHookRegistered())
                throw InvalidStateException("Listener cannot be applied. Register it first.")

            val openedActivity = openedListeners.firstOrNull { it._activity == activity }

            return if (openedActivity != null) {
                openedActivity
            } else {
                val listener = ActivityGlobalListener(activity)
                openedListeners.add(listener)
                listener
            }
        }
    }
}

internal class ActivityKeyListener (private val _activity : Activity)
    : NativeKeyListener, ListenerInitializer {

    override fun nativeKeyTyped(p0: NativeKeyEvent?) {}

    override fun nativeKeyPressed(p0: NativeKeyEvent?) =
        _activity.keysContextAnalyser.increaseKeysPressed(NativeKeyEvent.getKeyText(p0?.keyCode!!))

    override fun nativeKeyReleased(p0: NativeKeyEvent?) {}

    override fun init() {
        GlobalScreen.addNativeKeyListener(this)
    }

    override fun disable() {
        GlobalScreen.removeNativeKeyListener(this)
    }
}

internal class ActivityMouseListener (private val _activity: Activity)
    : NativeMouseListener, ListenerInitializer {

    override fun nativeMousePressed(p0: NativeMouseEvent?)  = _activity.mouseClicked()

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