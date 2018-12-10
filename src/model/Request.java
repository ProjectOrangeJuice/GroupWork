package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Models a pending user request to borrowe a book. This request needs to
 * be approved by a librarian before the user is able to get a copy from 
 * the system.
 * @author Oliver Harris
 *
 */
public class Request {

    private String username;

    private String resourceName;

    private Resource resource;

    /**
     * Makes a new request.
     * @param username user's username
     * @param resource the instance of the resource
     */
    public Request(String username, Resource resource) {
        this.username = username;
        this.resource = resource;
        this.resourceName = resource.getTitle();

    }

    /**
     * Method to load requests so they can be approved.
     * @return the requests to be approved.
     */
    public static ArrayList<Request> loadRequests() {
        ArrayList<Request> requests = new ArrayList<Request>();
        try {
            Connection connection = DBHelper.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * " + 
                "FROM requestsToApprove");
            ResultSet results = statement.executeQuery();
            while (results.next()) {

                Resource tempResource = Resource.getResource(results.getInt("rID"));
                if (tempResource != null) {
                    // resource exits
                    requests.add(new Request(results.getString("username"),
                        tempResource));
                } // otherwise do nothing. Should warn user

            }
            return requests;

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Getter method for the user name of the user.
     * @return user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter method for the name of the resource being requested.
     * @return the resource's name
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * Getter method for the resource requested.
     * @return the instance of the resource
     */
    public Resource getResource() {
        return resource;
    }

    /**
     * Method that matches the username to what the librarian has searched.
     * @param search what is being searched
     * @return true if it matches, false otherwise
     */
    public boolean contains(String search) {

        if (username.toLowerCase().contains(search.toLowerCase())) {
            return true;
        }
        return false;
    }

}
