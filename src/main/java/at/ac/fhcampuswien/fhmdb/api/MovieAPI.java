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

    private static final String BASE_URL = "https://prog2.fh-campuswien.ac.at/movies";
    private static final OkHttpClient client = new OkHttpClient();

    public static List<Movie> getAllMovies()
    {   // return every movie from the API
        return parseJsonToMovieList(makeRequest(createURL(null,null,null,null,null)));
    }
    public static List<Movie> getAllMovies(String query, Genre genre, String releaseYear, String ratingFrom)
    {
       return parseJsonToMovieList(makeRequest(createURL(query,genre,releaseYear,ratingFrom,null)));
    }
    public static Movie getOneMovie(String id)
    {
        return parseJsonToMovie(makeRequest(createURL(null,null,null,null,id)));
    }

    private static List<Movie> parseJsonToMovieList(String responseBody)
    {
        try{
            Gson gson = new Gson();
            Movie[] movies = gson.fromJson(responseBody, Movie[].class);
            return Arrays.asList(movies);
        } catch (Exception e)
        {
            System.err.println("Problem by parsing Json into Movielist: " + e.getMessage());
            return null;
        }
    }
    private static Movie parseJsonToMovie(String responseBody)
    {
        try{
            Gson gson = new Gson();
            return gson.fromJson(responseBody, Movie.class);
        } catch (Exception e)
        {
            System.err.println("Problem by parsing Json into Movie-Object: " + e.getMessage());
            return null;
        }
    }

    private static String makeRequest(String url){
        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", "http.agent")
                .build();
        try (Response response = client.newCall(request).execute()){
            return response.body().string();
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
        return null;
    }

    /*
    creates the URL to request the movie data based on selection from the drop-down list
     */
    private static String createURL(String query, Genre genre, String releaseYear, String ratingFrom, String id)
    {
        StringBuilder newURL = new StringBuilder();
        newURL.append(BASE_URL);
        if((query != null && !query.isBlank()) || (genre != null && genre != Genre.ALL_GENRES) || releaseYear != null || ratingFrom != null)
        {
            newURL.append("?");
            if(query != null && !query.trim().equals(""))
            {
                query = URLEncoder.encode(query);
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
        }
        if(id != null && !id.isBlank())
        {
            newURL.append("/").append(id);
        }
        System.out.println(newURL.toString());
        return newURL.toString();
    }

}
