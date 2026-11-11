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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.swe.studyplanner.model.Course
import com.swe.studyplanner.model.PlannerData
import com.swe.studyplanner.theme.Gruvbox

@Composable
fun ClassesView(
    planner: PlannerData,
    compact: Boolean,
    onCourseClick: (Course) -> Unit,
    onNewClass: () -> Unit,
) {
    Column(Modifier.fillMaxSize().padding(horizontal = if (compact) 16.dp else 28.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (!compact) {
                Text(
                    text = "Deadlines and study blocks are attached to a class",
                    style = MaterialTheme.typography.labelSmall,
                    color = Gruvbox.Gray,
                )
            }
            Spacer(Modifier.weight(1f))
            Button(
                onClick = onNewClass,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Gruvbox.Green,
                    contentColor = Gruvbox.BgHard,
                ),
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(6.dp))
                Text("New class")
            }
        }
        Spacer(Modifier.height(14.dp))
        LazyVerticalGrid(
            columns = GridCells.Adaptive(280.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            items(planner.courses, key = { it.id }) { course ->
                CourseCard(course, planner, onCourseClick)
            }
        }
    }
}

@Composable
private fun CourseCard(course: Course, planner: PlannerData, onClick: (Course) -> Unit) {
    val deadlines = planner.tasks.count { it.courseId == course.id }
    val blocks = planner.blocks.count { it.courseId == course.id }

    Surface(
        color = Gruvbox.Bg0,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth().clickable { onClick(course) },
    ) {
        Column {
            Box(Modifier.fillMaxWidth().height(4.dp).background(course.color))
            Column(Modifier.padding(16.dp)) {
                Text(
                    text = course.code,
                    style = MaterialTheme.typography.labelSmall,
                    color = course.color,
                    fontWeight = FontWeight.Medium,
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = course.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = Gruvbox.Fg,
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    text = course.instructor,
                    style = MaterialTheme.typography.bodySmall,
                    color = Gruvbox.Fg2,
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = course.schedule,
                    style = MaterialTheme.typography.labelSmall,
                    color = Gruvbox.Fg3,
                    fontFamily = FontFamily.Monospace,
                )
                Spacer(Modifier.height(16.dp))
                HorizontalDivider(color = Gruvbox.Bg1)
                Spacer(Modifier.height(10.dp))
                Row {
                    Text(
                        text = "$deadlines deadlines",
                        style = MaterialTheme.typography.labelSmall,
                        color = Gruvbox.Fg3,
                    )
                    Spacer(Modifier.width(16.dp))
                    Text(
                        text = "$blocks blocks",
                        style = MaterialTheme.typography.labelSmall,
                        color = Gruvbox.Fg3,
                    )
                }
            }
        }
    }
}
