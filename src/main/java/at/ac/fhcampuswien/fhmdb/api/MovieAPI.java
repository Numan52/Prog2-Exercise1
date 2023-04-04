package at.ac.fhcampuswien.fhmdb.api;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

public class MovieAPI{

    private final static String baseURL = "https://prog2.fh-campuswien.ac.at/movies";
    private static final OkHttpClient client = new OkHttpClient();

    public static List<Movie> getAllMovies()
    {
        return makeRequest(createURL(null,null,null,null));
    }
    public static List<Movie> getAllMovies(String query, Genre genre, String releaseYear, String ratingFrom)
    {
       return makeRequest(createURL(query,genre,releaseYear,ratingFrom));
    }

    private static List<Movie> makeRequest(String url){
        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", "http.agent")
                .build();
        try (Response response = client.newCall(request).execute()){
            String responsebody = response.body().string();
            Gson gson = new Gson();
            Movie[] movies = gson.fromJson(responsebody, Movie[].class);
            return Arrays.asList(movies);
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
        return null;
    }
    private static String createURL(String query, Genre genre, String releaseYear, String ratingFrom)
    {
        StringBuilder newURL = new StringBuilder();
        newURL.append(baseURL);
        //String url = newURL.toString();
        if((query != null && !query.trim().equals("")) || (genre != null && genre != Genre.ALL_GENRES) || releaseYear != null || ratingFrom != null)
        {
            newURL.append("?");
            if(query != null && !query.trim().equals(""))
            {
                query = URLEncoder.encode(query);
                System.out.println(query);
                newURL.append("query=").append(query).append("&");
            }
            if(genre != null && genre != Genre.ALL_GENRES)
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
        System.out.println(newURL.toString());
        return newURL.toString();
    }

}
