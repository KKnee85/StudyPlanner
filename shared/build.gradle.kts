import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("multiplatform")
    kotlin("plugin.compose")
    id("org.jetbrains.compose")
}

kotlin {
    jvm {
        // The project targets JDK 25 everywhere, so bytecode does not depend on
        // whichever JDK happens to run Gradle. Both halves have to be pinned:
        // Kotlin through jvmTarget, Java through targetCompatibility, otherwise
        // a Gradle run on an older JDK trips the "Inconsistent JVM-target
        // compatibility" check.
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_25)
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.preview)
            implementation(libs.compose.hot.reload.runtime.api)
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.kizitonwose.calendar.compose)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

compose.resources {
    packageOfResClass = "com.swe.studyplanner.resources"
    // Generate public accessors so other modules (and previews) can use Res.*
    publicResClass = true
}
