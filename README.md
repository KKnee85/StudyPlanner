# StudyPlanner 

## Team Members
- Kyle Knee
- Mark Hancock
- Evan Hayes

## Problem Statement
We propose to create a study planner tool to assist students in keeping track of due dates, test times, and planned study sessions. The goal of this project is to make scheduling study time easier by using intuitive visual tools. 

## Stakeholder Analysis
This application will be used by students looking to manage time more efficiently and organize their academic workload by scheduling out their study times and deadlines. This application will focus on the needs students, however, it may be used by many stakeholders as a general purpose scheduling tool. 

## Key Features
The application in question will have:
- Calendar with persistent weekly schedule
- Ability to schedule times for study, project work, and other
- Ability to set due dates and test times
- Functionality to send notification reminders of due dates and test times
- List of resources for helpful tools

## Technologies Used
- Kotlin
- Kotlin Multiplatform
- Compose Multiplatform (Desktop)
- Gradle

## Scrum Process

This project follows Scrum practices using:

- GitHub Issues
- GitHub Projects
- Pull Requests
- Sprint Reviews
- Sprint Retrospectives

## Repository Structure

- /docs       Scrum documents
- /src        Gradle build root and application source code

<pre>
StudyPlanner/
│
├── README.md
├── docs
│   ├── ProductBacklog.md
│   ├── ProductVision.md
│   ├── RequirementsSpecification.md
│   ├── RiskRegister.md
│   └── Sprint1
│       ├── Planning.md
│       ├── Retrospective.md
│       └── Review.md
└── src
    ├── build.gradle.kts
    ├── settings.gradle.kts
    ├── gradle.properties
    ├── gradlew
    ├── gradlew.bat
    ├── README.md
    ├── gradle
    │   ├── libs.versions.toml
    │   └── wrapper
    ├── desktopApp
    │   ├── build.gradle.kts
    │   └── src
    │       └── main
    │           ├── kotlin
    │           └── resources
    └── shared
        ├── build.gradle.kts
        └── src
            ├── commonMain
            │   ├── kotlin
            │   └── composeResources
            ├── commonTest
            └── jvmMain
</pre>
