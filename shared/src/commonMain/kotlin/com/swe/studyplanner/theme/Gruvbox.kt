package com.swe.studyplanner.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Gruvbox Dark Hard palette.
 *
 * The "hard" variant uses #1d2021 as the background instead of #282828, which
 * is the point of it: more contrast against the brighter foreground tones.
 */
object Gruvbox {
    val BgHard = Color(0xFF1D2021)
    val Bg0 = Color(0xFF282828)
    val Bg1 = Color(0xFF3C3836)
    val Bg2 = Color(0xFF504945)
    val Bg3 = Color(0xFF665C54)
    val Bg4 = Color(0xFF7C6F64)

    val Fg = Color(0xFFEBDBB2)
    val Fg1 = Color(0xFFD5C4A1)
    val Fg2 = Color(0xFFBDAE93)
    val Fg3 = Color(0xFFA89984)
    val Gray = Color(0xFF928374)

    val Red = Color(0xFFFB4934)
    val Green = Color(0xFFB8BB26)
    val Yellow = Color(0xFFFABD2F)
    val Blue = Color(0xFF83A598)
    val Purple = Color(0xFFD3869B)
    val Aqua = Color(0xFF8EC07C)
    val Orange = Color(0xFFFE8019)

    /** Accent colours handed out to classes, in this order. */
    val CourseColors = listOf(Blue, Aqua, Yellow, Purple, Orange, Red, Green)
}

private val DarkScheme = darkColorScheme(
    primary = Gruvbox.Green,
    onPrimary = Gruvbox.BgHard,
    primaryContainer = Gruvbox.Bg2,
    onPrimaryContainer = Gruvbox.Green,
    secondary = Gruvbox.Blue,
    onSecondary = Gruvbox.BgHard,
    secondaryContainer = Gruvbox.Bg1,
    onSecondaryContainer = Gruvbox.Blue,
    tertiary = Gruvbox.Purple,
    onTertiary = Gruvbox.BgHard,
    tertiaryContainer = Gruvbox.Bg1,
    onTertiaryContainer = Gruvbox.Purple,
    background = Gruvbox.BgHard,
    onBackground = Gruvbox.Fg,
    surface = Gruvbox.Bg0,
    onSurface = Gruvbox.Fg,
    surfaceVariant = Gruvbox.Bg1,
    onSurfaceVariant = Gruvbox.Fg2,
    surfaceTint = Gruvbox.Green,
    inverseSurface = Gruvbox.Fg,
    inverseOnSurface = Gruvbox.BgHard,
    error = Gruvbox.Red,
    onError = Gruvbox.BgHard,
    errorContainer = Gruvbox.Bg1,
    onErrorContainer = Gruvbox.Red,
    outline = Gruvbox.Bg3,
    outlineVariant = Gruvbox.Bg2,
    scrim = Color(0xCC000000),
)

@Composable
fun StudyPlannerTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = DarkScheme, content = content)
}
