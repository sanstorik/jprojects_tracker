import activities.projects.Project
import activities.projects.Projects

fun main(args: Array<String>) {
    Projects.info().add(Project("hello"))
    Projects.saveChanges()
}