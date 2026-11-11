import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm")
    kotlin("plugin.compose")
    id("org.jetbrains.compose")
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_25)
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

dependencies {
    implementation(project(":shared"))
    implementation(compose.desktop.currentOs)
    implementation(compose.preview)
    implementation(libs.kotlinx.coroutines.swing)
    implementation(libs.logback.classic)
    implementation(libs.gson)
}

compose.desktop {
    application {
        mainClass = "com.swe.studyplanner.MainKt"
    }
}
