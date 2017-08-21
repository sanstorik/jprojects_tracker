import activities.days.Days
import native_hooks.ActivityGlobalListener
import java.util.*
import kotlin.concurrent.timerTask

fun main(args: Array<String>) {
    ActivityGlobalListener.of(Days.currentDay).startTracking()
    Timer().schedule(timerTask { Days.saveChanges()}, 5000, 250)
}