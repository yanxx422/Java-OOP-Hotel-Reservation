# Java-OOP-Hotel-Reservation

## Introduction
This is a hotel reservation application that allows customers to find and book a hotel room based on room availability.

Relative knowledge includes: design classes using OOP, organize and process data with collections, and use common Java types.

## Main Components of the App
- CLI for the User Interface. Command Line Interface is used so that the user can enter commands to search for available rooms, book rooms, and so on.
- Java code. The second main component is the Java code itself—this is where business logic for the app is located.
- Java collections for in-memory storage of the data we need for the app, such as the users' names, room availability, and so on.
## Structure
 The following layer is used to support modularization and decoupling. 
![hotelreservation01](https://user-images.githubusercontent.com/24535066/160192694-d7b19a26-b37b-4bbf-91fc-76dadfcedb6c.png)

## Functionality
### Admin Mode
In admin mode, you can:
- Displaying all customers accounts.
- Viewing all of the rooms in the hotel.
- Viewing all of the hotel reservations.
- Adding a room to the hotel application.
### User Mode
In user mode, you can:
- Creating a customer account. The user needs to first create a customer account before they can create a reservation.
- Searching for rooms. The app allows the user to search for available rooms based on provided checkin and checkout dates. If the application has available rooms for the specified date range, a list of the corresponding rooms will be displayed to the user for choosing.
- Booking a room. Once the user has chosen a room, the app will allow them to book the room and create a reservation.

- Viewing reservations. After booking a room, the app allows customers to view a list of all their reservations. The user can only view the reservations associated with his/her account.

# Getting Start
Main class is in `ui/UIMenu.java`.

# Further Info
This is the first capstone project of Udacity's java programming nanodegree. 

