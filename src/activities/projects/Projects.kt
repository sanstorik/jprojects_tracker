package activities.projects

import database.ConnectionHolder
import database.ProjectConnectionJson
import java.util.*

/**
 * Main source of API between model and Database.
 * Has the information about all projects.
 *
 */
object Projects : ConnectionHolder<Project>("res/databases/projectsJson.json", ProjectConnectionJson()) {
    /**
     * Class with all projects, which controls
     * the input and output of data in application
     * connected with projects.
     */
    public class ProjectsInfo {
        private val _projects: MutableSet<Project> = HashSet()

        public fun get(name: String) = _projects.find { it.projectName == name }

        public fun sort(comparator: (one: Project, two: Project) -> Int)
                = _projects.toSortedSet(Comparator(comparator))

        /**
         * @return true if adding was successful,
         * false if it exists already
         */
        public fun add(project: Project) = _projects.add(project)

        /**
         * @return true if deleted,
         * false otherwise
         */
        public fun remove(project: Project) = _projects.remove(project)

        public fun removeAll() = _projects.clear()

        public fun getProjects() : Set<Project> = _projects


        public fun addEmpty(projectName: String) = _projects.add(Project(projectName))

        /**
         * @param newProjectName should not be empty neither blank
         */
        public fun rename(oldProjectName: String, newProjectName: String): Boolean {
            val project = get(oldProjectName)
            val isNewNameTaken = _projects.find { it.projectName == newProjectName }

            if (project == null || isNewNameTaken != null
                    || newProjectName.isBlank()
                    || newProjectName.isEmpty()) {
                return false
            }

            project.projectName = newProjectName
            return true
        }
    }

    private val _projectsInfo : ProjectsInfo = ProjectsInfo()
    init {
        initStartingConfiguration()
    }

    override fun receive(fileName: String) {
        val projSet = _connection.read(fileName)
        _projectsInfo.removeAll()
        projSet.forEach { _projectsInfo.add(it) }
    }

    /**
     * get holder of all current projects
     */
    public fun info() = _projectsInfo

    public fun saveChanges() = _connection.save(_projectsInfo.getProjects(), _defaultHolderName)
}