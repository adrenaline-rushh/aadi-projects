[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/XNhTFXWh)
# JournalApp
The Journal app maintains a list of events the user wishes to keep track of. The user shall enter the name of the event, date, start and end time of the event. The user can add new events to his journal, as well as update and delete existing events.

## Collaborators

1. Baibhav Padhy (f20201191@goa.bits-pilani.ac.in)
2. Aaditya Raghavan (f20201251@goa.bits-pilani.ac.in)

## How was the app developed

1. A new database has been created, using the class RoomDatabase.
   - Using this database, an entity has been defined. This entity represents a table. Each entry in the table represents a journal entry. The entity defines the ID (of type UUID), title (of type String), date (of type Date), start and end timings (of type Date) for each entry in the table.
   - Also, a DAO has been defined. In this DAO, functions to insert, update and delete data in the table have been defined. Queries to retrieve the entire table as well as retrieve single entries have also been defined.
   - We added a delete method in the DAO and the JournalRepository which executed the DAO's delete function. Following this, we also added a delete method in the EntryDetailsViewModel which calls the JournalRepository's delete method.
     
2. A repository has been created, with the same functions as the DAO mentioned above. Executor class been used in this repository to execute all functions in the main thread. 
3. In the nav graph, a Delete fragment has been added. In the AddEntryListAction fragment, 4 new arguments for passing start and end timings have been added. A nav host was added to the main activity. A RecyclerView was added in the EntryListFragment class, in the `OnCreateView` function. The `addEntryAction` was implemented to the floating action button. Then we implemented actions for the two Dialog fragments, DatePicker and TimePicker. On clicking the Date button, the DatePicker fragment pops up with initial date as current date. To implement this, we took a `Calendar` instance, extracted the Date, Month and Year, and set it as the default date. In the `onDateSetListener`,we directly changed the button text without having to send the data back to the EntryDetails fragment by invoking the ButtonView in the DialogFragment with the help of MainActivity. TimePicker fragments were set in a similar to set start and end timings. The only change was that we had to incorporate a check that the start time was before the end time. To implement this, we added a Package Private boolean variable that would check if the two timings are different. In the `SaveEntry` button's OnClickListener, we have implemented a check that would make sure that the start time comes before the end time, or else the entry would not be saved. If it is valid, then the entry is saved, the data is passed back to the EntryListFragment and shown in the RecyclerView with the help of JournalAdapter. On clicking any entry, the EntryDetailsFragment opens with the details of that entry in their respective fields and the user can change any field and save it again as desired.
4. To implement the Menu items, we created a Menu Resource File in the resources folder. We defined two resource files- one for EntryListFragment which contained the info menu item and one for the EntryDetailsFragment which contained the share and delete items. We created a DeleteFragment class which implements DeleteDialog pop-up. This fragment was added to the nav graph and an action from EntryDetailsFragment to this was created. In the Delete fragment, we called the onCreateDialog method which returns a new Alert dialog with two buttons- Yes and No, and a text asking the user to confirm its deletion. Code for navigation from EntryDetailsFragment to DeleteFragment was written in the MainActivity.
5. The SHARE functionality was implemented in the MainActivity using intents.
6. The INFO button was implemented in similar fashion as the Delete button. A separate fragment was created, added to the nav-graph and its corresponding action was also added from EntryDetailsFragment to InfoFragment. Its code is implemented in the MainActivity. In the onCreateDialog method, an alert dialog was created with one button and it give description about the app to the user.
7. TalkBack was able to identify all list entries. It could read each and every field on clicking. Initially while running the Accessibility scanner, suggestions were provided for List entries and the floating action button regarding colour contrast. We were able to make the requisite changes after which the scanner gave no suggestions.

## Other Details

It took approximately 25 hours to build this app, keeping all requirements in mind. Baibhav was responsible for coding, while Aaditya reviewed the code and made suggestions.
In terms of difficulty, we would rate the assignment 9/10. The assignment demanded bringing a lot of concepts to use.
