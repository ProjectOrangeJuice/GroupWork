package application;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import javafx.scene.web.WebView;
import model.GameID;
import model.GameTrailerDescription;

public class GameTrailerView {
    private ObjectMapper jsonMapper;
    private WebView steamView;
    private GameID app;
    private GameTrailerDescription trailerDescription;
    
    public GameTrailerView(String gameName) {
        jsonMapper = new ObjectMapper();
        
        app = getGameDescription(gameName);
        trailerDescription = getTrailerDescription(app.getAppID());
        
        steamView = new WebView();
        steamView.setPrefSize(1600, 900);
        steamView.getEngine().load(trailerDescription.getWebURLs().getMaxResURL());
    }
    
    private GameTrailerDescription getTrailerDescription(int gameID) {
        HttpResponse<String> response = null;
        
        try {
            response = Unirest.get("https://store.steampowered.com/api/appdetails/?appids={appID}")
                    .routeParam("appID", gameID + "").asString();
        }
        catch (UnirestException e) {
            e.printStackTrace();
            //System.exit(-1);
        }
        
        int trailerListName = response.getBody().indexOf("\"movies\"");
        int trailerListStart = response.getBody().indexOf("[", trailerListName);
        int trailerListEnd = response.getBody().indexOf("]", trailerListName);
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
}
