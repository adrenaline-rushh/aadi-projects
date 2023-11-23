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
     
2. A repository has been created, with the same functions as the DAO mentioned above. Executor class been used in this repository to execute all functions in the main thread. 
3. In the nav graph, a Delete fragment has been added. In the AddEntryListAction fragment, 4 new arguments for passing start and end timings have been added.


## Other Details

It took approximately 25 hours to build this app, keeping all requirements in mind. Baibhav was responsible for coding, while Aaditya reviewed the code and made suggestions.
In terms of difficulty, we would rate the assignment 9/10. The assignment demanded bringing a lot of concepts to use.
