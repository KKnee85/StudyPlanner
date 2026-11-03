plugins {
    kotlin("jvm")
    kotlin("plugin.compose")
    id("org.jetbrains.compose")
}

dependencies {
    implementation(project(":shared"))
    implementation(compose.desktop.currentOs)
    implementation(compose.preview)
    implementation(libs.kotlinx.coroutines.swing)
    implementation(libs.logback.classic)
}

compose.desktop {
    application {
        mainClass = "com.swe.studyplanner.MainKt"
    }
}
