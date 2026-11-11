package com.swe.studyplanner.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.swe.studyplanner.model.Course
import com.swe.studyplanner.model.Priority
import com.swe.studyplanner.theme.Gruvbox

@Composable
fun Dot(color: Color, size: Int = 8) {
    Box(Modifier.size(size.dp).clip(CircleShape).background(color))
}

/** Small rounded tag showing a class code and its colour. */
@Composable
fun CourseTag(course: Course?, modifier: Modifier = Modifier) {
    if (course == null) {
        Text(
            text = "No class",
            modifier = modifier,
            style = MaterialTheme.typography.labelSmall,
            color = Gruvbox.Gray,
        )
        return
    }
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(Gruvbox.Bg1)
            .padding(horizontal = 8.dp, vertical = 3.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Dot(course.color, 7)
        Spacer(Modifier.width(6.dp))
        Text(
            text = course.code,
            style = MaterialTheme.typography.labelSmall,
            color = Gruvbox.Fg1,
        )
    }
}

@Composable
fun PriorityBadge(priority: Priority) {
    val color = when (priority) {
        Priority.High -> Gruvbox.Red
        Priority.Medium -> Gruvbox.Yellow
        Priority.Low -> Gruvbox.Aqua
    }
    Text(
        text = priority.label,
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(color.copy(alpha = 0.16f))
            .padding(horizontal = 8.dp, vertical = 3.dp),
        color = color,
        style = MaterialTheme.typography.labelSmall,
    )
}

@Composable
fun SectionLabel(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text.uppercase(),
        modifier = modifier,
        style = MaterialTheme.typography.labelSmall,
        color = Gruvbox.Gray,
    )
}

@Composable
fun EmptyState(title: String, message: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth().padding(vertical = 22.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(title, color = Gruvbox.Fg2, style = MaterialTheme.typography.titleSmall)
        Spacer(Modifier.size(4.dp))
        Text(message, color = Gruvbox.Gray, style = MaterialTheme.typography.bodySmall)
    }
}
