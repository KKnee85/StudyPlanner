package com.swe.studyplanner.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kizitonwose.calendar.core.plusDays
import com.swe.studyplanner.model.PlannerData
import com.swe.studyplanner.theme.Gruvbox
import kotlin.math.roundToInt

@Composable
fun Sidebar(
    current: AppSection,
    onSelect: (AppSection) -> Unit,
    planner: PlannerData,
    compact: Boolean,
    zoom: Float,
    onZoomIn: () -> Unit,
    onZoomOut: () -> Unit,
) {
    Column(
        modifier = Modifier
            .width(if (compact) 76.dp else 236.dp)
            .fillMaxHeight()
            .background(Gruvbox.Bg0)
            .padding(horizontal = if (compact) 8.dp else 14.dp, vertical = 18.dp),
    ) {
        Brand(compact)
        Spacer(Modifier.size(if (compact) 20.dp else 28.dp))

        if (!compact) {
            SectionLabel("Planner", Modifier.padding(start = 10.dp))
            Spacer(Modifier.size(8.dp))
        }
        AppSection.entries.forEach { section ->
            NavItem(section, selected = current == section, compact = compact) { onSelect(section) }
            Spacer(Modifier.size(2.dp))
        }

        Spacer(Modifier.weight(1f))
        if (!compact) {
            DueThisWeekCard(planner)
            Spacer(Modifier.size(14.dp))
        }
        ZoomControl(compact, zoom, onZoomIn, onZoomOut)
        if (!compact) {
            Spacer(Modifier.size(14.dp))
            UserRow()
        }
    }
}

@Composable
private fun Brand(compact: Boolean) {
    Row(
        modifier = if (compact) Modifier.fillMaxWidth() else Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (compact) androidx.compose.foundation.layout.Arrangement.Center else androidx.compose.foundation.layout.Arrangement.Start,
    ) {
        Box(
            modifier = Modifier
                .size(if (compact) 32.dp else 36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Gruvbox.Green),
            contentAlignment = Alignment.Center,
        ) {
            Text("SP", color = Gruvbox.BgHard, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
        if (!compact) {
            Spacer(Modifier.width(10.dp))
            Column {
                Text(
                    text = "StudyPlanner",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = Gruvbox.Fg,
                )
                Text(
                    text = "Fall 2026",
                    style = MaterialTheme.typography.labelSmall,
                    color = Gruvbox.Gray,
                )
            }
        }
    }
}

@Composable
private fun NavItem(section: AppSection, selected: Boolean, compact: Boolean, onClick: () -> Unit) {
    val foreground = if (selected) Gruvbox.Green else Gruvbox.Fg2
    val shape = RoundedCornerShape(8.dp)

    if (compact) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(38.dp)
                .clip(shape)
                .background(if (selected) Gruvbox.Bg2 else Color.Transparent)
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center,
        ) {
            Icon(section.icon, contentDescription = section.label, tint = foreground, modifier = Modifier.size(19.dp))
        }
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape)
                .background(if (selected) Gruvbox.Bg2 else Color.Transparent)
                .clickable(onClick = onClick)
                .padding(horizontal = 10.dp, vertical = 9.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(section.icon, contentDescription = null, tint = foreground, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(10.dp))
            Text(section.label, style = MaterialTheme.typography.bodyMedium, color = foreground)
            if (selected) {
                Spacer(Modifier.weight(1f))
                Box(Modifier.size(6.dp).clip(CircleShape).background(Gruvbox.Green))
            }
        }
    }
}

@Composable
private fun DueThisWeekCard(planner: PlannerData) {
    val upcoming = planner.tasks.count {
        !it.done && it.dueDate >= planner.today && it.dueDate <= planner.today.plusDays(7)
    }
    Surface(color = Gruvbox.Bg1, shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(12.dp)) {
            Text("THIS WEEK", style = MaterialTheme.typography.labelSmall, color = Gruvbox.Gray)
            Spacer(Modifier.size(4.dp))
            Text(
                text = "$upcoming due",
                style = MaterialTheme.typography.titleMedium,
                color = if (upcoming > 0) Gruvbox.Yellow else Gruvbox.Fg2,
            )
            Text(
                text = "${planner.blocks.size} scheduled blocks",
                style = MaterialTheme.typography.labelSmall,
                color = Gruvbox.Gray,
            )
        }
    }
}

@Composable
private fun ZoomControl(compact: Boolean, zoom: Float, onZoomIn: () -> Unit, onZoomOut: () -> Unit) {
    val percent = "${(zoom * 100).roundToInt()}%"
    if (compact) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            ZoomButton("+", onZoomIn)
            Spacer(Modifier.height(4.dp))
            Text(percent, style = MaterialTheme.typography.labelSmall, color = Gruvbox.Fg3, textAlign = TextAlign.Center)
            Spacer(Modifier.height(4.dp))
            ZoomButton("−", onZoomOut)
        }
    } else {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("UI SCALE", style = MaterialTheme.typography.labelSmall, color = Gruvbox.Gray, modifier = Modifier.weight(1f))
            ZoomButton("−", onZoomOut)
            Text(
                text = percent,
                style = MaterialTheme.typography.labelSmall,
                color = Gruvbox.Fg2,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(56.dp),
                softWrap = false,
            )
            ZoomButton("+", onZoomIn)
        }
    }
}

@Composable
private fun ZoomButton(symbol: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(Gruvbox.Bg2)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(symbol, style = MaterialTheme.typography.labelMedium, color = Gruvbox.Fg1)
    }
}

@Composable
private fun UserRow() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier.size(30.dp).clip(CircleShape).background(Gruvbox.Bg2),
            contentAlignment = Alignment.Center,
        ) {
            Text("MH", style = MaterialTheme.typography.labelSmall, color = Gruvbox.Fg2)
        }
        Spacer(Modifier.width(10.dp))
        Column {
            Text("Student Name", style = MaterialTheme.typography.bodySmall, color = Gruvbox.Fg1)
            Text("Student", style = MaterialTheme.typography.labelSmall, color = Gruvbox.Gray)
        }
    }
}
