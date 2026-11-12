package com.swe.studyplanner.model

import androidx.compose.ui.graphics.Color
import kotlinx.datetime.LocalDate
import kotlinx.datetime.DayOfWeek

/** How urgent a task is; drives the badge colour only. */
enum class Priority(val label: String) {
    Low("Low"),
    Medium("Medium"),
    High("High"),
}

data class ClassMeeting(
    val dayOfWeek: DayOfWeek,
    val start: String,
    val end: String,
)

/** A class the student is taking (US-03 "Create Classes"). */
data class Course(
    val id: Int,
    val name: String,
    val code: String,
    val instructor: String,
    val schedule: String,
    val color: Color,
    val meetings: List<ClassMeeting>? = null,
)

/** A deadline (US-06 "Schedule Due Dates"). */
data class TaskItem(
    val id: Int,
    val title: String,
    val courseId: Int?,
    val dueDate: LocalDate,
    val dueTime: String,
    val priority: Priority,
    val notes: String = "",
    val done: Boolean = false,
)

/** A study block or other scheduled period (US-05 "Schedule Time Blocks"). */
data class StudyBlock(
    val id: Int,
    val title: String,
    val courseId: Int?,
    val date: LocalDate,
    val start: String,
    val end: String,
    val repeat: String = "Once",
    val notes: String = "",
)

/** A link or file attached to zero or more classes (US-12/13). */
data class ResourceLink(
    val id: Int,
    val title: String,
    val location: String,
    val kind: String,
    val courseIds: List<Int>,
)
