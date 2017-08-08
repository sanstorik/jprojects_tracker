package database

import activities.Project
import java.util.*

/**
 * Class that represents connection between model of projects
 * and JSON file, that holds data for this model.
 */
public class ProjectConnectionJson : DatabaseConnection<Project> {
    override fun save(obj: Set<Project>, holderName: String): Boolean {
        return true;
    }

    override fun read(holderName: String): Set<Project> {
        return setOf()
    }
}
