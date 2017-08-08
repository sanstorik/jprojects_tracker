import activities.Project
import activities.Projects

fun main(args: Array<String>) {
    Projects.info().add(Project("zzz"))
    println(Projects.info().get("zzz")?.mouseClickedCount)
}