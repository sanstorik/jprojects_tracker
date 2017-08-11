package database

import activities.Activity
import activities.FocusContextAnalyzer
import activities.KeysContextAnalyzer
import activities.projects.Project
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.ietf.jgss.GSSName
import java.io.*
import java.util.*

/**
 * Class that represents connection between model of projects
 * and JSON file, that holds data for this model.
 */
public class ProjectConnectionJson : DatabaseConnection<Project> {

    override fun save(projects: Set<Project>, holderName: String) {
        var file = File(holderName)
        if (!file.exists() || file.isDirectory()) file.createNewFile()

        BufferedWriter(OutputStreamWriter(FileOutputStream(file))).use {
            it.write(getProjectSetJson(projects))
        }
    }

    override fun read(holderName: String): Set<Project> {
        var file = File(holderName)
        if (!file.exists() || file.isDirectory) {
            throw NoSuchFileException(file)
        }

        return readProjectsSet(
                JsonParser().parse(BufferedReader(InputStreamReader(FileInputStream(file))))
        )
    }

    @SuppressWarnings("uncheked")
    private fun readProjectsSet(json: JsonElement): Set<Project> {
        val gson = Gson()
        val set = HashSet<Project>()

        val rootObject = json.asJsonObject
        rootObject.entrySet().forEach {
            val project : JsonObject = it.value.asJsonObject
            set.add(Project(projectName = it.key,
                    _mouseClickedCount = project.get("mouseClickedCount").asInt,
                    _mouseTravelled = project.get("mouseTravelled").asInt,
                    _timeSpentAfkInSec = project.get("timeSpentAfkInSec").asInt,
                    _timeSpentInSec = project.get("timeSpentInSec").asInt,
                    _timerStartsCount = project.get("timerStartsCount").asInt,
                    _focusContextMap = gson.fromJson("32432", Map::class.java) as Map<String,Long>)
                    //TODO:_keysClickedCount =
            )
        }

        return set
    }

    private fun getProjectSetJson(projects: Set<Project>): String {
        val rootObj = JsonObject()

        for (obj in projects) {
            rootObj.add(obj.projectName, getProjectJson(obj))
        }

        return Gson().toJson(rootObj)
    }

    private fun getProjectJson(project: Project): JsonObject {
        val projectValues = JsonObject()

        addActivityJsonProperties(projectValues, project)

        val calendarDate = Gson().toJson(project.dateOfCreation)
        projectValues.addProperty("dateOfCreation", calendarDate)

        return projectValues
    }
}

private fun addActivityJsonProperties(jsonObject: JsonObject, activity: Activity) {
    val gson = Gson()
    jsonObject.addProperty("mouseClickedCount", activity.mouseClickedCount)
    jsonObject.addProperty("mouseTravelled", activity.mouseTravelled)
    jsonObject.addProperty("timeSpentAfkInSec", activity.timeSpentAfkInSec)
    jsonObject.addProperty("timeSpentInSec", activity.timeSpentInSec)
    jsonObject.addProperty("timerStartsCount", activity.timerStartsCount)

    jsonObject.add("focusContext", focusAnalyzerJsonObject(activity.focusContextAnalyzer))
    jsonObject.add("keyContext", keyAnalyzerJsonObject(activity.keysContextAnalyser))
}

private fun focusAnalyzerJsonObject(focus: FocusContextAnalyzer): JsonObject {
    val gson = Gson()

    val focusContextObject = JsonObject()
    focusContextObject.addProperty("visitedContextMap", gson.toJson(focus.getVisitedContexts()))

    return focusContextObject
}

private fun keyAnalyzerJsonObject(keyContext: KeysContextAnalyzer): JsonObject {
    val gson = Gson()

    val keyContextObject = JsonObject()
    keyContextObject.addProperty("keyContextMap", gson.toJson(keyContext.getVisitedContexts()))
    keyContextObject.addProperty("clickedTotalCount", keyContext.keysClicked)

    return keyContextObject
}

/*private fun readActivityJsonProperties(jsonElement : JsonElement) {

}*/
