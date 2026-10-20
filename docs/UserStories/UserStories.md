# US-01 Create Account

As a **student**,

I want to **create an account**,

So that I can **use the system**.

## Acceptance Criteria

- User can enter name
- User can enter email
- User can choose password
- Password is encrypted
- User account is saved


## Labels

backend
frontend
high-priority
unassigned-sprint

# US-02 Login/Logout

As a **student**,

I want to **log in and out of my account**,

So that **my preferences are saved, and my information is secure, across multiple devices**.

## Acceptance Criteria

- User entering existing valid login for account can have access to system and account information
- Once a user logs out and back in successfully, their information remains the same
- Account information changed via one device should propagate to other devices and logins for that same account
- Entering invalid username not already in system will not allow access to system
- Entering invalid password for username existing in system will not allow access to system


## Labels

backend
frontend
high-priority
unassigned-sprint

# US-03 Create Classes

As a **student**,

I want to **input my class schedule**,

So that **my class times are saved and visible in the calendar with the relevant related information**

## Acceptance Criteria

- User can enter class name
- User can enter class ID
- User can enter class times and dates

# US-04 Schedule Time Blocks

As a **student**,

I want to **schedule a time slot**,

So that **my calendar is propagated with my daily schedule including study times, work periods, and miscellaneous events**

## Acceptance Criteria

- User can schedule an event time block
- User can enter event title
- User can link class or classes to event
- User can designate if event is single time or repeating weekly/bi-weekly ect

# US-05 Schedule Due Dates

As a **student**,

I want to **schedule a due date**,

So that **my calendar is propagated with my deadlines**

## Acceptance Criteria

- User can enter a due date and time
- Due date creates a visible indicator on calendar view
- Due dates can be assigned a class

# US-06 Receive Conflict Notification

As a **student**,

I want to **manage time conflicts**,

So that **I never double book myself to multiple commitments**

## Acceptance Criteria

- User is notified if they have multiple blocks scheduled during the same date and time
- System must prompt user if they would like to update schedule to accomodate
- System must generate automated time suggestion with no conflicts

# US-07 View My Calendar

As a **student**,

I want to **view my calendar**,

So that **I can see a visual representation of my scheduled commitments**

## Acceptance Criteria

- User will see standard calendar layout as the first screen after login
- Calendar will be populated with blocks representing scheduled times
- Calendar blocks will display relevant information such as title and time period

# US-08 View Block Details

As a **student**,

I want to **view all details associated with a time block**,

So that **I can see all linked classes, tests times, and notes**

## Acceptance Criteria

- Calendar blocks are interact-able with unique sub menu
- Sub menu details linked classes, resources, notes, and upcoming test/deadlines of linked classes

# US-09 Update Block Details

As a **student**,

I want to **Update details associated with a time block**,

So that **I can adjust for changes or fix mistakes**

##  Acceptance Criteria

- Calendar block sub menus includes update options
- Notes field may be updated
- Time range and dates may be updated
- Classes may be linked or removed from a time block
- Time block may be fully deleted.

