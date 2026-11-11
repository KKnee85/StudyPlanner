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

private val UI_SCALE = System.getenv("STUDYPLANNER_UI_SCALE")?.toFloatOrNull() ?: 1f

fun main() = application {
    val state = rememberWindowState(
        size = DpSize(1280.dp, 900.dp),
        position = WindowPosition(Alignment.Center),
    )
    Window(onCloseRequest = ::exitApplication, state = state, title = "StudyPlanner") {
        window.minimumSize = Dimension(520, 460)

        LaunchedEffect(Unit) {

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
