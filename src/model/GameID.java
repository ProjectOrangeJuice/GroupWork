package model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class GameID {
    private int appID;
    private String gameName;
    
    @JsonGetter("appid")
    public int getAppID() {
        return appID;
    }
    
    @JsonSetter("appid")
    public void setAppID(int appID) {
        this.appID = appID;
    }
    
    @JsonGetter("name")
    public String getGameName() {
        return gameName;
    }
    
    @JsonSetter("name")
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
}
