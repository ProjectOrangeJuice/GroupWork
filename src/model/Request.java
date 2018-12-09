package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Request {

    private String username;

    private String resourceName;

    private Resource resource;

    public Request(String username, Resource resource) {
        this.username = username;
        this.resource = resource;
        this.resourceName = resource.getTitle();

    }

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

    public String getUsername() {
        return username;
    }

    public String getResourceName() {
        return resourceName;
    }

    public Resource getResource() {
        return resource;
    }

    public boolean contains(String search) {

        if (username.toLowerCase().contains(search.toLowerCase())) {
            return true;
        }
        return false;
    }

}
