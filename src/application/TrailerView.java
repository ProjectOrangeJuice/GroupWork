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
import java.io.IOException;
import java.util.List;

public class TrailerView
{
    private static final String API_KEY = "fde767385d9021cca4adc2853f21a53f";
    
    private static final String SEARCH_URL = "https://api.themoviedb.org/3/search/movie?";
    
    private static final String GET_VIDEOS_URL = "https://api.themoviedb.org/3/movie/{movieID}/videos?";
    
    private static final String YOUTUBE_URL = "https://www.youtube.com/watch?v=";
    
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
        
        System.out.println(video.getKey());
        
        /*System.out.println(result.getID());
        System.out.println(result.getVoteCount());
        System.out.println(result.isVideo());
        System.out.println(result.getVoteAverage());
        System.out.println(result.getPopularity());
        System.out.println(result.getOriginalLanguage());
        System.out.println(result.getOriginalTitle());
        System.out.println(result.getGenreIDs()[1]);
        System.out.println(result.getReleaseDate());*/
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
