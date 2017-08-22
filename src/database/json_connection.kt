package database

import activities.Activity
import activities.FocusContextAnalyzer
import activities.KeyContextAnalyzer
import activities.days.Day
import activities.days.hour.HourActivity
import activities.projects.Project
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.*
import java.util.*
import kotlin.collections.HashSet

/**
 * Class that represents connection between model of projects
 * and JSON file, that holds data for this model.
 */
public class ProjectConnectionJson : DatabaseConnection<Project> {

    override fun save(obj: Set<Project>, holderName: String) {
        val file = File(holderName)
        if (!file.exists() || file.isDirectory) file.createNewFile()

        BufferedWriter(OutputStreamWriter(FileOutputStream(file))).use {
            it.write(getProjectSetJson(obj))
        }
    }

    override fun read(holderName: String): Set<Project> {
        val file = File(holderName)
        if (!file.exists() || file.isDirectory) {
            throw NoSuchFileException(file)
        }

        return readProjectsSet(
                JsonParser().parse(BufferedReader(InputStreamReader(FileInputStream(file))))
        )
    }

    @Suppress("UNCHECKED_CAST")
    private fun readProjectsSet(json: JsonElement): Set<Project> {
        val set = HashSet<Project>()

        val rootObject = json.asJsonObject
        rootObject.entrySet().forEach {
            val project : JsonObject = it.value.asJsonObject
            set.add(Project(_projectName = it.key,
                    _mouseClickedCount = project.get("mouseClickedCount").asInt,
                    _timeSpentAfkInSec = project.get("timeSpentAfkInSec").asInt,
                    _timeSpentInSec = project.get("timeSpentInSec").asInt,
                    _timerStartsCount = project.get("timerStartsCount").asInt,
                    _focusContextMap = getFocusContextMap(project.get("focusContext")),
                    _keysContextMap = getKeyContextMap(project.get("keyContext")),
                    _keysClickedCount = getKeyContextClicked(project.get("keyContext")),
                    _dateOfCreation = getDateOfProjectCreation(project.get("dateOfCreation")))
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

    private fun getDateOfProjectCreation(element: JsonElement) =
            Gson().fromJson(element.asString, Calendar::class.java)
}


internal class DaysConnectionJson: DatabaseConnection<Day> {
    override fun save(obj: Set<Day>, holderName: String) {
        val file = File(holderName)
        if (!file.exists() || file.isDirectory) file.createNewFile()

        BufferedWriter(OutputStreamWriter(FileOutputStream(file))).use {
            it.write(getDaysSetJson(obj))
        }
    }

    override fun read(holderName: String): Set<Day> {
        val file = File(holderName)
        if (!file.exists() || file.isDirectory) {
            throw NoSuchFileException(file)
        }

        return readDaySet(
                JsonParser().parse(BufferedReader(InputStreamReader(FileInputStream(file))))
        )
    }

    private fun getDaysSetJson(days: Set<Day>): String {
        val rootObj = JsonObject()

        for (obj in days) {
            rootObj.add(obj.date.toString(), getDayJson(obj))
        }

        return Gson().toJson(rootObj)
    }

    private fun getDayJson(day: Day): JsonElement {
        val dayValues = JsonObject()

        addActivityJsonProperties(dayValues, day)

        val date = Gson().toJson(day.date)
        dayValues.addProperty("date", date)
        dayValues.add("hourActivities", getHourActivitiesJson(day))

        return dayValues
    }

    /**
     * Save to days set of Hours, 1-24 which
     * are actually activities aswell.
     */
    private fun getHourActivitiesJson(day: Day): JsonElement {
        val rootObj = JsonObject()

        day.getHourActivities().forEach {
            rootObj.add(it.hour.toString(), getHourActivityJson(it))
        }

        return rootObj
    }

    private fun getHourActivityJson(hourActivity: HourActivity): JsonElement {
        val hourValues = JsonObject()

        addActivityJsonProperties(hourValues, hourActivity)
        return hourValues
    }

    @Suppress("UNCHECKED_CAST")
    private fun readDaySet(json: JsonElement): Set<Day> {
        val set = HashSet<Day>()

        val rootObject = json.asJsonObject
        rootObject.entrySet().forEach {
            val day : JsonObject = it.value.asJsonObject
            set.add(Day(date = getDayDate(day.get("date")),
                    _hourActivities = readHourSet(day.get("hourActivities")),
                    _mouseClickedCount = day.get("mouseClickedCount").asInt,
                    _timeSpentAfkInSec = day.get("timeSpentAfkInSec").asInt,
                    _timeSpentInSec = day.get("timeSpentInSec").asInt,
                    _timerStartsCount = day.get("timerStartsCount").asInt,
                    _focusContextMap = getFocusContextMap(day.get("focusContext")),
                    _keysContextMap = getKeyContextMap(day.get("keyContext")),
                    _keysClickedCount = getKeyContextClicked(day.get("keyContext")))
            )
        }

        return set
    }

    private fun getDayDate(element: JsonElement) =
            Gson().fromJson(element.asString, activities.days.Date::class.java)

    private fun readHourSet(json: JsonElement): Set<HourActivity> {
        val set = HashSet<HourActivity>(24)

        val rootObject = json.asJsonObject
        rootObject.entrySet().forEach {
            val hour : JsonObject = it.value.asJsonObject
            set.add(HourActivity(hour = Integer.parseInt(it.key),
                    _mouseClickedCount = hour.get("mouseClickedCount").asInt,
                    _timeSpentAfkInSec = hour.get("timeSpentAfkInSec").asInt,
                    _timeSpentInSec = hour.get("timeSpentInSec").asInt,
                    _timerStartsCount = hour.get("timerStartsCount").asInt,
                    _focusContextMap = getFocusContextMap(hour.get("focusContext")),
                    _keysContextMap = getKeyContextMap(hour.get("keyContext")),
                    _keysClickedCount = getKeyContextClicked(hour.get("keyContext")))
            )
        }

        return set
    }
}


private fun addActivityJsonProperties(jsonObject: JsonObject, activity: Activity) {
    jsonObject.addProperty("mouseClickedCount", activity.mouseClickedCount)
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

private fun keyAnalyzerJsonObject(keyContext: KeyContextAnalyzer): JsonObject {
    val gson = Gson()

    val keyContextObject = JsonObject()
    keyContextObject.addProperty("keyContextMap", gson.toJson(keyContext.getClickedButtons()))
    keyContextObject.addProperty("clickedTotalCount", keyContext.keysClicked)

    return keyContextObject
}

@Suppress("UNCHECKED_CAST")
private fun getKeyContextMap(obj: JsonElement) =
        Gson().fromJson(obj.asJsonObject.get("keyContextMap").asString, Map::class.java) as Map<String,Int>


private fun getKeyContextClicked(obj: JsonElement) = obj.asJsonObject.get("clickedTotalCount").asInt

@Suppress("UNCHECKED_CAST")
private fun getFocusContextMap(obj: JsonElement) =
     Gson().fromJson(obj.asJsonObject.get("visitedContextMap").asString, Map::class.java) as Map<String,Long>
