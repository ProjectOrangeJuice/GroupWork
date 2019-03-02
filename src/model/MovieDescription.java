package model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class MovieDescription 
{
    private int voteCount;
    private int movieID;
    private boolean isVideo;
    private double voteAverage;
    private String title;
    private double popularity;
    private String posterPath;
    private String originalLanguage;
    private String originalTitle;
    private int[] genreIDs;
    private String backdropPath;
    private boolean isAdult;
    private String overview;
    private String releaseDate;
    
    @JsonGetter("vote_count")
    public int getVoteCount() {
        return voteCount;
    }
    
    @JsonSetter("vote_count")
    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
    
    @JsonGetter("id")
    public int getID() {
        return movieID;
    }
    
    @JsonSetter("id")
    public void setID(int id) {
        this.movieID = id;
    }
    
    @JsonGetter("video")
    public boolean isVideo() {
        return isVideo;
    }
    
    @JsonSetter("video")
    public void setVideo(boolean isVideo) {
        this.isVideo = isVideo;
    }
    
    @JsonGetter("vote_average")
    public double getVoteAverage() {
        return voteAverage;
    }
    
    @JsonSetter("vote_average")
    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public double getPopularity() {
        return popularity;
    }
    
    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }
    
    @JsonGetter("poster_path")
    public String getPosterPath() {
        return posterPath;
    }
    
    @JsonSetter("poster_path")
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
    
    @JsonGetter("original_language")
    public String getOriginalLanguage() {
        return originalLanguage;
    }
    
    @JsonSetter("original_language")
    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }
    
    @JsonGetter("original_title")
    public String getOriginalTitle() {
        return originalTitle;
    }
    
    @JsonSetter("original_title")
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }
    
    @JsonGetter("genre_ids")
    public int[] getGenreIDs() {
        return genreIDs;
    }
    
    @JsonSetter("genre_ids")
    public void setGenreIDs(int[] genreIDs) {
        this.genreIDs = genreIDs;
    }
    
    @JsonGetter("backdrop_path")
    public String getBackdropPath() {
        return backdropPath;
    }
    
    @JsonSetter("backdrop_path")
    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }
    
    @JsonGetter("adult")
    public boolean isAdult() {
        return isAdult;
    }
    
    @JsonSetter("adult")
    public void setAdult(boolean isAdult) {
        this.isAdult = isAdult;
    }
    
    public String getOverview() {
        return overview;
    }
    
    public void setOverview(String overview) {
        this.overview = overview;
    }
    
    @JsonGetter("release_date")
    public String getReleaseDate() {
        return releaseDate;
    }
    
    @JsonSetter("release_date")
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
