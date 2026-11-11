package com.swe.studyplanner.ui

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.swe.studyplanner.model.PlannerData
import com.swe.studyplanner.model.ResourceLink
import com.swe.studyplanner.theme.Gruvbox

@Composable
fun ResourcesView(planner: PlannerData, compact: Boolean) {
    Column(Modifier.fillMaxSize().padding(horizontal = if (compact) 16.dp else 28.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(Modifier.weight(1f))
            Text(
                text = "Links and files attached to classes",
                style = MaterialTheme.typography.labelSmall,
                color = Gruvbox.Gray,
            )
        }
        Spacer(Modifier.height(14.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(planner.resources, key = { it.id }) { resource ->
                ResourceCard(resource, planner, compact)
            }
        }
    }
}

@Composable
private fun ResourceCard(resource: ResourceLink, planner: PlannerData, compact: Boolean) {
    val courses = resource.courseIds.mapNotNull { planner.course(it) }

    Surface(color = Gruvbox.Bg0, shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Gruvbox.Bg1),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = if (resource.kind == "File") Icons.Outlined.Description else Icons.Outlined.Link,
                    contentDescription = resource.kind,
                    tint = if (resource.kind == "File") Gruvbox.Orange else Gruvbox.Blue,
                    modifier = Modifier.size(18.dp),
                )
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    text = resource.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Gruvbox.Fg,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = resource.location,
                    style = MaterialTheme.typography.labelSmall,
                    color = Gruvbox.Fg3,
                    fontFamily = FontFamily.Monospace,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            if (!compact) {
                Spacer(Modifier.width(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    courses.forEach { CourseTag(it) }
                }
            }
        }
    }
}
