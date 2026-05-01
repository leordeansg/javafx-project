# Gabe’s Garage Manager (GGM)

A JavaFX desktop application for tracking car service history, expenses, and reminders in one place.

## Overview

GGM solves a simple problem: car data is usually scattered across notes, apps, and memory. This puts everything in one place with a clean UI and structured data.

Built as a single user desktop app with local storage using SQLite.

## Features

* Add, update, delete cars
* Track service history per car
* Log expenses and modifications
* Create and manage reminders
* Dashboard with key stats
* Input validation and error handling
* Safe delete with confirmation

## Tech Stack

* Java
* JavaFX
* SQLite (JDBC)
* VS Code

## Architecture

The application follows a layered structure:

* Presentation layer: JavaFX (FXML + Controllers)
* Domain layer: core models and validation
* Data access layer: repository pattern for database operations
* Persistence layer: SQLite database

This keeps UI, logic, and database separate and maintainable.

## How to Run

### Requirements

* Java JDK 25
* JavaFX SDK

### Setup

1. Clone the repository
2. Open the project in VS Code
3. Configure JavaFX path in your environment (update if needed)
4. Run `GGM.java`

Note: JavaFX SDK is not included. You must install it locally and configure the path.

## Database

* Uses SQLite
* Database file is created automatically on first run
* No setup required

## Screenshots

### Home
<p align="center">
  <img src="docs/home.png" width="700"/>
</p>

### Cars
<p align="center">
  <img src="docs/cars.png" width="700"/>
</p>

### Reminders
<p align="center">
  <img src="docs/reminders.png" width="700"/>
</p>

### Settings
<p align="center">
  <img src="docs/settings.png" width="700"/>
</p>

## What I Learned

* Structuring a real application using layers
* Repository pattern for clean database access
* JavaFX UI design and state handling
* Input validation and protecting data integrity
* Working with SQLite via JDBC

## Future Improvements

* Multi user support
* Notifications for reminders
* Cloud backup and sync
* Data export (CSV or PDF)
* Data visualisation (charts)

## Notes

This project was built as part of a university module but designed to reflect a real world application with practical use.
