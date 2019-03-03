package model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class VideoDescription 
{
    private String videoID;
    private String iso6391;
    private String iso31661;
    private String key;
    private String name;
    private String site;
    private String size;
    private String type;
    
    @JsonGetter("id")
    public String getID() {
        return videoID;
    }
    
    @JsonSetter("id")
    public void setId(String videoID) {
        this.videoID = videoID;
    }
    
    @JsonGetter("iso_639_1")
    public String getIso6391() {
        return iso6391;
    }
    
    @JsonSetter("iso_639_1")
    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }
    
    @JsonGetter("iso_3166_1")
    public String getISO31661() {
        return iso31661;
    }
    
    @JsonSetter("iso_3166_1")
    public void setISO31661(String iso31661) {
        this.iso31661 = iso31661;
    }
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getSite() {
        return site;
    }
    
    public void setSite(String site) {
        this.site = site;
    }
    
    public String getSize() {
        return size;
    }
    
    public void setSize(String size) {
        this.size = size;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
}
