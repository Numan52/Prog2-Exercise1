package at.ac.fhcampuswien.fhmdb.api;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import okhttp3.*;

import java.io.IOException;

public class MovieAPI{

    private final static String baseURL = "https://prog2.fh-campuswien.ac.at/movies";
    private static final OkHttpClient client = new OkHttpClient();

    public static void getAllMovies()
    {
        makeRequest(baseURL);
    }
    public static void getAllMovies(String query, Genre genre, String releaseYear, String ratingFrom)
    {
        makeRequest(createURL(query,genre,releaseYear,ratingFrom));
    }

    private static void makeRequest(String url){
        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", "http.agent")
                .build();
        try (Response response = client.newCall(request).execute()){
            String responseBody = response.body().string();
            System.out.println(responseBody);
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
    private static String createURL(String query, Genre genre, String releaseYear, String ratingFrom)
    {
        StringBuilder newURL = new StringBuilder();
        newURL.append(baseURL);
        //String url = newURL.toString();
        if(query != null || genre != null || releaseYear != null || ratingFrom != null)
        {
            newURL.append("?");
            if(query != null && !query.trim().equals(""))
            {
                newURL.append("query=").append(query).append("&");
            }
            if(genre != null)
            {
                newURL.append("genre=").append(genre).append("&");
            }
            if(releaseYear != null)
            {
                newURL.append("releaseYear=").append(releaseYear).append("&");
            }
            if(ratingFrom != null)
            {
                newURL.append("ratingFrom=").append(ratingFrom).append("&");
            }
            //url = newURL.substring(0, newURL.length() - 1);
        }
        return newURL.toString();
    }

}
