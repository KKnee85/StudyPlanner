package com.swe.studyplanner

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import kotlinx.coroutines.delay
import java.awt.Dimension
import java.awt.Toolkit

// AWT reports every display as 96 DPI / 1x. On a compositor that scales the
// whole window (e.g. this machine's 1.5x), Compose then draws everything a third
// too small. Set STUDYPLANNER_UI_SCALE to the display scale to compensate; leave
// it unset on a normal 1x screen.
private val UI_SCALE = System.getenv("STUDYPLANNER_UI_SCALE")?.toFloatOrNull() ?: 1f

fun main() = application {
    val state = rememberWindowState(
        size = DpSize(1280.dp, 900.dp),
        position = WindowPosition(Alignment.Center),
    )
    Window(onCloseRequest = ::exitApplication, state = state, title = "StudyPlanner") {
        // Low enough that the responsive layout can reach its compact mode.
        window.minimumSize = Dimension(520, 460)

        LaunchedEffect(Unit) {
            // Java's AWT never adopts the size a tiling compositor assigns, so
            // the window would otherwise render at 1280x900 and leave the rest
            // of the screen unpainted. Asking for (almost) the screen size once
            // the window is up makes the app claim the workspace it was given.
            delay(700)
            val screen = Toolkit.getDefaultToolkit().screenSize
            window.setSize(
                Dimension(
                    (screen.width * 0.96).toInt(),
                    (screen.height * 0.94).toInt(),
                ),
            )
        }

        CompositionLocalProvider(
            LocalDensity provides Density(UI_SCALE, fontScale = UI_SCALE)
        ) {
            App()
        }
    }
}
