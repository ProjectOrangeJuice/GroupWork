package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class represents a person from which both the user and librarian classes
 * are inherited from. It holds all the standard information to create a
 * profile.
 * 
 * @author Charles Day
 * @version 1.0
 */
public abstract class Person {

    /** The persons user name. */
    protected final String username;

    /** The persons first name. */
    private String firstName;

    /** The persons last name. */
    private String lastName;

    /** The persons phone number. */
    private String phoneNumber;

    /** The persons street address. */
    private String address;

    /** The persons post code. */
    private String postcode;

    /** The persons chosen avatar image. */
    private String avatarPath;

    /**
     * Creates a new Person object from the given arguments.
     * 
     * @param username
     * @param firstName
     * @param lastName
     * @param phoneNumber
     * @param address
     * @param postcode
     * @param avatar
     */
    public Person(String username, String firstName, String lastName, String phoneNumber, String address,
            String postcode, String avatarPath) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.postcode = postcode;
        this.avatarPath = avatarPath;
    }

    /**
     * Returns the user name of the person.
     * 
     * @return userName String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the first name of the person.
     * 
     * @return firstName String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the person.
     * 
     * @param firstName String
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
        Person.updateDatabase("users", this.getUsername(), "firstName", firstName);
    }

    /**
     * Returns the last name of the person.
     * 
     * @return lastName String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the person.
     * 
     * @param lastName String
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
        Person.updateDatabase("users", this.getUsername(), "lastName", lastName);
    }

    /**
     * Returns the phone number of the person.
     * 
     * @return phoneNumber String
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the person.
     * 
     * @param phoneNumber String
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        Person.updateDatabase("users", this.getUsername(), "telephone", phoneNumber);
    }

    /**
     * Returns the address of the person.
     * 
     * @return address String
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the person.
     * 
     * @param address String
     */
    public void setAddress(String address) {
        this.address = address;
        Person.updateDatabase("users", this.getUsername(), "address", address);
    }

    /**
     * Returns the post code of the person.
     * 
     * @return postcode String
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * Sets the post code of the person
     * 
     * @param postcode String
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
        Person.updateDatabase("users", this.getUsername(), "postcode", postcode);
    }

    /**
     * Returns the avatar the person has chosen.
     * 
     * @return avatar Image
     */
    public String getAvatar() {
        return avatarPath;
    }

    /**
     * Sets the avatar the person has chosen.
     * 
     * @param avatar Image
     */
    public void setAvatar(String avatarPath) {
        this.avatarPath = avatarPath;
        Person.updateDatabase("users", this.getUsername(), "avatarPath", avatarPath);
    }

    /**
     * loads up a user and librarian from the database
     * @param userName
     * @return either a librarian or a user from the database
     */
    public static Person loadPerson(String userName) {
        try {
            // Declaring necessary variables
            Connection dbConnection = DBHelper.getConnection();

            PreparedStatement sqlStatement = dbConnection
                .prepareStatement("SELECT COUNT(*) FROM users WHERE users.username = ?");
            sqlStatement.setString(1, userName);
            ResultSet rs = sqlStatement.executeQuery();

            //Selects the amount of people who are staff
            if (rs.getInt(1) == 1) {
                sqlStatement = dbConnection.prepareStatement("SELECT COUNT (*) FROM staff WHERE username = ?");
                sqlStatement.setString(1, userName);
                rs = sqlStatement.executeQuery();

                //Loads up all staff users
                if (rs.getInt(1) == 1) {
                    sqlStatement = dbConnection.prepareStatement(
                        "SELECT * FROM users, staff WHERE users.username = staff.username and users.username = ?");
                    sqlStatement.setString(1, userName);
                    rs = sqlStatement.executeQuery();

                    String usernameResult = rs.getString(1);
                    String firstnameResult = rs.getString(2);
                    String lastnameResult = rs.getString(3);
                    String telephoneResult = rs.getString(4);
                    String addressResult = rs.getString(5);
                    String postcodeResult = rs.getString(6);
                    String pathResult = rs.getString(7);
                    String staffIDResult = rs.getString(10);
                    String employmentDateResult = rs.getString(11);

                    dbConnection.close();
                    return new Librarian(usernameResult, firstnameResult, lastnameResult, telephoneResult,
                        addressResult, postcodeResult, pathResult, employmentDateResult,
                        Integer.parseInt(staffIDResult));
                }
                else {

                	//Loads up normal users
                    sqlStatement = dbConnection.prepareStatement("SELECT * FROM users WHERE username = ?");
                    sqlStatement.setString(1, userName);
                    rs = sqlStatement.executeQuery();

                    String usernameResult = rs.getString(1);
                    String firstnameResult = rs.getString(2);
                    String lastnameResult = rs.getString(3);
                    String telephoneResult = rs.getString(4);
                    String addressResult = rs.getString(5);
                    String postcodeResult = rs.getString(6);
                    String pathResult = rs.getString(7);
                    String balanceResult = rs.getString(8);

                    dbConnection.close();
                    return new User(usernameResult, firstnameResult, lastnameResult, telephoneResult, addressResult,
                        postcodeResult, pathResult, Double.parseDouble(balanceResult));
                }
            }
            else {
                dbConnection.close();
                // throw new IllegalStateException("Either too many or not enough rows
                // returned.");
            }

            // Catch most other errors!
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        // By default return null.
        return null;
    }

    /**
     * Method that updates the database
     * @param table String
     * @param username String
     * @param column String
     * @param data String
     * @return True if the database updates and false if it does not
     */
    protected static boolean updateDatabase(String table, String username, String column, String data) {
        try {
            Connection connectionToDB = DBHelper.getConnection();
            PreparedStatement sqlStatement = connectionToDB.prepareStatement("UPDATE " + 
               table + " SET " + column + " = ? WHERE username = ?");
            sqlStatement.setString(1, data);
            sqlStatement.setString(2, username);
            if (sqlStatement.executeUpdate() == 1) {
                connectionToDB.close();
                return true;
            }
            connectionToDB.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Removes the user from the database.
     * 
     * @param username The username to remove
     * @return If it was removed
     */
    public static boolean removePerson(String username) {
        try {
            if (!userBorrowing(username)) {

                Connection connectionToDb = DBHelper.getConnection();
                PreparedStatement sqlStatement = connectionToDb.prepareStatement("DELETE FROM userRequests WHERE userName= ?");
                sqlStatement.setString(1, username);
                sqlStatement.executeUpdate();
                sqlStatement = connectionToDb.prepareStatement("DELETE FROM users WHERE userName= ?");
                sqlStatement.setString(1, username);
                sqlStatement.executeUpdate();

                return true;

            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Method that gets a user that is currently borrowing the resource
     * @param username String
     * @return true if the user is borrowing a resource
     * @throws SQLException
     */
    private static boolean userBorrowing(String username) throws SQLException {
        Connection connectionToDB = DBHelper.getConnection();
        PreparedStatement sqlStatement = connectionToDB.prepareStatement("SELECT" + 
            " * FROM borrowRecords WHERE username= ?");
        sqlStatement.setString(1, username);
        ResultSet results = sqlStatement.executeQuery();
        if (results.next()) {
            return true;
        }
        return false;

    }

}
