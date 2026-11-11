package com.swe.studyplanner.model

import com.kizitonwose.calendar.core.minusDays
import com.kizitonwose.calendar.core.now
import com.kizitonwose.calendar.core.plusDays
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.swe.studyplanner.theme.Gruvbox
import kotlinx.datetime.LocalDate

/**
 * Static sample content for the UI prototype.
 *
 * Deliberately read-only: there is no persistence, no editing and no creation
 * here. Every screen renders this fixed set so the layout can be reviewed
 * without any of the underlying logic existing yet.
 */
class PlannerData {
    val today: LocalDate = LocalDate.now()

    val courses: List<Course> = listOf(
        Course(1, "Software Engineering", "SWE 2050", "Dr. Alvarez", "Mon / Wed  10:00 - 11:15", Gruvbox.Blue),
        Course(2, "Operating Systems", "CS 3410", "Prof. Chen", "Tue / Thu  13:00 - 14:15", Gruvbox.Aqua),
        Course(3, "Linear Algebra", "MATH 2210", "Dr. Okafor", "Mon / Wed / Fri  09:00 - 09:50", Gruvbox.Yellow),
        Course(4, "Physics I", "PHYS 1010", "Dr. Singh", "Tue / Thu  08:00 - 09:15", Gruvbox.Purple),
    )

    var tasks: List<TaskItem> by mutableStateOf(listOf(
        // Keep all your existing TaskItem entries here.
        // TaskItem(1, "Sprint 2 prototype", 1, today, "23:59", Priority.High, "Calendar, classes, due dates"),
        TaskItem(2, "Problem set 6", 3, today.plusDays(1), "17:00", Priority.Medium),
        TaskItem(3, "Lab report: optics", 4, today.plusDays(3), "12:00", Priority.High, "Include error analysis"),
        TaskItem(4, "Read chapter 7", 2, today.plusDays(5), "09:00", Priority.Low),
        TaskItem(5, "Group retro notes", 1, today.minusDays(1), "16:00", Priority.High, "Was due yesterday"),
        TaskItem(6, "Quiz 3 revision", 3, today.plusDays(7), "08:00", Priority.Medium),
        TaskItem(7, "Sprint review slides", 1, today.plusDays(9), "23:59", Priority.Medium, done = true),
    ))
        private set

    var blocks: List<StudyBlock> by mutableStateOf(listOf(
        StudyBlock(1, "Lecture", 2, today, "13:00", "14:15", "Weekly"),
        StudyBlock(2, "Study: Sprint 2", 1, today, "18:00", "20:00", "Once"),
        StudyBlock(3, "Problem set session", 3, today.plusDays(1), "10:00", "11:30", "Once"),
        StudyBlock(4, "Lab prep", 4, today.plusDays(2), "15:00", "16:30", "Once"),
        StudyBlock(5, "Group standup", 1, today.plusDays(2), "09:00", "09:30", "Weekly"),
    ))
        private set

    val resources: List<ResourceLink> = listOf(
        ResourceLink(1, "Compose Multiplatform docs", "https://www.jetbrains.com/help/kotlin-multiplatform-dev/", "Link", listOf(1)),
        ResourceLink(2, "Calendar library samples", "https://calendar.kizitonwose.dev", "Link", listOf(1)),
        ResourceLink(3, "OS lecture slides", "~/Documents/School/CS3410/slides.pdf", "File", listOf(2)),
        ResourceLink(4, "Linear algebra notes", "~/Documents/School/MATH2210/notes.pdf", "File", listOf(3)),
        ResourceLink(5, "Physics formula sheet", "https://example.edu/phys1010/formulas", "Link", listOf(4)),
    )

    fun course(id: Int?): Course? = courses.firstOrNull { it.id == id }

    fun tasksDueOn(date: LocalDate): List<TaskItem> = tasks.filter { it.dueDate == date }

    fun blocksOn(date: LocalDate): List<StudyBlock> =
        blocks.filter { it.date == date }.sortedBy { it.start }

    fun addTask( // Adds task to TaskItem (will have to update with final data storage)
        title: String,
        courseId: Int?,
        dueDate: LocalDate,
        dueTime: String,
        priority: Priority,
        notes: String,
    ) {
        tasks = tasks + TaskItem(
            id = (tasks.maxOfOrNull { it.id } ?: 0) + 1,
            title = title,
            courseId = courseId,
            dueDate = dueDate,
            dueTime = dueTime,
            priority = priority,
            notes = notes,
        )
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
        blocks = blocks + StudyBlock(
            id = (blocks.maxOfOrNull { it.id } ?: 0) + 1,
            title = title,
            courseId = courseId,
            date = date,
            start = start,
            end = end,
            repeat = repeat,
            notes = notes,
        )
    }
}
