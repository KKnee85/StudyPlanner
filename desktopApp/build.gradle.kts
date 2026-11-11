import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm")
    kotlin("plugin.compose")
    id("org.jetbrains.compose")
}

// The project targets JDK 25 everywhere, so bytecode does not depend on
// whichever JDK happens to run Gradle.
kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_25)
    }
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
        // Compose Desktop draws through AWT. JDK 21's AWT never processes the
        // configure event a tiling Wayland compositor sends, so a resized
        // window keeps painting at its old size instead of reflowing. The
        // project requires JDK 25, so the app inherits Gradle's JVM by default
        // and this behaves correctly without extra configuration.
        mainClass = "com.swe.studyplanner.MainKt"
    }
}
