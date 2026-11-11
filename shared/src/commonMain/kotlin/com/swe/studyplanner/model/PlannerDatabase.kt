package com.swe.studyplanner.model

data class PlannerDatabase(
    val courses: List<Course> = emptyList(),
    val tasks: List<TaskItem> = emptyList(),
    val blocks: List<StudyBlock> = emptyList(),
    val resources: List<ResourceLink> = emptyList(),
)
