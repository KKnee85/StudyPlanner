package com.swe.studyplanner.ui.dialogs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.swe.studyplanner.model.PlannerData
import com.swe.studyplanner.model.Priority
import com.swe.studyplanner.theme.Gruvbox
import com.swe.studyplanner.ui.SectionLabel
import kotlinx.datetime.LocalDate

/**
 * Modal forms for the prototype.
 *
 * They are deliberately not wired to anything: the fields work, Save closes the
 * dialog, and nothing is stored or reflected anywhere else.
 */

@Composable
private fun PlannerDialogShell(
    title: String,
    confirmLabel: String,
    confirmEnabled: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = Gruvbox.Bg0,
            border = BorderStroke(1.dp, Gruvbox.Bg2),
            modifier = Modifier.width(470.dp),
        ) {
            Column(Modifier.padding(24.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = Gruvbox.Fg,
                )
                Spacer(Modifier.height(18.dp))
                content()
                Spacer(Modifier.height(24.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismiss) { Text("Cancel", color = Gruvbox.Fg3) }
                    Spacer(Modifier.width(6.dp))
                    Button(
                        onClick = onConfirm,
                        enabled = confirmEnabled,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Gruvbox.Green,
                            contentColor = Gruvbox.BgHard,
                        ),
                    ) {
                        Text(confirmLabel)
                    }
                }
            }
        }
    }
}

@Composable
private fun Field(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    singleLine: Boolean = true,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = { if (placeholder.isNotEmpty()) Text(placeholder, color = Gruvbox.Bg4) },
        singleLine = singleLine,
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Gruvbox.Green,
            unfocusedBorderColor = Gruvbox.Bg3,
            focusedLabelColor = Gruvbox.Green,
            unfocusedLabelColor = Gruvbox.Gray,
            focusedTextColor = Gruvbox.Fg,
            unfocusedTextColor = Gruvbox.Fg1,
            cursorColor = Gruvbox.Green,
            focusedContainerColor = Gruvbox.Bg0,
            unfocusedContainerColor = Gruvbox.Bg0,
        ),
    )
}

