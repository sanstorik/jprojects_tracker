import activities.projects.Project
import activities.projects.Projects
import native_hooks.ActivityGlobalListener
import java.util.*

fun main(args: Array<String>) {
    val project = Projects.info().add(Project("zzz"))
    val project1 = Projects.info().add(Project("zalupa"))
   /* val activity = ActivityGlobalListener.init(Projects.info().get("zalupa")!!);
    activity.startTracking()

    Timer().schedule(object : TimerTask() {
        override fun run() {
            Projects.saveChanges()
        }
    }, 10)

    Projects.saveChanges();*/
}