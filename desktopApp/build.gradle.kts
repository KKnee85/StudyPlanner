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

        // Compose Desktop draws through AWT. Under a tiling Wayland compositor,
        // JDK 21's AWT never processes the configure event the compositor sends,
        // so a resized window keeps painting at its old size instead of
        // reflowing (and the compact layout never triggers). A newer JDK does.
        //
        // Gradle itself stays on JDK 21: the Kotlin compiler cannot parse
        // nixpkgs' four-part version string ("25.0.5.0.3") and fails at
        // configuration time, so only the app's JVM is pointed elsewhere.
        //
        //   ~/.gradle/gradle.properties:
        //     appJdk=/path/to/jdk-25
        //
        // Unset on machines where JDK 21 behaves, which keeps the build portable.
        providers.gradleProperty("appJdk").orNull?.let { javaHome = it }
    }
}