@Composable
private fun Chip(text: String, selected: Boolean, accent: Color? = null, onClick: () -> Unit) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(text) },
        shape = RoundedCornerShape(8.dp),
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = Gruvbox.Bg2,
            selectedLabelColor = accent ?: Gruvbox.Green,
            labelColor = Gruvbox.Fg2,
            containerColor = Gruvbox.Bg1,
        ),
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = selected,
            borderColor = Gruvbox.Bg2,
            selectedBorderColor = accent ?: Gruvbox.Green,
        ),
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CourseChips(planner: PlannerData, selected: Int?, onSelect: (Int?) -> Unit) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Chip("No class", selected = selected == null) { onSelect(null) }
        planner.courses.forEach { course ->
            Chip(course.code, selected = course.id == selected, accent = course.color) { onSelect(course.id) }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NewTaskDialog(
    planner: PlannerData,
    defaultDate: LocalDate,
    onDismiss: () -> Unit,
) {
    var title by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var dateText by remember { mutableStateOf(defaultDate.toString()) }
    var timeText by remember { mutableStateOf("17:00") }
    var courseId by remember { mutableStateOf<Int?>(null) }
    var priority by remember { mutableStateOf(Priority.Medium) }

    val dueDate = runCatching { LocalDate.parse(dateText.trim()) }.getOrNull() // Checks for valid date or null
    val dueTime = timeText.trim()
    val validTime = Regex("([01][0-9]|2[0-3]):[0-5][0-9]").matches(dueTime) // Checks for valid time format

    PlannerDialogShell(
        title = "New deadline",
        confirmLabel = "Add deadline",
        confirmEnabled = title.isNotBlank() && dueDate != null && validTime,
        onDismiss = onDismiss,
        onConfirm = {
            if (dueDate != null && title.isNotBlank() && validTime) {
                planner.addTask(
                    title = title.trim(),
                    courseId = courseId,
                    dueDate = dueDate,
                    dueTime = dueTime,
                    priority = priority,
                    notes = notes.trim(),
                )
                onDismiss()
            }
        }
    ) {
        Field("Title", title, { title = it }, "e.g. Problem set 7")
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Column(Modifier.weight(1f)) {
                Field("Due date", dateText, { dateText = it }, "YYYY-MM-DD")
            }
            Column(Modifier.weight(1f)) {
                Field("Time", timeText, { timeText = it }, "HH:MM")
            }
        }
        Spacer(Modifier.height(16.dp))
        SectionLabel("Class")
        Spacer(Modifier.height(8.dp))
        CourseChips(planner, selected = courseId) { courseId = it }
        Spacer(Modifier.height(16.dp))
        SectionLabel("Priority")
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Priority.entries.forEach { option ->
                Chip(option.label, selected = priority == option) { priority = option }
            }
        }
        Spacer(Modifier.height(16.dp))
        Field("Notes", notes, { notes = it }, "Optional", singleLine = false)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NewBlockDialog(
    planner: PlannerData,
    defaultDate: LocalDate,
    onDismiss: () -> Unit,
) {
    var title by remember { mutableStateOf("") }
    var dateText by remember { mutableStateOf(defaultDate.toString()) }
    var start by remember { mutableStateOf("18:00") }
    var end by remember { mutableStateOf("20:00") }
    var courseId by remember { mutableStateOf<Int?>(null) }
    var repeat by remember { mutableStateOf("Once") }

    val blockDate = runCatching { LocalDate.parse(dateText.trim()) }.getOrNull() // Checks for valid date or null
    val startTime = start.trim()
    val validStartTime = Regex("([01][0-9]|2[0-3]):[0-5][0-9]").matches(startTime) // Checks for valid time format
    val endTime = end.trim()
    val validEndTime = Regex("([01][0-9]|2[0-3]):[0-5][0-9]").matches(endTime)

    PlannerDialogShell(
        title = "New study block",
        confirmLabel = "Add block",
        confirmEnabled = title.isNotBlank() && blockDate != null && validStartTime && validEndTime,
        onDismiss = onDismiss,
        onConfirm = {
            if (blockDate != null && title.isNotBlank() && validStartTime && validEndTime) {
                planner.addBlock(
                    title = title.trim(),
                    courseId = courseId,
                    date = blockDate,
                    start = startTime,
                    end = endTime,
                    repeat = repeat,
                    notes = "",
                )
                onDismiss()
            }
        }
    ) {
        Field("Title", title, { title = it }, "e.g. Study: midterm review")
        Spacer(Modifier.height(12.dp))
        Field("Date", dateText, { dateText = it }, "YYYY-MM-DD")
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Column(Modifier.weight(1f)) { Field("Start", start, { start = it }, "HH:MM") }
            Column(Modifier.weight(1f)) { Field("End", end, { end = it }, "HH:MM") }
        }
        Spacer(Modifier.height(16.dp))
        SectionLabel("Class")
        Spacer(Modifier.height(8.dp))
        CourseChips(planner, selected = courseId) { courseId = it }
        Spacer(Modifier.height(16.dp))
        SectionLabel("Repeats")
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("Once", "Weekly", "Bi-weekly").forEach { option ->
                Chip(option, selected = repeat == option) { repeat = option }
            }
        }
    }
}

@Composable
fun NewClassDialog(
    planner: PlannerData,
    onDismiss: () -> Unit,
) {
    var name by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }
    var instructor by remember { mutableStateOf("") }
    var schedule by remember { mutableStateOf("") }

    PlannerDialogShell(
        title = "New class",
        confirmLabel = "Add class",
        confirmEnabled = name.isNotBlank() && code.isNotBlank(),
        onConfirm = {
            planner.addCourse(
                name = name.trim(),
                code = code.trim(),
                instructor = instructor.trim(),
                schedule = schedule.trim(),
            )
            onDismiss()
        },
        onDismiss = onDismiss,
    ) {
        Field("Class name", name, { name = it }, "e.g. Software Engineering")
        Spacer(Modifier.height(12.dp))
        Field("Class code", code, { code = it }, "e.g. SWE 2050")
        Spacer(Modifier.height(12.dp))
        Field("Instructor", instructor, { instructor = it }, "Optional")
        Spacer(Modifier.height(12.dp))
        Field("Meeting times", schedule, { schedule = it }, "e.g. Mon / Wed  10:00 - 11:15")
    }
}
