package application;

import model.MovieDescription;
import model.VideoDescription;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import javafx.scene.web.WebView;
import java.io.IOException;
import java.util.List;

public class MovieTrailerView
{
    private static final String API_KEY = "fde767385d9021cca4adc2853f21a53f";
    
    private static final String SEARCH_URL = "https://api.themoviedb.org/3/search/movie?";
    
    private static final String GET_VIDEOS_URL = "https://api.themoviedb.org/3/movie/{movieID}/videos?";
    
    private static final String YOUTUBE_URL = "https://www.youtube.com/embed/";
    
    private ObjectMapper jsonMapper;
    
    private MovieDescription movieDescription;
    
    private VideoDescription trailerDescription;
    
    private WebView youtubeView;
    
    public MovieTrailerView(String movieName)
    {
        jsonMapper = new ObjectMapper();
        
        
        movieDescription = getMovieFromTMDB(movieName);
        trailerDescription = getTrailerFromTMDB(movieDescription);
        youtubeView = new WebView();
        youtubeView.getEngine().load(YOUTUBE_URL + trailerDescription.getKey());
        youtubeView.setPrefSize(1600, 900);
    }
    
    public MovieDescription getMovieFromTMDB(String movieName)
    {
        HttpResponse<String> response = null;
        
        try {
            response = Unirest.get(SEARCH_URL).queryString("api_key", API_KEY)
                    .queryString("query", movieName).queryString("page", 1)
                    .queryString("include_adult", false).asString();
        }
        catch (UnirestException e) {
            e.printStackTrace();
            //System.exit(-1);
        }
        
        String resultList = getResultList(response.getBody());
        List<MovieDescription> movieDescriptions = null;
        
        try {
            movieDescriptions = jsonMapper.readValue(resultList, new TypeReference<List<MovieDescription>>() {});
        }
        catch (IOException e) 
        {
            e.printStackTrace();
            //System.exit(-1);
        }
      
        MovieDescription result = movieDescriptions.get(0);
        /*boolean search = true;
        int i = 0;
        while(search && i < movieDescriptions.size())
        {
            if(movieDescriptions.get(i).getTitle().equals(movieName))
            {
                result = movieDescriptions.get(i);
                search = false;
            }
            
            i++;
        }*/
        
        return result;
    }
    
    public VideoDescription getTrailerFromTMDB(MovieDescription movieDescription)
    {
        HttpResponse<String> response = null;
        try {
            response = Unirest.get(GET_VIDEOS_URL).routeParam("movieID", movieDescription.getID() + "")
                    .queryString("api_key", API_KEY).asString();
        }
        catch (UnirestException e) 
        {
            e.printStackTrace();
            //System.exit(-1);
        }
        
        String resultList = getResultList(response.getBody());
        
        List<VideoDescription> videoDescriptions = null;
        try {
            videoDescriptions = jsonMapper.readValue(resultList, 
                new TypeReference<List<VideoDescription>>() {});
        }
        catch (IOException e) 
        {
            e.printStackTrace();
           // System.exit(-1);
        }
        
        VideoDescription video = null;
        boolean search = true;
        int i = 0;
        while(search && i < videoDescriptions.size())
        {
            VideoDescription currentVideo = videoDescriptions.get(i);
            if(currentVideo.getType().equalsIgnoreCase("Trailer") && 
                    currentVideo.getSite().equalsIgnoreCase("YouTube"))
            {
                video = currentVideo;
                break;
            }
            
            i++;
        }
        
        return video;
    }
    
    public void stop() {
        youtubeView.getEngine().load(null);
    }
    
    public WebView getWebView()
    {
        return youtubeView;
    }
    
    public MovieDescription getMovieDescription()
    {
        return movieDescription;
    }
    
    public VideoDescription getTrailerDescription()
    {
        return trailerDescription;
    }
    
    private static String getFirstResult(String jsonSearchResult)
    {
        int movieDescriptionStart = jsonSearchResult.indexOf("{",1);
        int movieDescriptionEnd = jsonSearchResult.indexOf("}");
        
        return jsonSearchResult.substring(movieDescriptionStart, movieDescriptionEnd + 1);
    }
    
    private static String getResultList(String jsonSearchResult)
    {
        int resultStart = jsonSearchResult.indexOf("[");
        int resultEnd = jsonSearchResult.lastIndexOf("]");
        
        return jsonSearchResult.substring(resultStart, resultEnd + 1);
    }
}
