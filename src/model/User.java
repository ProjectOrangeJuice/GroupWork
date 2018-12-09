package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This class represents a user of the library, allowing them to borrow copies
 * of recourses and make payments towards any fines against them. It has all the
 * attributes and methods of the Person class with the inclusion of the account
 * balance behaviour.
 * 
 * @author Charles Day
 * @version 1.0
 */
public class User extends Person {

    /** The current account balance for this User. */
    private double accountBalance;

    /** All of the copies the user has taken out. */
    private ArrayList<Copy> copiesList = new ArrayList<Copy>();

    /**
     * Creates a new User object from the given arguments.
     * 
     * @param username
     * @param firstName
     * @param lastName
     * @param phoneNumber
     * @param address
     * @param postcode
     * @param avatar
     * @param accountBalance
     */
    public User(String username, String firstName, String lastName,
        String phoneNumber, String address, String postcode, String avatarPath,
        double accountBalance) {
        super(username, firstName, lastName, phoneNumber, address, postcode,
            avatarPath);
        this.accountBalance = accountBalance;
    }

    /**
     * Adds a copy of a resource that the user has withdrawn.
     * 
     * @param copy Copy
     */
    public void addBorrowedCopy(Copy copy) {
        this.copiesList.add(copy);
        // Updater not needed as copy already updates the database.
    }

    /**
     * Returns all copies that the user has currently withdrawn.
     * 
     * @return copiesList ArrayList
     */
    public ArrayList<Copy> getBorrowedCopies() {
        return copiesList;
    }

    public ArrayList<Resource> getRequestedResources() {

        ArrayList<Resource> requestedResource = new ArrayList<Resource>();

        try {
            Connection dbConnection = DBHelper.getConnection();
            PreparedStatement sqlStatement = dbConnection.prepareStatement(
                "SELECT rID FROM userRequests WHERE userName = ?");
            sqlStatement.setString(1, this.getUsername());
            ResultSet rs = sqlStatement.executeQuery();

            while (rs.next()) {
                requestedResource.add(Resource.getResource(rs.getInt("rID")));
            }

        }
        catch (SQLException e) {
            System.out.println("Cannot find requested requested resources.");
            e.printStackTrace();
        }

        return requestedResource;

    }

    /**
     * Removes a copy from the list of copies withdrawn.
     * 
     * @param copy Copy
     */
    public void removeBorrowedCopy(Copy copy) {
        copiesList.remove(copy);
        // Updater not needed as copy already updates the database.
    }

    /**
     * Allows payments to be added to the account balance.
     * 
     * @param amount The amount the User has payed in pounds.
     */
    public void makePayment(double amount) {
        this.accountBalance += amount;
        Person.updateDatabase("users", this.getUsername(), "accountBalance",
            Double.toString(this.accountBalance));
    }

    /**
     * Returns the current account balance.
     * 
     * @return accountBalance The current account balance in pounds.
     */
    public double getAccountBalance() {
        return accountBalance;
    }

    /**
     * Reduces the users balance.
     * 
     * @param username The username in the database.
     * @param amount The amount to reduce by.
     * @throws SQLException The database could not update.
     */
    public static void reduceBalance(String username, double amount)
            throws SQLException {
        Connection connection = DBHelper.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "UPDATE users set accountBalance = accountBalance - ? WHERE username=?");
        statement.setString(1, username);
        statement.setDouble(2, amount);

