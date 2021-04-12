# Library Management System
> Simple application that allows you to manage the library 

## General info
This is my first project that was created to expand my portfolio.
It allows you to add readers and books, borrow and return books, and view records in the database with the ability to edit 

## Screenshots
![Alt text](./src/main/resources/screenshots/ss1.png?raw=true)

![Alt text](./src/main/resources/screenshots/ss2.png?raw=true)

## Technologies and tools
* Java
* JavaFX
* MySQL
* MySQL WorkBench
* JDBC
* IntelliJ IDEA
* SceneBuilder
* Maven

## About Files
* /Database: Contains the Exported Database Schema+Data and Database properties
* src/main Contains JAVA source codes and resources 

## Instructions
1) Clone the Project using link https://github.com/MarcinCzekaj00/Library-Management-System.git or Download the zip

2) Open project in IntelliJ
- File->New->Project from Version Control then past clone link
- File->Open then find and open downloaded zip

3) Importing Database(Schema+Data)
- Import the Database in the MySQL database using MySQL WorkBench
- MySQL Workbench->Data Import/Restore->Load Folder Contents->SQL->SQLDB.sql

4) Database setting can be changed
- src->main->java->org.library.DBConnect->DBConnection.java
- Change the Authentication Setting in /Database/DBProperties

5) Run the project
- src->main->java->org.library->App

6) To log in System use login "login" and password "password" or change it in app

7) If u see "Error occurred during initialization of boot layer" error
- File->Project Structure-> Modules then remove all modules except "Maven: org.openjfx[...]" and "Maven: mysql:mysql-connector[...]"

## Contact
Created by [@MarcinCzekaj00](https://github.com/MarcinCzekaj00/)
