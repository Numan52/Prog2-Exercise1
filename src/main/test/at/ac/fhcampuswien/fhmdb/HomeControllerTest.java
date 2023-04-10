package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.api.MovieAPI;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {
    @Test
    void sort_null_movielist_ascending_throws_exception()
    {
        //given
        HomeController homeController = new HomeController();
        ObservableList<Movie> allMovies = null;

        //when & then
        assertThrowsExactly(NullPointerException.class, () -> homeController.sortMoviesAscending(allMovies));
    }

    @Test
    void sort_null_movielist_descending_throws_exception()
    {
        //given
        HomeController homeController = new HomeController();
        ObservableList<Movie> allMovies = null;

        //when & then
        assertThrowsExactly(NullPointerException.class, () -> homeController.sortMoviesDescending(allMovies));
    }
    @Test
    void descending_movielist_correct_sorting_ascending()
    {
        //given
        HomeController homeController = new HomeController();
        ObservableList<Movie> allMovies = FXCollections.observableArrayList();
        allMovies.add(new Movie("1", "Dragon", Arrays.asList(Genre.ACTION, Genre.FAMILY), 2021, "Text 1", "", 168, Arrays.asList("Director 1"), Arrays.asList("Writer 1"), Arrays.asList("Actor 1"), 6.5));
        allMovies.add(new Movie("2", "Akanda", Arrays.asList(Genre.THRILLER), 2010, "Text 2", "", 138, Arrays.asList("Director 2"), Arrays.asList("Writer 2"), Arrays.asList("Actor 2"), 8.0));

        //when
        homeController.sortMoviesAscending(allMovies);

        //then
        assertEquals("Akanda", allMovies.get(0).getTitle());
        assertEquals("Dragon", allMovies.get(1).getTitle());
    }
    @Test
    void ascending_movielist_correct_sorting_descending()
    {
        //given
        HomeController homeController = new HomeController();
        ObservableList<Movie> allMovies = FXCollections.observableArrayList();
        allMovies.add(new Movie("1", "A quiet place", Arrays.asList(Genre.ACTION, Genre.FAMILY), 2021, "Text 1", "", 168, Arrays.asList("Director 1"), Arrays.asList("Writer 1"), Arrays.asList("Actor 1"), 6.5));
        allMovies.add(new Movie("2", "M3GAN", Arrays.asList(Genre.THRILLER), 2010, "Text 2", "", 138, Arrays.asList("Director 2"), Arrays.asList("Writer 2"), Arrays.asList("Actor 2"), 8.0));

        //when
        homeController.sortMoviesDescending(allMovies);

        //then
        assertEquals("M3GAN", allMovies.get(0).getTitle());
        assertEquals("A quiet place", allMovies.get(1).getTitle());
    }
    @Test
    void already_ascending_list_sorting_ascending_stays_same()
    {
        //given
        HomeController homeController = new HomeController();
        ObservableList<Movie> allMovies = FXCollections.observableArrayList();
        allMovies.add(new Movie("1", "Akanda", Arrays.asList(Genre.ACTION, Genre.FAMILY), 2021, "Text 1", "", 168, Arrays.asList("Director 1"), Arrays.asList("Writer 1"), Arrays.asList("Actor 1"), 6.5));
        allMovies.add(new Movie("2", "Dragon", Arrays.asList(Genre.THRILLER), 2010, "Text 2", "", 138, Arrays.asList("Director 2"), Arrays.asList("Writer 2"), Arrays.asList("Actor 2"), 8.0));

        //when
        homeController.sortMoviesAscending(allMovies);

        //then
        assertEquals("Akanda", allMovies.get(0).getTitle());
        assertEquals("Dragon", allMovies.get(1).getTitle());
    }
    @Test
    public void get_all_movies_from_API_with_no_filter()
    {
        //given
        HomeController homeController = new HomeController();
        ObservableList<Movie> allMovies = FXCollections.observableArrayList();

        //when
        homeController.filterMoviesAPI(allMovies,null,null,null,null);

        //then
        assertEquals(33,allMovies.size());
    }
    @Test
    public void get_all_movies_from_API_with_filter()
    {
        //given
        HomeController homeController = new HomeController();
        ObservableList<Movie> allMovies = FXCollections.observableArrayList();
        String query = "O";
        Genre genre = Genre.ALL_GENRES;
        String releaseYear = "2019";
        String ratingFrom = "6.6";

        //when
        homeController.filterMoviesAPI(allMovies, query, genre, releaseYear, ratingFrom);

        //then
        assertEquals(2, allMovies.size());
    }
}