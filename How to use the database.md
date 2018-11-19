# Database

The database is called "test.db" and will be found in the working directory (Not bin). The main class should call DBHelper.tableCheck(); to ensure the database is ready. 

For testing you can call this in any location.

## Force update

Remove the database file. Re-run the DBHelper

## Getting the connection

When you create the DBHelper object it is not the connection. Call DBHelper.connection(); to get the connection. **Make sure you close the connection afterwards**

## Running the database

Ensure the lib in the lib folder is included in your path

## Running sql

http://www.sqlitetutorial.net/sqlite-java/

## Changing the table schema 

If you change the schema ensure it runs. The database won't update unless the version is changed in the DBHelper class. If you push this then everyones database will change
