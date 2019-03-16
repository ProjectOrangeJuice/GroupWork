package model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * A class that represents a movie description as stored on the Total Movie 
 * Database web API.
 * @author Alexandru Dascalu
 * @version 1.0
 */
public class MovieDescription {
    /**The number of votes this movie has.*/
    private int voteCount;
    
    /**The ID of this movie on TMDB.*/
    private int movieID;
    
    /**Says if this movie is just a video on TDMB or not.*/
    private boolean isVideo;
    
    /**The average rating given by users on TMDB.*/
    private double voteAverage;
    
    /**The title of the movie.*/
    private String title;
    
    /**The popularity of this movie.*/
    private double popularity;
    
    /**The path to the poster image on TMDB.*/
    private String posterPath;
    
    /**The original language of the movie.*/
    private String originalLanguage;
    
    /**The original title of the movie.*/
    private String originalTitle;
    
    /**The IDs of the genres this movie belongs to.*/
    private int[] genreIDs;
    
    /**The path to the backdrop image of this movie on IMDB.*/
    private String backdropPath;
    
    /**Says whether this movie is for adults only or not.*/
    private boolean isAdult;
    
    /**The overview of this movie on IMDB.*/
    private String overview;
    
    /**The release date of this movie.*/
    private String releaseDate;
    
    /**
     * Gets the number of votes of this movie.
     * @return the number of votes of this movie.
     */
    @JsonGetter("vote_count")
    public int getVoteCount() {
        return voteCount;
    }
    
    /**
     * Sets the vote count of this movie to the given value.
     * @param voteCount The new vote count of this movie.
     */
    @JsonSetter("vote_count")
    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
    
    /**
     * Gets the TMDB ID of this movie.
     * @return the TMDB ID of this movie.
     */
    @JsonGetter("id")
    public int getID() {
        return movieID;
    }
    
    /**
     * Sets the id of this movie to the given value.
     * @param id The new id of this movie.
     */
    @JsonSetter("id")
    public void setID(int id) {
        movieID = id;
    }
    
    /**
     * Says if this movie is considered to be just a video.
     * @return true if this movie is considered to be just a video, false
     * if not.
     */
    @JsonGetter("video")
    public boolean isVideo() {
        return isVideo;
    }
    
    /**
     * Sets whether this movide is just a video.
     * @param isAdult True if the movie is just a video, false if not.
     */
    @JsonSetter("video")
    public void setVideo(boolean isVideo) {
        this.isVideo = isVideo;
    }
    
    /**
     * Gets the average of votes of this movie.
     * @return the average of votes of this movie.
     */
    @JsonGetter("vote_average")
    public double getVoteAverage() {
        return voteAverage;
    }
    
    /**
     * Sets the vote average of this movie to the given value.
     * @param voteAverage The new vote average of this movie.
     */
    @JsonSetter("vote_average")
    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }
    
    /**
     * Gets the title of this movie.
     * @return the title of this movie.
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Sets the title of this movie to the given value.
     * @param title The new title of this movie.
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * Gets the popularity of this movie.
     * @return the popularity of this movie.
     */
    public double getPopularity() {
        return popularity;
    }
    
    /**
     * Sets the popularity of this movie to the given value.
     * @param popularity The new popularity of this movie.
     */
    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }
    
    /**
     * Gets the path to the poster of this movie.
     * @return the path to the poster of this movie.
     */
    @JsonGetter("poster_path")
    public String getPosterPath() {
        return posterPath;
    }
    
    /**
     * Sets the poster path of this movie to the given value.
     * @param posterPath The new poster path of this movie.
     */
    @JsonSetter("poster_path")
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
    
    /**
     * Gets the original language of this movie.
     * @return the original language of this movie.
     */
    @JsonGetter("original_language")
    public String getOriginalLanguage() {
        return originalLanguage;
    }
    
    /**
     * Sets the original language of this movie to the given value.
     * @param originalLanguage The new language of this movie.
     */
    @JsonSetter("original_language")
    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }
    
    /**
     * Gets the original title of this movie.
     * @return the original title of this movie.
     */
    @JsonGetter("original_title")
    public String getOriginalTitle() {
        return originalTitle;
    }
    
    /**
     * Sets the original title of this movie to the given value.
     * @param originalTitle The new title of this movie.
     */
    @JsonSetter("original_title")
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }
    
    /**
     * Gets the list of TMDB ids of the genres of this movie.
     * @return the array of TMDB ids of the genres of this movie.
     */
    @JsonGetter("genre_ids")
    public int[] getGenreIDs() {
        return genreIDs;
    }
    
    /**
     * Sets the list of TMDB ids of the genres of this movie to the given array.
     * @param genreIDs The new list of TMDB ids of the genres of this movie.
     */
    @JsonSetter("genre_ids")
    public void setGenreIDs(int[] genreIDs) {
        this.genreIDs = genreIDs;
    }
    
    /**
     * Gets the original title of this movie.
     * @return the original title of this movie.
     */
    @JsonGetter("backdrop_path")
    public String getBackdropPath() {
        return backdropPath;
    }
    
    /**
     * Sets the backdrop path of this movie to the given value.
     * @param backdropPath The new overview of this movie to the given value.
     */
    @JsonSetter("backdrop_path")
    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }
    
    /**
     * Says if this movie is for adults only or not.
     * @return true if the movie is for adults only, false if not.
     */
    @JsonGetter("adult")
    public boolean isAdult() {
        return isAdult;
    }
    
    /**
     * Sets the adults only quality of this movie.
     * @param isAdult True if the movie is adults only, false if not.
     */
    @JsonSetter("adult")
    public void setAdult(boolean isAdult) {
        this.isAdult = isAdult;
    }
    
    /**
     * Gets the overview description of this movie.
     * @return the overview description of this movie.
     */
    public String getOverview() {
        return overview;
    }
    
    /**
     * Sets the overview of this movie to the given value.
     * @param overview The new overview of the movie.
     */
    public void setOverview(String overview) {
        this.overview = overview;
    }
    
    /**
     * Gets the release date of this movie.
     * @return the release date of this movie.
     */
    @JsonGetter("release_date")
    public String getReleaseDate() {
        return releaseDate;
    }
    
    /**
     * Sets the release date of this movie to the given value.
     * @param releaseDate The new release date of the movie.
     */
    @JsonSetter("release_date")
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
