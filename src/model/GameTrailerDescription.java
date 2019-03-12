package model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class GameTrailerDescription {
    private int id;
    private String name;
    private String thumbnailPath;
    private TrailerURLs webURLs;
    private boolean isHighlight;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @JsonGetter("thumbnail")
    public String getThumbnailPath() {
        return thumbnailPath;
    }
    
    @JsonSetter("thumbnail")
    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }
    
    @JsonGetter("webm")
    public TrailerURLs getWebURLs() {
        return webURLs;
    }
    
    @JsonSetter("webm")
    public void setWebURLs(TrailerURLs webURLs) {
        this.webURLs = webURLs;
    }
    
    @JsonGetter("highlight")
    public boolean isHighlight() {
        return isHighlight;
    }
    
    @JsonSetter("highlight")
    public void setHighlight(boolean isHighlight) {
        this.isHighlight = isHighlight;
    }
    
}
