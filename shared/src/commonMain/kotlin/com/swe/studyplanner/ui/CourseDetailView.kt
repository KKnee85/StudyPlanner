package com.swe.studyplanner.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.swe.studyplanner.model.Course
import com.swe.studyplanner.model.Priority
import com.swe.studyplanner.theme.Gruvbox

private data class DemoDeadline(val title: String, val due: String, val priority: Priority)
private data class DemoSession(val day: String, val time: String, val place: String)
private data class DemoResource(val title: String, val detail: String, val file: Boolean)

private data class CourseDemo(
    val sessions: List<DemoSession>,
    val deadlines: List<DemoDeadline>,
    val resources: List<DemoResource>,
)

/**
 * Hardcoded per-class demo content. In the real app this would be assembled
 * from the class, its scheduled blocks, deadlines and resource associations;
 * here it exists only to show the shape of the overview screen.
 */
private fun demoFor(course: Course): CourseDemo = when (course.id) {
    1 -> CourseDemo(
        sessions = listOf(
            DemoSession("Mon", "10:00 - 11:15", "Engineering 204"),
            DemoSession("Wed", "10:00 - 11:15", "Engineering 204"),
        ),
        deadlines = listOf(
            DemoDeadline("Sprint 2 prototype", "Nov 10 · 23:59", Priority.High),
            DemoDeadline("Sprint review slides", "Nov 19 · 23:59", Priority.Medium),
            DemoDeadline("Group retro notes", "Nov 9 · 16:00", Priority.High),
        ),
        resources = listOf(
            DemoResource("Compose Multiplatform docs", "jetbrains.com/help/kotlin-multiplatform-dev", false),
            DemoResource("Calendar library samples", "calendar.kizitonwose.dev", false),
            DemoResource("Sprint backlog", "~/Documents/School/SWE2050/backlog.pdf", true),
        ),
    )
    2 -> CourseDemo(
        sessions = listOf(
            DemoSession("Tue", "13:00 - 14:15", "Turing Hall 1"),
            DemoSession("Thu", "13:00 - 14:15", "Turing Hall 1"),
        ),
        deadlines = listOf(
            DemoDeadline("Read chapter 7", "Nov 15 · 09:00", Priority.Low),
            DemoDeadline("Lab 4: scheduling", "Nov 21 · 23:59", Priority.Medium),
        ),
        resources = listOf(
            DemoResource("OS lecture slides", "~/Documents/School/CS3410/slides.pdf", true),
            DemoResource("xv6 book", "pdos.csail.mit.edu/6.828/xv6", false),
        ),
    )
    3 -> CourseDemo(
        sessions = listOf(
            DemoSession("Mon", "09:00 - 09:50", "Math 12"),
            DemoSession("Wed", "09:00 - 09:50", "Math 12"),
            DemoSession("Fri", "09:00 - 09:50", "Math 12"),
        ),
        deadlines = listOf(
            DemoDeadline("Problem set 6", "Nov 11 · 17:00", Priority.Medium),
            DemoDeadline("Quiz 3 revision", "Nov 17 · 08:00", Priority.Medium),
        ),
        resources = listOf(
            DemoResource("Linear algebra notes", "~/Documents/School/MATH2210/notes.pdf", true),
        ),
    )
    else -> CourseDemo(
        sessions = listOf(
            DemoSession("Tue", "08:00 - 09:15", "Science 3"),
            DemoSession("Thu", "08:00 - 09:15", "Science 3"),
        ),
        deadlines = listOf(
            DemoDeadline("Lab report: optics", "Nov 13 · 12:00", Priority.High),
        ),
        resources = listOf(
            DemoResource("Physics formula sheet", "example.edu/phys1010/formulas", false),
        ),
    )
}

@Composable
fun CourseDetailView(course: Course, onBack: () -> Unit, compact: Boolean) {
    val demo = demoFor(course)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = if (compact) 16.dp else 28.dp),
    ) {
        TextButton(onClick = onBack, contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)) {
            Icon(Icons.Default.KeyboardArrowLeft, null, tint = Gruvbox.Fg2)
            Spacer(Modifier.width(4.dp))
            Text("All classes", color = Gruvbox.Fg2)
        }
        Spacer(Modifier.height(8.dp))

        // Header
        Surface(color = Gruvbox.Bg0, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
            Column {
                Box(Modifier.fillMaxWidth().height(4.dp).background(course.color))
                Row(Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text(course.code, style = MaterialTheme.typography.labelSmall, color = course.color, fontWeight = FontWeight.Medium)
                        Spacer(Modifier.height(4.dp))
                        Text(course.name, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.SemiBold, color = Gruvbox.Fg)
                        Spacer(Modifier.height(8.dp))
                        Text(course.instructor, style = MaterialTheme.typography.bodySmall, color = Gruvbox.Fg2)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(course.schedule, style = MaterialTheme.typography.labelSmall, color = Gruvbox.Fg3, fontFamily = FontFamily.Monospace)
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        DetailSection("Schedule") {
            demo.sessions.forEach { session ->
                Row(Modifier.fillMaxWidth().padding(vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(session.day, style = MaterialTheme.typography.labelMedium, color = Gruvbox.Fg2, modifier = Modifier.width(52.dp))
                    Text(session.time, style = MaterialTheme.typography.bodyMedium, color = Gruvbox.Fg, fontFamily = FontFamily.Monospace)
                    Spacer(Modifier.weight(1f))
                    Text(session.place, style = MaterialTheme.typography.labelSmall, color = Gruvbox.Fg3)
                }
            }
        }

        DetailSection("Upcoming deadlines") {
            demo.deadlines.forEach { deadline ->
                Row(Modifier.fillMaxWidth().padding(vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                    Dot(course.color, 8)
                    Spacer(Modifier.width(10.dp))
                    Column(Modifier.weight(1f)) {
                        Text(deadline.title, style = MaterialTheme.typography.bodyMedium, color = Gruvbox.Fg)
                        Text(deadline.due, style = MaterialTheme.typography.labelSmall, color = Gruvbox.Fg3)
                    }
                    PriorityBadge(deadline.priority)
                }
            }
        }

        DetailSection("Resources") {
            demo.resources.forEach { resource ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp)
                        .clickable { }
                        .padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = if (resource.file) Icons.Outlined.Description else Icons.Outlined.Link,
                        contentDescription = null,
                        tint = if (resource.file) Gruvbox.Orange else Gruvbox.Blue,
                        modifier = Modifier.size(16.dp),
                    )
                    Spacer(Modifier.width(10.dp))
                    Column(Modifier.weight(1f)) {
                        Text(resource.title, style = MaterialTheme.typography.bodyMedium, color = Gruvbox.Fg)
                        Text(resource.detail, style = MaterialTheme.typography.labelSmall, color = Gruvbox.Fg3, fontFamily = FontFamily.Monospace)
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun DetailSection(title: String, content: @Composable () -> Unit) {
    Surface(color = Gruvbox.Bg0, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
        Column(Modifier.padding(horizontal = 18.dp, vertical = 14.dp)) {
            Text(title.uppercase(), style = MaterialTheme.typography.labelSmall, color = Gruvbox.Gray)
            Spacer(Modifier.height(8.dp))
            HorizontalDivider(color = Gruvbox.Bg1)
            Spacer(Modifier.height(4.dp))
            content()
        }
    }
}
