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

# US-04 Update Classes

As a **student**,

I want to **update my class schedule**,

So that **as I complete classes, drop classes, and take new classes my class list remains accurate**

## Acceptance Criteria

- User can delete classes from the class list
- User is prompted for confirmation before deletion
- User can update class times and dates
- User can add additional classes to class list
- System automatically handles updates to calendar based on class updates

# US-05 Schedule Time Blocks

As a **student**,

I want to **schedule a time slot**,

So that **my calendar is propagated with my daily schedule including study times, work periods, and miscellaneous events**

## Acceptance Criteria

- User can schedule an event time block
- User can enter event title
- User can link class or classes to event
- User can designate if event is single time or repeating weekly/bi-weekly ect

# US-06 Schedule Due Dates

As a **student**,

I want to **schedule a due date**,

So that **my calendar is propagated with my deadlines**

## Acceptance Criteria

- User can enter a due date and time
- Due date creates a visible indicator on calendar view
- Due dates can be assigned a class

# US-07 Receive Conflict Notification

As a **student**,

I want to **manage time conflicts**,

So that **I never double book myself to multiple commitments**

## Acceptance Criteria

- User is notified if they have multiple blocks scheduled during the same date and time
- System must prompt user if they would like to update schedule to accomodate
- System must generate automated time suggestion with no conflicts

# US-08 View My Calendar

As a **student**,

I want to **view my calendar**,

So that **I can see a visual representation of my scheduled commitments**

## Acceptance Criteria

- User will see standard calendar layout as the first screen after login
- Calendar will be populated with blocks representing scheduled times
- Calendar blocks will display relevant information such as title and time period

# US-09 View Block Details

As a **student**,

I want to **view all details associated with a time block**,

So that **I can see all linked classes, tests times, and notes**

## Acceptance Criteria

- Calendar blocks are interact-able with unique sub menu
- Sub menu details linked classes, resources, notes, and upcoming test/deadlines of linked classes

# US-10 Update Block Details

As a **student**,

I want to **update details associated with a time block**,

So that **I can adjust for changes or fix mistakes**

##  Acceptance Criteria

- Calendar block sub menus includes update options
- Notes field may be updated
- Time range and dates may be updated
- Classes may be linked or removed from a time block
- Time block may be fully deleted.

# US-11 Update Block Details

As a **student**,

I want to **receive notifications approaching and at due dates**,

So that **I can be reminded of important turn ins**

##  Acceptance Criteria

- User will be prompted to enable notifications
- User will be able to toggle notifications overall and for individual items
- If notifications are enabled, scheduled due dates will have auto configured notification settings
- Users will be able to adjust reminder intervals for specific deadlines, (1week, 1day, 1hour, etc)
- Notifications will be updated should due date be changed or deleted

# US-12 Save a resource

As a **student**,

I want to **save links and files in a resource list**,

So that **I can easily access study materials**

##  Acceptance Criteria

- User can save links and upload files
- User may name each link or file option
- Links and files are saved and accessible from a dedicated resources menu
- User can open links and files from the resources menu


# US-13 Associate resources with classes

As a **student**,

I want to **associate a resource with one or more classes**,

So that **I can organize study materials**

##  Acceptance Criteria

- User can associate a resource with zero or more classes
- User may sort the resource list based on associated classes
- Resources may appear more than once in the resource list based on sorting options
- Class associations are saved

# US-14 Access resources from calendar block

As a **student**,

I want to **access the resources associated with the classes linked to a calendar block**,

So that **I can quickly access useful study materials while I'm studying**

##  Acceptance Criteria

- User can access associated resources when calendar block is selected
- Resources for all classes associated to a block are displayed
- Blocks with no associated classes do not display resources

# US-15 Update resource list

As a **student**,

I want to **update my list of resources**,

So that **I can add. remove, or reorganize my study materials**

##  Acceptance Criteria

- User can edit a resource's title, link, file, or class associations
- User can removed resources from the list
- Deleted resources are removed from all class associations and resource access points 



