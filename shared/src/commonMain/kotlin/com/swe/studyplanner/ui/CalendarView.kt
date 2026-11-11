package com.swe.studyplanner.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.OutDateStyle
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.minusMonths
import com.kizitonwose.calendar.core.now
import com.kizitonwose.calendar.core.plusMonths
import com.swe.studyplanner.model.PlannerData
import com.swe.studyplanner.model.StudyBlock
import com.swe.studyplanner.model.TaskItem
import com.swe.studyplanner.theme.Gruvbox
import kotlinx.coroutines.launch
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.YearMonth

/**
 * Month grid with its title bar and weekday header. Sized by its parent so it
 * can sit beside the day panel (wide) or above it (narrow).
 */
@Composable
fun CalendarGrid(
    planner: PlannerData,
    selectedDate: LocalDate,
    onSelectDate: (LocalDate) -> Unit,
    compact: Boolean,
    modifier: Modifier = Modifier,
) {
    val currentMonth = remember { YearMonth(planner.today.year, planner.today.month) }
    val weekDays = remember { daysOfWeek() }
    val state = rememberCalendarState(
        startMonth = remember { currentMonth.minusMonths(24) },
        endMonth = remember { currentMonth.plusMonths(24) },
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = weekDays.first(),
        outDateStyle = OutDateStyle.EndOfGrid,
    )

    CalendarColumn(
        modifier = modifier,
        planner = planner,
        state = state,
        weekDays = weekDays,
        currentMonth = currentMonth,
        selectedDate = selectedDate,
        onSelectDate = onSelectDate,
        compact = compact,
    )
}

@Composable
private fun CalendarColumn(
    modifier: Modifier,
    planner: PlannerData,
    state: CalendarState,
    weekDays: List<DayOfWeek>,
    currentMonth: YearMonth,
    selectedDate: LocalDate,
    onSelectDate: (LocalDate) -> Unit,
    compact: Boolean,
) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = if (compact) 16.dp else 28.dp, end = if (compact) 16.dp else 20.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = state.firstVisibleMonth.yearMonth.label(),
                style = if (compact) MaterialTheme.typography.titleMedium else MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = Gruvbox.Fg,
            )
            Spacer(Modifier.weight(1f))
            TextButton(onClick = { scope.launch { state.animateScrollToMonth(currentMonth) } }) {
                Text("Today", color = Gruvbox.Fg2)
            }
            IconButton(onClick = {
                scope.launch { state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.minusMonths(1)) }
            }) {
                Icon(Icons.Default.KeyboardArrowLeft, "Previous month", tint = Gruvbox.Fg2)
            }
            IconButton(onClick = {
                scope.launch { state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.plusMonths(1)) }
            }) {
                Icon(Icons.Default.KeyboardArrowRight, "Next month", tint = Gruvbox.Fg2)
            }
        }

        Row(Modifier.fillMaxWidth().padding(bottom = 6.dp)) {
            weekDays.forEach { day ->
                Text(
                    text = day.name.lowercase().take(3).replaceFirstChar { it.uppercase() },
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelSmall,
                    color = Gruvbox.Gray,
                )
            }
        }

        BoxWithConstraints(Modifier.fillMaxWidth().weight(1f)) {
            val cellHeight = (maxHeight - 4.dp) / 6
            HorizontalCalendar(
                modifier = Modifier.fillMaxSize(),
                state = state,
                dayContent = { day ->
                    DayCell(
                        day = day,
                        height = cellHeight,
                        selectedDate = selectedDate,
                        planner = planner,
                        onSelect = onSelectDate,
                    )
                },
            )
        }
        Spacer(Modifier.height(10.dp))
    }
}

