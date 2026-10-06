package com.swe.studyplanner

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
