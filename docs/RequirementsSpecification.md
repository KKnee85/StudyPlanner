# Agile Software Requirements Specification

## Functional Requirements
### FR1 Class Creation
Users shall be able to create class groups for defining schedule blocks
### FR2 Scheduling
Users shall be able to schedule study blocks, due dates, and test times with options to associate each to 0 or more classes. Scheduled blocks must have configurable titles, time slots, and notification settings. Scheduling conflicts must be automatically recognized producing a prompt to update or ignore.  
### FR3 Calendar View
Users shall be able to view all scheduled events in an automatically updated calendar format. Each scheduled block will be interact-able to update time tables and leave notes. 
### FR4 Notifications
The system will automatically notify users of upcoming due dates and test times. Users shall be able to schedule and configure automatic notification reminders leading up to and during scheduled blocks
### FR5 Resource List
Students shall be able to store links and files and attach them to 1 or more classes. Resources must be accessible from their own dedicated menu and from calendar time block that share any associated classes. 
## Non-Functional Requirements
### NFR1 Usability 
A new user must be able to allocate a time block within a maximum of 3 menus and automatically see it appear in a calendar
### NFR2 Reliability
Scheduled time blocks must be non-volatile and accessible at all times with multiple layers of redundancy 
### NFR3 Performance
Calendar with all scheduled blocks must load pseudo-simultaneously with negligible load times <1s
### NFR4 Maintainability
Software updates must ship with zero user downtime
### NFR5 Security
Any user information such as usernames, passwords, or class schedules must be encrypted
