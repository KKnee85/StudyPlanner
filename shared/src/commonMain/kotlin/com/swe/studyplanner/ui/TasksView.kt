package com.swe.studyplanner.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.swe.studyplanner.model.PlannerData
import com.swe.studyplanner.model.TaskItem
import com.swe.studyplanner.theme.Gruvbox

private enum class TaskFilter(val label: String) {
    All("All"),
    Upcoming("Upcoming"),
    Overdue("Overdue"),
    Done("Done"),
}

@Composable
fun TasksView(planner: PlannerData, compact: Boolean) {
    var filter by remember { mutableStateOf(TaskFilter.All) }

    val visible = when (filter) {
        TaskFilter.All -> planner.tasks
        TaskFilter.Upcoming -> planner.tasks.filter { !it.done && it.dueDate >= planner.today }
        TaskFilter.Overdue -> planner.tasks.filter { !it.done && it.dueDate < planner.today }
        TaskFilter.Done -> planner.tasks.filter { it.done }
    }.sortedWith(compareBy({ it.done }, { it.dueDate }))

    Column(Modifier.fillMaxSize().padding(horizontal = if (compact) 16.dp else 28.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TaskFilter.entries.forEach { option ->
                FilterChip(
                    selected = filter == option,
                    onClick = { filter = option },
                    label = { Text(option.label) },
                    shape = RoundedCornerShape(8.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Gruvbox.Bg2,
                        selectedLabelColor = Gruvbox.Green,
                        labelColor = Gruvbox.Fg3,
                        containerColor = Gruvbox.Bg0,
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = filter == option,
                        borderColor = Gruvbox.Bg1,
                        selectedBorderColor = Gruvbox.Bg3,
                    ),
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(visible, key = { it.id }) { task ->
                TaskCard(task, planner)
            }
        }
    }
}

@Composable
private fun TaskCard(task: TaskItem, planner: PlannerData) {
    val course = planner.course(task.courseId)
    val overdue = !task.done && task.dueDate < planner.today

    Surface(color = Gruvbox.Bg0, shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Read-only in the prototype: the checkbox shows state, it does not
            // change it. Toggling arrives with the task logic.
            Checkbox(
                checked = task.done,
                onCheckedChange = null,
                colors = CheckboxDefaults.colors(
                    checkedColor = Gruvbox.Green,
                    checkmarkColor = Gruvbox.BgHard,
                    uncheckedColor = Gruvbox.Bg4,
                ),
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (task.done) Gruvbox.Gray else Gruvbox.Fg,
                    textDecoration = if (task.done) TextDecoration.LineThrough else null,
                )
                Spacer(Modifier.height(3.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CourseTag(course)
                    Spacer(Modifier.width(10.dp))
                    Text(
                        text = "${task.dueDate.shortLabel()}  ·  ${task.dueTime}",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (overdue) Gruvbox.Red else Gruvbox.Fg3,
                        fontWeight = if (overdue) FontWeight.Medium else FontWeight.Normal,
                    )
                }
            }
            PriorityBadge(task.priority)
        }
    }
}
