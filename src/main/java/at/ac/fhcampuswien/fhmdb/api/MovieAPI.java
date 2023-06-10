package at.ac.fhcampuswien.fhmdb.api;

import at.ac.fhcampuswien.fhmdb.exceptions.MovieApiException;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.builderpattern.URLBuilder;
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

    public static List<Movie> getAllMovies() throws MovieApiException {   // return every movie from the API
        return parseJsonToMovieList(makeRequest(new URLBuilder(BASE_URL).build()));
    }
    public static List<Movie> getAllMovies(String query, Genre genre, String releaseYear, String ratingFrom) throws MovieApiException {
        String url = new URLBuilder(BASE_URL)
                .setQuery(query)
                .setGenre(genre)
                .setReleaseYear(releaseYear)
                .setRatingFrom(ratingFrom)
                .build();
       return parseJsonToMovieList(makeRequest(url));
    }
    public static Movie getOneMovie(String id) throws MovieApiException {
        String url = new URLBuilder(BASE_URL)
                .setPath(id)
                .build();
        return parseJsonToMovie(makeRequest(url));
    }

    private static List<Movie> parseJsonToMovieList(String responseBody) throws MovieApiException
    {
        try{
            Gson gson = new Gson();
            Movie[] movies = gson.fromJson(responseBody, Movie[].class);
            return Arrays.asList(movies);
        } catch (Exception e)
        {
            System.err.println("Problem by parsing Json into Movielist: " + e.getMessage());
            throw new MovieApiException(e.getMessage());
        }
    }
    private static Movie parseJsonToMovie(String responseBody) throws MovieApiException
    {
        try{
            Gson gson = new Gson();
            return gson.fromJson(responseBody, Movie.class);
        } catch (Exception e)
        {
            System.err.println("Problem by parsing Json into Movie-Object: " + e.getMessage());
            throw new MovieApiException(e.getMessage());
        }
    }

    private static String makeRequest(String url) throws MovieApiException {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", "http.agent")
                .build();
        try (Response response = client.newCall(request).execute()){
            return response.body().string();
        }
        catch (Exception e){
            throw new MovieApiException(e.getMessage());
        }
    }

}
