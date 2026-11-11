package com.swe.studyplanner.ui

import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.YearMonth

fun Month.label(): String = name.lowercase().replaceFirstChar { it.uppercase() }

fun Month.shortLabel(): String = label().take(3)

/** "Monday, November 10" */
fun LocalDate.longLabel(): String =
    "${dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }}, ${month.label()} $day"

/** "Nov 10" */
fun LocalDate.shortLabel(): String = "${month.shortLabel()} $day"

/** "November 2026" */
fun YearMonth.label(): String = "${month.label()} $year"

fun LocalDate.isSameDay(other: LocalDate): Boolean = this == other
