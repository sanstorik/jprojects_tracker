import activities.Projects

fun main(args: Array<String>) {
    val project = Projects.info().get("zzz")!!
    val calendar = project.dateOfCreation
}