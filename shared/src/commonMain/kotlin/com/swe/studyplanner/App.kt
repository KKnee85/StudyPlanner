package com.swe.studyplanner

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.reload.DevelopmentEntryPoint
import com.swe.studyplanner.model.Course
import com.swe.studyplanner.model.PlannerData
import com.swe.studyplanner.theme.Gruvbox
import com.swe.studyplanner.theme.StudyPlannerTheme
import com.swe.studyplanner.ui.AppSection
import com.swe.studyplanner.ui.CalendarGrid
import com.swe.studyplanner.ui.ClassesView
import com.swe.studyplanner.ui.CourseDetailView
import com.swe.studyplanner.ui.DayPanel
import com.swe.studyplanner.ui.PlannerDialog
import com.swe.studyplanner.ui.ResourcesView
import com.swe.studyplanner.ui.Sidebar
import com.swe.studyplanner.ui.TasksView
import com.swe.studyplanner.ui.TopBar
import com.swe.studyplanner.ui.dialogs.NewBlockDialog
import com.swe.studyplanner.ui.dialogs.NewTaskDialog

private const val ZOOM_STEP = 0.05f
private const val ZOOM_MIN = 0.8f
private const val ZOOM_MAX = 1.6f

@Composable
@Preview
@DevelopmentEntryPoint
fun App() {
    StudyPlannerTheme {
        // Static prototype data: nothing here is created, edited or saved.
        val planner = remember { PlannerData() }
        var section by remember { mutableStateOf(AppSection.Calendar) }
        var selectedDate by remember { mutableStateOf(planner.today) }
        var openCourse by remember { mutableStateOf<Course?>(null) }
        var dialog by remember { mutableStateOf<PlannerDialog?>(null) }
        var zoom by remember { mutableStateOf(1f) }

        val base = LocalDensity.current
        CompositionLocalProvider(
            LocalDensity provides Density(
                density = base.density * zoom,
                fontScale = base.fontScale * zoom,
            ),
        ) {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                BoxWithConstraints(Modifier.fillMaxSize()) {
                    val compact = maxWidth < 980.dp
                    val railOnly = maxWidth < 1180.dp
                    val calendarWide = section == AppSection.Calendar && !compact

                    Row(Modifier.fillMaxSize()) {
                        Sidebar(
                            current = section,
                            onSelect = {
                                section = it
                                openCourse = null
                            },
                            planner = planner,
                            compact = railOnly,
                            zoom = zoom,
                            onZoomIn = { zoom = (zoom + ZOOM_STEP).coerceAtMost(ZOOM_MAX) },
                            onZoomOut = { zoom = (zoom - ZOOM_STEP).coerceAtLeast(ZOOM_MIN) },
                        )

                        if (calendarWide) {
                            Column(Modifier.weight(1f).fillMaxHeight()) {
                                TopBar(
                                    title = section.label,
                                    subtitle = subtitleFor(section, planner),
                                    compact = false,
                                )
                                CalendarGrid(
                                    planner = planner,
                                    selectedDate = selectedDate,
                                    onSelectDate = { selectedDate = it },
                                    compact = false,
                                    modifier = Modifier.weight(1f),
                                )
                            }
                            VerticalDivider(color = Gruvbox.Bg1)
                            DayPanel(
                                planner = planner,
                                date = selectedDate,
                                onNewTask = { dialog = PlannerDialog.NewTask },
                                onNewBlock = { dialog = PlannerDialog.NewBlock },
                                compact = false,
                                modifier = Modifier.width(360.dp).fillMaxHeight(),
                            )
                        } else {
                            Column(Modifier.weight(1f).fillMaxHeight()) {
                                TopBar(
                                    title = openCourse?.code ?: section.label,
                                    subtitle = openCourse?.name ?: subtitleFor(section, planner),
                                    compact = compact,
                                )
                                Box(Modifier.weight(1f).fillMaxWidth()) {
                                    when (section) {
                                        AppSection.Calendar -> Column(Modifier.fillMaxSize()) {
                                            CalendarGrid(
                                                planner = planner,
                                                selectedDate = selectedDate,
                                                onSelectDate = { selectedDate = it },
                                                compact = true,
                                                modifier = Modifier.weight(1f),
                                            )
                                            HorizontalDivider(color = Gruvbox.Bg1)
                                            DayPanel(
                                                planner = planner,
                                                date = selectedDate,
                                                onNewTask = { dialog = PlannerDialog.NewTask },
                                                onNewBlock = { dialog = PlannerDialog.NewBlock },
                                                compact = true,
                                                modifier = Modifier.fillMaxWidth().height(260.dp),
                                            )
                                        }
                                        AppSection.Tasks -> TasksView(planner, compact)
                                        AppSection.Classes -> {
                                            val course = openCourse
                                            if (course == null) {
                                                ClassesView(planner, compact) { openCourse = it }
                                            } else {
                                                CourseDetailView(course, onBack = { openCourse = null }, compact = compact)
                                            }
                                        }
                                        AppSection.Resources -> ResourcesView(planner, compact)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Visual only: both dialogs close on Save without storing anything.
        when (dialog) {
            PlannerDialog.NewTask -> NewTaskDialog(
                planner = planner,
                defaultDate = selectedDate,
                onDismiss = { dialog = null },
            )
            PlannerDialog.NewBlock -> NewBlockDialog(
                planner = planner,
                defaultDate = selectedDate,
                onDismiss = { dialog = null },
            )
            null -> Unit
        }
    }
}

private fun subtitleFor(section: AppSection, planner: PlannerData): String = when (section) {
    AppSection.Calendar -> "Classes, deadlines and study blocks for the whole term"
    AppSection.Tasks -> {
        val open = planner.tasks.count { !it.done }
        val done = planner.tasks.count { it.done }
        val overdue = planner.tasks.count { !it.done && it.dueDate < planner.today }
        "$open open · $done done · $overdue overdue"
    }
    AppSection.Classes -> "${planner.courses.size} classes this term"
    AppSection.Resources -> "${planner.resources.size} saved resources"
}