        statement.executeUpdate();
    }

    /**
     * Checks the users balance
     * 
     * @param username The username in the database.
     * @param amount The amount to check it exceeds.
     * @return If the user has enough in their balance.
     * @throws SQLException The database was unable to check.
     */
    public static boolean checkBalance(String username, double amount)
            throws SQLException {
        Connection connection = DBHelper.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "SELECT accountBalance FROM users where username=?");
        statement.setString(1, username);
        ResultSet results = statement.executeQuery();
        if (results.next()) {
            return results.getDouble("accountBalance") >= amount;
        }
        return false;
    }

    public static boolean addBalance(String username, double value) {
        Connection dbConnection;
        try {
            dbConnection = DBHelper.getConnection();

            PreparedStatement sqlStatement2 = dbConnection.prepareStatement(
                "UPDATE users set accountBalance = accountBalance + ? WHERE username=?");
            sqlStatement2.setDouble(1, value);
            sqlStatement2.setString(2, username);

            int updates = sqlStatement2.executeUpdate();
            return (updates >= 1);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Resource> loadUserHistory() {

        ArrayList<Resource> borrowHistory = new ArrayList<Resource>();

        try {
            Connection dbConnection = DBHelper.getConnection();
            PreparedStatement sqlStatement = dbConnection.prepareStatement(
                "SELECT borrowRecords.copyId, rId FROM borrowRecords, copies WHERE username = ? " +
                    "AND copies.copyId = borrowRecords.copyId");
            sqlStatement.setString(1, username);
            ResultSet rs = sqlStatement.executeQuery();

            while (rs.next()) {
                System.out.println("Adding borrow History!");
                borrowHistory.add(Resource.getResource(rs.getInt("rID")));
            }
        }
        catch (SQLException e) {
            System.out.println("Failed to load user history;");
            e.printStackTrace();
        }

        return borrowHistory;
    }

    public void loadUserCopies() {

        copiesList.clear();

        try {
            Connection dbConnection = DBHelper.getConnection();
            PreparedStatement sqlStatement = dbConnection.prepareStatement("SELECT * FROM " + 
               "copies WHERE keeper = ?");
            sqlStatement.setString(1, username);
            ResultSet rs = sqlStatement.executeQuery();

            while (rs.next()) {
                copiesList.add(Resource.getResource(rs.getInt("rID"))
                    .getCopy(rs.getInt("copyID")));
            }
        }
        catch (SQLException e) {
            System.out.println("Failed to load copies into user.");
            e.printStackTrace();
        }
    }

    public boolean hasOutstandingFines() {
        try {
            Connection dbConnection = DBHelper.getConnection();
            PreparedStatement sqlStatement = dbConnection.prepareStatement(
                "SELECT COUNT(*) FROM fines WHERE username = ? AND paid = 0;");
            sqlStatement.setString(1, this.getUsername());
            ResultSet rs = sqlStatement.executeQuery();
            dbConnection.close();

            if (rs.getInt(1) == 0) {
                return false;
            }
            else {
                return true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isBorrowing(Resource resource) {
        for (Copy copy : getBorrowedCopies()) {
            if (copy.getResource() == resource) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Resource> getRecommendations() throws SQLException {
        Connection connectionToDB = DBHelper.getConnection();
        PreparedStatement sqlStatement = connectionToDB.prepareStatement(
            "select copyID from borrowRecords where username=?");
        sqlStatement.setString(1, username);
        ResultSet borrowedCopiesID = sqlStatement.executeQuery();

        sqlStatement = connectionToDB
            .prepareStatement("select rID from copies where copyID=?");
        ArrayList<Resource> borrowedResources = new ArrayList<>();

        while (borrowedCopiesID.next()) {
            int copyID = borrowedCopiesID.getInt("copyID");

            sqlStatement.setInt(1, copyID);
            ResultSet resourceIDResult = sqlStatement.executeQuery();

            /*
             * Since copyID is the primary key of copies, the result to
             * selecting rID will always have only one row.
             */
            int resourceID = resourceIDResult.getInt("rID");
            System.out.println("rID: " + resourceID);
            Resource borrowedResource = Resource.getResource(resourceID);

            if (!borrowedResources.contains(borrowedResource)) {
                borrowedResources.add(borrowedResource);
            }
        }

        ArrayList<ResourceRecommendScore> resourceScores = new ArrayList<>();
        ArrayList<Resource> resources = Resource.getResources();
        for (Resource resource : resources) {
            if (!borrowedResources.contains(resource)) {
                ResourceRecommendScore resourceScore = new ResourceRecommendScore();
                resourceScore.setResource(resource);
                resourceScore.setBorrowedResources(borrowedResources);
                resourceScores.add(resourceScore);
            }
        }

        resourceScores.sort(null);

        ArrayList<Resource> recommendedResource = new ArrayList<>();
        for (ResourceRecommendScore resourceScore : resourceScores) {
            if (resourceScore.calculateLikeness() > 0) {
                recommendedResource.add(resourceScore.getResource());
            }
        }

        return recommendedResource;
    }

    /*
     * public static void main(String args[]) { User manny = (User)
     * Person.loadPerson("Manny");
     * 
     * DBHelper.tableCheck(); Resource.loadDatabaseResources();
     * ArrayList<Resource> recs=null; try { recs = manny.getRecommendations(); }
     * catch (SQLException e) { e.printStackTrace(); } }
     */
}