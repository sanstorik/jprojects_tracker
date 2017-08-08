package database

import activities.Project
import java.util.*

public class ProjectConnectionJson : DatabaseConnection<Project> {
    override fun save(obj: Project, holderName: String): Boolean {
        return true
    }

    override fun read(holderName: String): Project {
        return Project(projectName = "zd", _dateOfCreation = Calendar.getInstance())
    }
}