package application;

import model.MovieDescription;
import model.VideoDescription;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class TrailerView extends Application
{
    private static final String API_KEY = "fde767385d9021cca4adc2853f21a53f";
    
    private static final String SEARCH_URL = "https://api.themoviedb.org/3/search/movie?";
    
    private static final String GET_VIDEOS_URL = "https://api.themoviedb.org/3/movie/{movieID}/videos?";
    
    private static final String YOUTUBE_URL = "https://www.youtube.com/embed/";
    
    public static void main(String args[])
    {
        launch();
    }
    
    public void start(Stage stage) throws UnirestException, JsonParseException, JsonMappingException, IOException
    {
        /*Unirest.setObjectMapper(new ObjectMapper() {
            com.fasterxml.jackson.databind.ObjectMapper mapper 
              = new com.fasterxml.jackson.databind.ObjectMapper();
         
            public String writeValue(Object value) 
            {
                try 
                {
                    return mapper.writeValueAsString(value);
                }
                catch (JsonProcessingException e) {
                    e.printStackTrace();
                    return null;
                }
            }
         
            public <T> T readValue(String value, Class<T> valueType) 
            {
                try 
                {
                    return mapper.readValue(value, valueType);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        });*/
        
        HttpResponse<String> response = Unirest.get(SEARCH_URL).queryString("api_key", API_KEY)
                .queryString("query", "Iron Man").queryString("page", 1)
                .queryString("include_adult", false).asString();
        
        ObjectMapper jsonMapper = new ObjectMapper();
        
        String resultList = getResultList(response.getBody());
        List<MovieDescription> movieDescriptions = jsonMapper.readValue(resultList, new TypeReference<List<MovieDescription>>() {});
      
        MovieDescription result = null;
        for(MovieDescription md: movieDescriptions)
        {
            if(md.getTitle().equals("Iron Man"))
            {
                result = md;
                break;
            }
        }
        
        System.out.println(result.getID());
        response = Unirest.get(GET_VIDEOS_URL).routeParam("movieID", result.getID() + "")
                .queryString("api_key", API_KEY).asString();
        resultList = getResultList(response.getBody());
        
        List<VideoDescription> videoDescriptions = jsonMapper.readValue(resultList, new TypeReference<List<VideoDescription>>() {});
        
        VideoDescription video = null;
        for(VideoDescription vd: videoDescriptions)
        {
            if(vd.getType().equalsIgnoreCase("Trailer") && vd.getSite().equalsIgnoreCase("YouTube"))
            {
                video = vd;
                break;
            }
        }
        
        String trailerURL = YOUTUBE_URL + video.getKey();
        
        System.out.println(trailerURL);
 
        WebView webview = new WebView();
        webview.getEngine().load(trailerURL);
        webview.setPrefSize(640, 390);

        stage.setScene(new Scene(webview));
        stage.show();
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
