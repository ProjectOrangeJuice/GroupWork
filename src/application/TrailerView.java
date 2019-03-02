package application;

import model.MovieDescription;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
//import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.IOException;
import javafx.scene.media.MediaView;

public class TrailerView
{
    public static void main(String args[]) throws UnirestException, JsonParseException, JsonMappingException, IOException
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
        
        HttpResponse<String> response = Unirest.get("https://api.themoviedb.org/3/search/movie?api_key=fde767385d9021cca4adc2853f21a53f&language=en-US&query=Iron%20Man&page=1&include_adult=false")
                .asString();
        
        String firstResult = getFirstResult(response.getBody());
        System.out.println(firstResult);
        ObjectMapper jsonMapper = new ObjectMapper();
        MovieDescription movieDescription = jsonMapper.readValue(firstResult, MovieDescription.class);
        
        System.out.println(movieDescription.getId());
    }
    
    private static String getFirstResult(String jsonSearchResult)
    {
        int movieDescriptionStart = jsonSearchResult.indexOf("{",1);
        int movieDescriptionEnd = jsonSearchResult.indexOf("}");
        
        return jsonSearchResult.substring(movieDescriptionStart, movieDescriptionEnd + 1);
    }
}
