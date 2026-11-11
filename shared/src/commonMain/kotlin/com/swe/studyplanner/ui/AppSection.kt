package com.swe.studyplanner.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Checklist
import androidx.compose.material.icons.outlined.FolderOpen
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.ui.graphics.vector.ImageVector

/** Top-level destinations. One screen each. */
enum class AppSection(val label: String, val icon: ImageVector) {
    Calendar("Calendar", Icons.Outlined.CalendarMonth),
    Tasks("Tasks", Icons.Outlined.Checklist),
    Classes("Classes", Icons.Outlined.MenuBook),
    Resources("Resources", Icons.Outlined.FolderOpen),
}

/** Which modal form is open. The forms are visual only: saving closes them. */
enum class PlannerDialog {
    NewTask,
    NewBlock,
}
