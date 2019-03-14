package application;

import java.io.IOException;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javafx.scene.web.WebView;

public class GameTrailerView {
    private static final String YOUTUBE_URL = "https://www.youtube.com/embed/";
    private static final String YOUTUBE_API_SEARCH_URL = "https://www.google" + 
            "apis.com/youtube/v3/search?part=snippet&maxResults=1&order=rating&q=";
    private static final String YOUTUBE_API_KEY = "&key=AIzaSyBLyeidWvIxNFdvK" + 
            "0Bl7fPZ_WqeXrI8cac";
    private static final String KEYWORD_ADD_ON = " PC Launch Trailer";
    private static final int REQUEST_TIMEOUT = 10000;
    
    private String videoName;
    private WebView youtubeView;
    
    public GameTrailerView(String gameName) {
        String youtubeKey = getYoutubeKey(gameName);
        
        youtubeView = new WebView();
        youtubeView.setPrefSize(1600, 900);
        youtubeView.getEngine().load(YOUTUBE_URL + youtubeKey);
    }
    
    private String getYoutubeKey(String gameName) {

        String keyword = gameName + KEYWORD_ADD_ON;
        keyword = keyword.replace(" ", "+");
 
        String searchUrl = YOUTUBE_API_SEARCH_URL + keyword + YOUTUBE_API_KEY;
 
        Document resultsDocument = null;
        try {
            Connection apiConnection = Jsoup.connect(searchUrl).timeout(REQUEST_TIMEOUT);
            resultsDocument = apiConnection.ignoreContentType(true).get();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
 
        String jsonString = resultsDocument.text();
        JSONObject jsonObject = (JSONObject) new JSONTokener(jsonString).nextValue();
 
        JSONObject firstResult = jsonObject.getJSONArray("items").getJSONObject(0);
        videoName = firstResult.getJSONObject("snippet").getString("title");
        
        return firstResult.getJSONObject("id").getString("videoId");
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
        return youtubeView;
    }
    
    public String getVideoName() {
        return videoName;
    }
    
    public void stop() {
        youtubeView.getEngine().load(null);
    }
}
