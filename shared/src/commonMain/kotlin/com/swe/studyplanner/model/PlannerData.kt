package com.swe.studyplanner.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.gson.GsonBuilder
import com.kizitonwose.calendar.core.minusDays
import com.kizitonwose.calendar.core.now
import com.kizitonwose.calendar.core.plusDays
import com.swe.studyplanner.theme.Gruvbox
import kotlinx.datetime.LocalDate
import java.io.File

/**
 * Static sample content for the UI prototype.
 *
 * Deliberately read-only: there is no persistence, no editing and no creation
 * here. Every screen renders this fixed set so the layout can be reviewed
 * without any of the underlying logic existing yet.
 */
class PlannerData {
    val today: LocalDate = LocalDate.now()

    private val studyPlannerDB = File(System.getProperty("user.home"), ".studyplanner_db.json")

    private val gson =
        GsonBuilder()
            .registerTypeAdapter(LocalDate::class.java, DateAdapter())
            .setPrettyPrinting()
            .create()

    var courses: List<Course> by mutableStateOf(emptyList())
        private set

    var tasks: List<TaskItem> by mutableStateOf(emptyList())
        private set

    var blocks: List<StudyBlock> by mutableStateOf(emptyList())
        private set

    var resources: List<ResourceLink> by mutableStateOf(emptyList())
        private set

    init {
        readFromJson()
    }

    fun course(id: Int?): Course? = courses.firstOrNull { it.id == id }

    fun tasksDueOn(date: LocalDate): List<TaskItem> = tasks.filter { it.dueDate == date }

    fun blocksOn(date: LocalDate): List<StudyBlock> = blocks.filter { it.date == date }.sortedBy { it.start }

    fun addCourse(
        name: String,
        code: String,
        instructor: String,
        schedule: String,
        meetings: List<ClassMeeting>,
    ) {
        courses = courses + Course(
            id = (courses.maxOfOrNull { it.id } ?: 0) + 1,
            name = name,
            code = code,
            instructor = instructor,
            schedule = schedule,
            color = Gruvbox.CourseColors[courses.size % Gruvbox.CourseColors.size],
            meetings = meetings,
        )
        saveToJson()
    }

    fun addTask( // Adds task to TaskItem (will have to update with final data storage)
        title: String,
        courseId: Int?,
        dueDate: LocalDate,
        dueTime: String,
        priority: Priority,
        notes: String,
    ) {
        tasks = tasks +
            TaskItem(
                id = (tasks.maxOfOrNull { it.id } ?: 0) + 1,
                title = title,
                courseId = courseId,
                dueDate = dueDate,
                dueTime = dueTime,
                priority = priority,
                notes = notes,
            )
        saveToJson()
    }

    fun addBlock( // Adds block to StudyBlock (will have to update)
        title: String,
        courseId: Int?,
        date: LocalDate,
        start: String,
        end: String,
        repeat: String,
        notes: String,
    ) {
        blocks = blocks +
            StudyBlock(
                id = (blocks.maxOfOrNull { it.id } ?: 0) + 1,
                title = title,
                courseId = courseId,
                date = date,
                start = start,
                end = end,
                repeat = repeat,
                notes = notes,
            )
        saveToJson()
    }

    fun saveToJson() {
        try {
            val dbValues =
                PlannerDatabase(
                    courses,
                    tasks,
                    blocks,
                    resources,
                )
            studyPlannerDB.writeText(gson.toJson(dbValues))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun readFromJson() {
        if (!studyPlannerDB.exists()) return

        try {
            val dbValues = gson.fromJson(studyPlannerDB.readText(), PlannerDatabase::class.java)
            if (dbValues != null) {
                courses = dbValues.courses
                tasks = dbValues.tasks
                blocks = dbValues.blocks
                resources = dbValues.resources
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