@Composable
private fun DayCell(
    day: CalendarDay,
    height: Dp,
    selectedDate: LocalDate,
    planner: PlannerData,
    onSelect: (LocalDate) -> Unit,
) {
    val isSelected = day.date == selectedDate
    val isToday = day.date == planner.today
    val inMonth = day.position == DayPosition.MonthDate

    val markers = (planner.blocksOn(day.date).mapNotNull { planner.course(it.courseId)?.color } +
        planner.tasksDueOn(day.date).mapNotNull { planner.course(it.courseId)?.color })
        .distinct()
        .take(3)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .padding(3.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(
                when {
                    isSelected -> Gruvbox.Bg2
                    isToday -> Gruvbox.Bg1
                    else -> Color.Transparent
                },
            )
            .border(
                width = if (isToday && !isSelected) 1.dp else 0.dp,
                color = if (isToday && !isSelected) Gruvbox.Bg3 else Color.Transparent,
                shape = RoundedCornerShape(8.dp),
            )
            .clickable { onSelect(day.date) },
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            modifier = Modifier.padding(top = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = day.date.day.toString(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal,
                color = when {
                    !inMonth -> Gruvbox.Bg4
                    isSelected -> Gruvbox.Fg
                    else -> Gruvbox.Fg1
                },
            )
            Spacer(Modifier.height(4.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                markers.forEach { color ->
                    Box(Modifier.size(5.dp).clip(CircleShape).background(color))
                }
            }
        }
    }
}

/**
 * Details for the selected day. Sits beside the calendar at full window height,
 * or under it in the compact layout.
 */
@Composable
fun DayPanel(
    planner: PlannerData,
    date: LocalDate,
    onNewTask: () -> Unit,
    onNewBlock: () -> Unit,
    compact: Boolean,
    modifier: Modifier = Modifier,
) {
    val blocks = planner.blocksOn(date)
    val tasks = planner.tasksDueOn(date)

    Column(
        modifier = modifier
            .background(Gruvbox.Bg0)
            .verticalScroll(rememberScrollState())
            .padding(if (compact) 16.dp else 24.dp),
    ) {
        SectionLabel(if (date == planner.today) "Today" else date.dayOfWeek.name.lowercase())
        Spacer(Modifier.height(2.dp))
        Text(
            text = date.longLabel(),
            style = if (compact) MaterialTheme.typography.titleMedium else MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = Gruvbox.Fg,
        )
        Spacer(Modifier.height(14.dp))

        // Visual only: the dialogs open, but nothing is stored.
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(onClick = onNewTask) {
                Icon(Icons.Default.Add, null, Modifier.size(16.dp))
                Spacer(Modifier.width(6.dp))
                Text("Task")
            }
            OutlinedButton(onClick = onNewBlock) {
                Icon(Icons.Default.Add, null, Modifier.size(16.dp))
                Spacer(Modifier.width(6.dp))
                Text("Block")
            }
        }

        Spacer(Modifier.height(18.dp))
        HorizontalDivider(color = Gruvbox.Bg1)
        Spacer(Modifier.height(16.dp))

        SectionLabel("Study blocks")
        Spacer(Modifier.height(10.dp))
        if (blocks.isEmpty()) {
            Text("Nothing scheduled.", style = MaterialTheme.typography.bodySmall, color = Gruvbox.Gray)
        } else {
            blocks.forEach { block ->
                BlockEntry(block, planner)
                Spacer(Modifier.height(8.dp))
            }
        }

        Spacer(Modifier.height(18.dp))
        SectionLabel("Due")
        Spacer(Modifier.height(10.dp))
        if (tasks.isEmpty()) {
            Text("No deadlines.", style = MaterialTheme.typography.bodySmall, color = Gruvbox.Gray)
        } else {
            tasks.forEach { task ->
                TaskEntry(task, planner)
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun BlockEntry(block: StudyBlock, planner: PlannerData) {
    val course = planner.course(block.courseId)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Gruvbox.Bg1)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            Modifier
                .width(3.dp)
                .height(34.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(course?.color ?: Gruvbox.Gray),
        )
        Spacer(Modifier.width(10.dp))
        Column(Modifier.weight(1f)) {
            Text(block.title, style = MaterialTheme.typography.bodyMedium, color = Gruvbox.Fg)
            Text(
                text = "${block.start} - ${block.end}  ·  ${block.repeat}",
                style = MaterialTheme.typography.labelSmall,
                color = Gruvbox.Fg3,
                fontFamily = FontFamily.Monospace,
            )
        }
        CourseTag(course)
    }
}

@Composable
private fun TaskEntry(task: TaskItem, planner: PlannerData) {
    val course = planner.course(task.courseId)
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Dot(course?.color ?: Gruvbox.Gray, 8)
        Spacer(Modifier.width(10.dp))
        Column(Modifier.weight(1f)) {
            Text(task.title, style = MaterialTheme.typography.bodyMedium, color = Gruvbox.Fg)
            Text(
                text = "${task.dueTime}  ·  ${course?.code ?: "No class"}",
                style = MaterialTheme.typography.labelSmall,
                color = Gruvbox.Fg3,
            )
        }
        PriorityBadge(task.priority)
    }
}
