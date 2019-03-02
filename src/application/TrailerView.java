package application;

import javafx.scene.media.MediaView;

public class TrailerView extends MediaView
{
    public void test()
    {
        HttpResponse<String> response = Unirest.get("https://api.themoviedb.org/3/search/movie?include_adult=false&page=1&query=Thor&language=en-US&api_key=fde767385d9021cca4adc2853f21a53f")
                .body("{}")
                .asString();
    }
}
