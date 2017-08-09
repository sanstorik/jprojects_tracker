package activities

import database.ProjectConnectionJson
import java.util.*

/**
 * Main source of API between model and Database.
 * Has the information about all projects.
 *
 */
object Projects : ConnectionHolder<Project> ("projectsJson.txt", ProjectConnectionJson()) {
    /**
     * Class with all projects, which controls
     * the input and output of data in application
     * connected with projects.
     */
    public class ProjectsInfo {
        private val _projects: HashSet<Project> = HashSet()

        public fun get(name: String) = _projects.find { it.projectName == name }

        public fun sort(comparator: (one: Project, two: Project) -> Int)
                = _projects.toSortedSet(Comparator(comparator))

        public fun add(project: Project) : Boolean {
            if (_projects.contains(project)) return false

            _projects.add(project)
            return true;
        }

        public fun getProjects() = Collections.unmodifiableSet(_projects)
    }

    private val _projectsInfo : ProjectsInfo = ProjectsInfo()
    init {
        _connection = ProjectConnectionJson()
    }

    override fun import(fileName: String) {
        val projSet = _connection.read(fileName)
        projSet.forEach { _projectsInfo.add(it) }
    }

    /**
     * get holder of all current projects
     */
    public fun info() = _projectsInfo

    private fun saveChanges() {
        _connection.save(_projectsInfo.getProjects(), _defaultHolderName)
    }
}