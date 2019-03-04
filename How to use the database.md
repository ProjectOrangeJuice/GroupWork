# Database

The database is called "test.db" and will be found in the working directory (Not bin).
You don't create the object!

To check for the latest database or create the database if you don't have it either call force update or DBHelper.tableCheck();

## Force update

DBHelper.forceUpdate();

## Getting the connection

When you create the DBHelper object it is not the connection. Call DBHelper.connection(); to get the connection.

## Running the database

Ensure the lib in the lib folder is included in your path

## Running sql

http://www.sqlitetutorial.net/sqlite-java/

## Changing the table schema

If you change the schema ensure it runs. The database won't update unless the version is changed in the DBHelper class. If you push this then everyones database will change
