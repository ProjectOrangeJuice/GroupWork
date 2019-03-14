package application;

import java.io.IOException;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import model.GameID;
import model.GameTrailerDescription;

public class GameTrailerView {
    private static final String YOUTUBE_URL = "https://www.youtube.com/embed/";
    private ObjectMapper jsonMapper;
    private String videoName;
    private WebView steamView;
    private GameID app;
    private GameTrailerDescription trailerDescription;
    
    public GameTrailerView(String gameName) {
        jsonMapper = new ObjectMapper();
        String youtubeKey = getYoutubeKey(gameName);
        System.out.println(youtubeKey);
        
        steamView = new WebView();
        steamView.setPrefSize(1600, 900);
        steamView.getEngine().load(YOUTUBE_URL + youtubeKey);
    }
    
    private String getYoutubeKey(String gameName) {

        String keyword = gameName + " Trailer";
        keyword = keyword.replace(" ", "+");
 
        String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=1&order=rating&q=" + keyword + "&key=AIzaSyBLyeidWvIxNFdvK0Bl7fPZ_WqeXrI8cac";
 
        Document doc = null;
        try {
            doc = Jsoup.connect(url).timeout(10 * 1000).ignoreContentType(true).get();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.exit(-1);
        }
 
        String getJson = doc.text();
        JSONObject jsonObject = (JSONObject) new JSONTokener(getJson ).nextValue();
 
        videoName = jsonObject.getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getString("title");
        return jsonObject.getJSONArray("items").getJSONObject(0).getJSONObject("id").getString("videoId");
    }
    
    /*private GameTrailerDescription getTrailerDescription(int gameID) {
        HttpResponse<String> response = null;
        
        try {
            response = Unirest.get("https://store.steampowered.com/api/appdetails/?appids={appID}")
                    .routeParam("appID", gameID + "").asString();
        }
        catch (UnirestException e) {
            e.printStackTrace();
            //System.exit(-1);
        }
        
        System.out.println(response.getBody());
        int trailerListName = response.getBody().indexOf("\"movies\"");
        int trailerListStart = response.getBody().indexOf("[", trailerListName);
        int trailerListEnd = response.getBody().indexOf("]", trailerListName);
        System.out.println(trailerListName);
        String trailerList = response.getBody().substring(trailerListStart, trailerListEnd + 1);
        
        List<GameTrailerDescription> gameTrailers = null;
        try {
            gameTrailers = jsonMapper.readValue(trailerList, new TypeReference<List<GameTrailerDescription>>() {});
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        return gameTrailers.get(gameTrailers.size() - 1);
    }
    
    private GameID getGameDescription(String gameName) {
        HttpResponse<String> response = null;
        
        try {
            response = Unirest.get("http://api.steampowered.com/ISteamApps/GetAppList/v0002/")
                    .asString();
        }
        catch (UnirestException e) {
            e.printStackTrace();
            //System.exit(-1);
        }
        
        String results = response.getBody();
        
        int  idAppearance = getIndexOfGame(results, gameName);
        if(idAppearance == -1) {
          return null;
        }
        
        int descriptionStart = results.lastIndexOf("{", idAppearance);
        int descriptionEnd = results.indexOf("}", idAppearance);
        
        GameID gameID = null;
        try {
            gameID = jsonMapper.readValue(results.substring(descriptionStart, descriptionEnd + 1), GameID.class);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        return gameID;
    }
    
    private static int getIndexOfGame(String allGames, String gameName) {
        int  idAppearance = -1;
        int lastAppearance = -1;
        
        boolean search = true;
        while(search) {
            idAppearance = allGames.indexOf(gameName, lastAppearance);
            
            if(idAppearance == -1) {
                search = false;
            } else {
                lastAppearance = idAppearance + gameName.length();
                
                if(hasExactName(gameName, allGames, idAppearance)) {
                    search = false;
                }
            }
        }
        
        return idAppearance;
    }
    
    private static boolean hasExactName(String gameName, String allGames, int idAppearance) {
        int quotesStart = allGames.lastIndexOf("\"", idAppearance);
        int quotesEnd = allGames.indexOf("\"", idAppearance);
        
        String fullName = allGames.substring(quotesStart + 1, quotesEnd);
        if(gameName.equals(fullName)) {
            return true;
        } else {
            return false;
        }
    }
    
    public GameID getGameID() {
        return app;
    }
    
    public GameTrailerDescription getTrailerDescription() {
        return trailerDescription;
    }
    */
    
    public WebView getWebView() {
        return steamView;
    }
    
    public String getVideoName() {
        return videoName;
    }
    
    public void stop() {
        steamView.getEngine().load(null);
    }
 
    /*public void start(Stage stage) {
        GameTrailerView g = new GameTrailerView();
        
        Scene trailerScene = new Scene(g.getWebView(), 1600, 900);
        
        stage.setTitle(g.getTrailerDescription().getName());
        
        stage.setOnHidden(e -> {
            g.stop();
        });
        
        System.out.println(g.getWebView().getEngine().getLocation());
        stage.setScene(trailerScene);
        stage.show();
    }*/
}
