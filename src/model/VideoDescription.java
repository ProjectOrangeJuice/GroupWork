package model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * A class that represents the description of video associated with a movie 
 * from the Total Movie Database web API.
 * @author Alexandru Dascalu
 */
public class VideoDescription 
{
    /**The ID on the TMDB web API of this video.*/
    private String videoID;
    
    /**The code of the language the video is in.*/
    private String iso6391;
    
    /**The code of the region this video is associated with.*/
    private String iso31661;
    
    /**The key of the URL this video can be found at.*/
    private String key;
    
    /**The name of the video.*/
    private String name;
    
    /**The name of the web site on which the video can be found.*/
    private String site;
    
    /**The resolution of the video.*/
    private String size;
    
    /**The type of the video.*/
    private String type;
    
    /**
     * Gets the ID on the TMDB web API of this video.
     * @return the ID on the TMDB web API of this video.
     */
    @JsonGetter("id")
    public String getID() {
        return videoID;
    }
    
    /**
     * Sets the video ID to a new value.
     * @param videoID The new video ID to a new value.
     */
    @JsonSetter("id")
    public void setId(String videoID) {
        this.videoID = videoID;
    }
    
    /**
     * Gets the code of the language the video is in.
     * @return the code of the language the video is in.
     */
    @JsonGetter("iso_639_1")
    public String getIso6391() {
        return iso6391;
    }
    
    /**
     * Sets the code of the language of this video to a new value.
     * @param iso6391 The new code of the language of this video.
     */
    @JsonSetter("iso_639_1")
    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }
    
    /**
     * Gets the code of the region associated with this video.
     * @return the code of the region associated with this video.
     */
    @JsonGetter("iso_3166_1")
    public String getISO31661() {
        return iso31661;
    }
    
    /**
     * Sets the code of the region of this video to a new value.
     * @param iso31661 The new code of the region of this video.
     */
    @JsonSetter("iso_3166_1")
    public void setISO31661(String iso31661) {
        this.iso31661 = iso31661;
    }
    
    /**
     * Gets the key of the URL where the video can be found.
     * @return the key of the URL where the video can be found.
     */
    public String getKey() {
        return key;
    }
    
    /**
     * Sets the key of the URL of this video to a new value.
     * @param key The new key of the URL of this video.
     */
    public void setKey(String key) {
        this.key = key;
    }
    
    /**
     * Gets the name of the video.
     * @return the name of the video.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the name of this video to a new value.
     * @param name The new name of this video.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Gets the site where the video can be found.
     * @return the site where the video can be found.
     */
    public String getSite() {
        return site;
    }
    
    /**
     * Sets the site this video can be found at to a new value.
     * @param site The new site this video can be found at.
     */
    public void setSite(String site) {
        this.site = site;
    }
    
    /**
     * Gets the resolution of the video.
     * @return the resolution of the video.
     */
    public String getSize() {
        return size;
    }
    
    /**
     * Sets the resolution of this video to a new value.
     * @param size The new resolution of this video.
     */
    public void setSize(String size) {
        this.size = size;
    }
    
    /**
     * Gets the type of the video.
     * @return the type of the video.
     */
    public String getType() {
        return type;
    }
    
    /**
     * Sets the type of this video to a new value.
     * @param type The new type of this video.
     */
    public void setType(String type) {
        this.type = type;
    }
}
