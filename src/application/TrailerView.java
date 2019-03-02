package application;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import javafx.scene.media.MediaView;

public class TrailerView
{
    public static void main(String args[]) throws UnirestException
    {
        HttpResponse<String> response = Unirest.get("https://api.themoviedb.org/3/search/movie?include_adult=false&page=1&query=Thor&language=en-US&api_key=fde767385d9021cca4adc2853f21a53f")
                .asString();
        
        System.out.println(response.getBody());
    }
}
