package model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class TrailerURLs {
    private String lowResURL;
    private String maxResURL;
    
    @JsonGetter("480")
    public String getLowResURL() {
        return lowResURL;
    }
    
    @JsonSetter("480")
    public void setLowResURL(String lowResURL) {
        this.lowResURL = lowResURL;
    }
    
    @JsonGetter("max")
    public String getMaxResURL() {
        return maxResURL;
    }
    
    @JsonSetter("max")
    public void setMaxResURL(String maxResURL) {
        this.maxResURL = maxResURL;
    }
}
