# Kotlin Multiplatform project

This project targets Desktop (JVM) and is built with [Gradle](https://gradle.org) and
[Compose Multiplatform](https://kotlinlang.org/compose-multiplatform/). It requires **JDK 21**.

- [/desktopApp](./desktopApp) contains the desktop (JVM) application.
- [/shared](./shared) holds the code shared across your applications — Compose UI,
  business logic, and platform-specific implementations.

## Building

```sh
./gradlew build
```

On Windows use `gradlew.bat build`. The Gradle wrapper downloads the pinned Gradle version on
first use, so no separate installation is required.

## Running

```sh
./gradlew :desktopApp:run
```

## Testing

```sh
./gradlew test                       # all modules
./gradlew :shared:jvmTest            # a single test suite
```

## Source layout

Source sets follow the standard Kotlin Multiplatform conventions:

| Path | Contains |
|---|---|
| `shared/src/commonMain/kotlin` | Code compiled for every platform (Compose UI, business logic) |
| `shared/src/jvmMain/kotlin` | JVM-only code — `actual` implementations for `expect` declarations |
| `shared/src/commonMain/composeResources` | Images, fonts, strings and other Compose resources |
| `shared/src/commonTest/kotlin` | Tests that run on every platform |
| `shared/src/jvmTest/kotlin` | JVM-only tests |
| `desktopApp/src/main/kotlin` | Desktop entry point (`main.kt`) |
| `desktopApp/src/main/resources` | JVM resources such as `logback.xml` |

Dependency versions are declared in [gradle/libs.versions.toml](./gradle/libs.versions.toml).

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html),
[Compose Multiplatform](https://kotlinlang.org/compose-multiplatform/), and [Gradle](https://docs.gradle.org).
