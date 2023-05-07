package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.api.MovieAPI;
import at.ac.fhcampuswien.fhmdb.exceptions.MovieApiException;
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
    public void get_all_movies_from_API_with_no_filter(){
        //given
        HomeController homeController = new HomeController();
        ObservableList<Movie> allMovies = FXCollections.observableArrayList();

        //when
        homeController.filterMoviesAPI(allMovies,null,null,null,null);

        //then
        assertEquals(33,allMovies.size());
    }
    @Test
    public void get_all_movies_from_API_with_filter()  {
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
    @Test
    public void filter_movies_between_two_years_with_startyear_bigger_endyear_return_null() {
        //given
        HomeController homeController = new HomeController();
        ObservableList<Movie> allMovies = FXCollections.observableArrayList();
        allMovies.add(new Movie("1", "Akhanda", Arrays.asList(Genre.ACTION, Genre.FAMILY), 2021, "Text 1", "", 168, Arrays.asList("Director 1"), Arrays.asList("Writer 1"), Arrays.asList("Actor 1"), 6.5));
        allMovies.add(new Movie("2", "Shutter Island", Arrays.asList(Genre.THRILLER), 2010, "Text 2", "", 138, Arrays.asList("Director 2"), Arrays.asList("Writer 2"), Arrays.asList("Actor 2"), 8.0));
        allMovies.add(new Movie("3", "Ant-Man", Arrays.asList(Genre.ACTION, Genre.COMEDY), 2015, "Text 3", "", 115, Arrays.asList("Director 3"), Arrays.asList("Writer 3"), Arrays.asList("Actor 3"), 7.5));
        allMovies.add(new Movie("4", "6 Underground", Arrays.asList(Genre.THRILLER, Genre.ACTION), 2019, "Text 4", "", 128, Arrays.asList("Director 4"), Arrays.asList("Writer 4"), Arrays.asList("Actor 4"), 9.0));

        //when
        List<Movie> filteredMoviesByYear = homeController.getMoviesBetweenYears(allMovies, 2017, 2015);

        //then
        assertNull(filteredMoviesByYear);
    }
    @Test
    public void filter_movies_between_two_negativ_years_throws_exception() {
        //given
        HomeController homeController = new HomeController();
        ObservableList<Movie> allMovies = FXCollections.observableArrayList();
        allMovies.add(new Movie("1", "Akhanda", Arrays.asList(Genre.ACTION, Genre.FAMILY), 2021, "Text 1", "", 168, Arrays.asList("Director 1"), Arrays.asList("Writer 1"), Arrays.asList("Actor 1"), 6.5));
        allMovies.add(new Movie("2", "Shutter Island", Arrays.asList(Genre.THRILLER), 2010, "Text 2", "", 138, Arrays.asList("Director 2"), Arrays.asList("Writer 2"), Arrays.asList("Actor 2"), 8.0));
        allMovies.add(new Movie("3", "Ant-Man", Arrays.asList(Genre.ACTION, Genre.COMEDY), 2015, "Text 3", "", 115, Arrays.asList("Director 3"), Arrays.asList("Writer 3"), Arrays.asList("Actor 3"), 7.5));
        allMovies.add(new Movie("4", "6 Underground", Arrays.asList(Genre.THRILLER, Genre.ACTION), 2019, "Text 4", "", 128, Arrays.asList("Director 4"), Arrays.asList("Writer 4"), Arrays.asList("Actor 4"), 9.0));

        //when & then
        assertThrowsExactly(IllegalArgumentException.class, () -> homeController.getMoviesBetweenYears(allMovies, -201, -2012));
    }

    @Test
    public void filter_movies_with_movies_not_between_two_years_return_empty_list() {
        //given
        HomeController homeController = new HomeController();
        ObservableList<Movie> allMovies = FXCollections.observableArrayList();
        allMovies.add(new Movie("1", "Akhanda", Arrays.asList(Genre.ACTION, Genre.FAMILY), 2021, "Text 1", "", 168, Arrays.asList("Director 1"), Arrays.asList("Writer 1"), Arrays.asList("Actor 1"), 6.5));
        allMovies.add(new Movie("2", "Shutter Island", Arrays.asList(Genre.THRILLER), 2010, "Text 2", "", 138, Arrays.asList("Director 2"), Arrays.asList("Writer 2"), Arrays.asList("Actor 2"), 8.0));
        allMovies.add(new Movie("3", "Ant-Man", Arrays.asList(Genre.ACTION, Genre.COMEDY), 2015, "Text 3", "", 115, Arrays.asList("Director 3"), Arrays.asList("Writer 3"), Arrays.asList("Actor 3"), 7.5));
        allMovies.add(new Movie("4", "6 Underground", Arrays.asList(Genre.THRILLER, Genre.ACTION), 2019, "Text 4", "", 128, Arrays.asList("Director 4"), Arrays.asList("Writer 4"), Arrays.asList("Actor 4"), 9.0));

        //when
        List<Movie> filteredMoviesByYear = homeController.getMoviesBetweenYears(allMovies, 2005, 2009);

        //then
        assertEquals(0, filteredMoviesByYear.size());
    }
    @Test
    public void get_Most_Popular_Actor_from_movie_list()
    {
        //given
        HomeController homeController = new HomeController();
        ObservableList<Movie> allMovies = FXCollections.observableArrayList();
        allMovies.add(new Movie("1", "Akhanda", Arrays.asList(Genre.ACTION, Genre.FAMILY), 2021, "Text 1", "", 168, Arrays.asList("Director 1"), Arrays.asList("Writer 1"), Arrays.asList("Actor 1"), 6.5));
        allMovies.add(new Movie("2", "Shutter Island", Arrays.asList(Genre.THRILLER), 2010, "Text 2", "", 138, Arrays.asList("Director 2"), Arrays.asList("Writer 2"), Arrays.asList("Actor 2"), 8.0));
        allMovies.add(new Movie("3", "Ant-Man", Arrays.asList(Genre.ACTION, Genre.COMEDY), 2015, "Text 3", "", 115, Arrays.asList("Director 3"), Arrays.asList("Writer 3"), Arrays.asList("Actor 3", "Actor 1"), 7.5));
        allMovies.add(new Movie("4", "6 Underground", Arrays.asList(Genre.THRILLER, Genre.ACTION), 2019, "Text 4", "", 128, Arrays.asList("Director 4"), Arrays.asList("Writer 4"), Arrays.asList("Actor 4", "Actor 1"), 9.0));

        //when
        String result = homeController.getMostPopularActor(allMovies);

        //then
        assertEquals("Actor 1", result);
    }
    @Test
    void testCountMoviesFrom() {
        //given
        HomeController homeController = new HomeController();
        ObservableList<Movie> allMovies = FXCollections.observableArrayList();

        allMovies.add(new Movie("1", "Akhanda", Arrays.asList(Genre.ACTION, Genre.FAMILY), 2021, "Text 1", "", 168, Arrays.asList("Director 1"), Arrays.asList("Writer 1"), Arrays.asList("Actor 1"), 6.5));
        allMovies.add(new Movie("2", "Shutter Island", Arrays.asList(Genre.THRILLER), 2010, "Text 2", "", 138, Arrays.asList("Director 2", "Director 1"), Arrays.asList("Writer 2"), Arrays.asList("Actor 2"), 8.0));
        allMovies.add(new Movie("3", "Ant-Man", Arrays.asList(Genre.ACTION, Genre.COMEDY), 2015, "Text 3", "", 115, Arrays.asList("Director 3"), Arrays.asList("Writer 3"), Arrays.asList("Actor 3"), 7.5));
        allMovies.add(new Movie("4", "6 Underground", Arrays.asList(Genre.THRILLER, Genre.ACTION), 2019, "Text 4", "", 128, Arrays.asList("Director 4"), Arrays.asList("Writer 4"), Arrays.asList("Actor 4"), 9.0));
        String director = "Director 1";

        //when
        long result = homeController.countMoviesFrom(allMovies, director);

        //then
        assertEquals(2L, result);
    }
    @Test
    public void get_one_movie_from_API_with_valid_id() {
        //given
        String id = "4e1920ba-6963-4035-ac76-22baa7881111";

        //when
        Movie oneMovie = MovieAPI.getOneMovie(id);

        //then
        assertEquals("The Dark Knight", oneMovie.getTitle());
    }
    @Test
    public void get_one_movie_from_API_with_invalid_id(){
        //given
        String id = "4";

        //when
        Movie oneMovie = MovieAPI.getOneMovie(id);

        //then
        assertNull(oneMovie);
    }

    @Test
    public void get_longest_movie_title_from_movie_list_with_4_movies(){
        //given
        HomeController homeController = new HomeController();
        ObservableList<Movie> allMovies = FXCollections.observableArrayList();
        allMovies.add(new Movie("1", "Akhanda", Arrays.asList(Genre.ACTION, Genre.FAMILY), 2021, "Text 1", "", 168, Arrays.asList("Director 1"), Arrays.asList("Writer 1"), Arrays.asList("Actor 1"), 6.5));
        allMovies.add(new Movie("2", "Shutter Island", Arrays.asList(Genre.THRILLER), 2010, "Text 2", "", 138, Arrays.asList("Director 2"), Arrays.asList("Writer 2"), Arrays.asList("Actor 2"), 8.0));
        allMovies.add(new Movie("3", "Ant-Man", Arrays.asList(Genre.ACTION, Genre.COMEDY), 2015, "Text 3", "", 115, Arrays.asList("Director 3"), Arrays.asList("Writer 3"), Arrays.asList("Actor 3"), 7.5));
        allMovies.add(new Movie("4", "6 Underground", Arrays.asList(Genre.THRILLER, Genre.ACTION), 2019, "Text 4", "", 128, Arrays.asList("Director 4"), Arrays.asList("Writer 4"), Arrays.asList("Actor 4"), 9.0));

        //when
        int result = homeController.getLongestMovieTitle(allMovies);

        //then
        assertEquals(14, result);
    }

    @Test
    public void get_longest_movie_title_with_null_list_throws_exception(){
        //given
        HomeController homeController = new HomeController();
        ObservableList<Movie> allMovies = null;

        //when & then
        assertThrowsExactly(NullPointerException.class, () -> homeController.getLongestMovieTitle(allMovies));
    }

    @Test
    public void get_longest_movie_title_with_empty_list_return_0(){
        //given
        HomeController homeController = new HomeController();
        ObservableList<Movie> allMovies = FXCollections.observableArrayList();

        //when
        int result = homeController.getLongestMovieTitle(allMovies);

        //then
        assertEquals(0, result);
    }

    @Test
    public void filter_movies_between_two_valid_years() {
        //given
        HomeController homeController = new HomeController();
        ObservableList<Movie> allMovies = FXCollections.observableArrayList();
        allMovies.add(new Movie("1", "Akhanda", Arrays.asList(Genre.ACTION, Genre.FAMILY), 2021, "Text 1", "", 168, Arrays.asList("Director 1"), Arrays.asList("Writer 1"), Arrays.asList("Actor 1"), 6.5));
        allMovies.add(new Movie("2", "Shutter Island", Arrays.asList(Genre.THRILLER), 2010, "Text 2", "", 138, Arrays.asList("Director 2"), Arrays.asList("Writer 2"), Arrays.asList("Actor 2"), 8.0));
        allMovies.add(new Movie("3", "Ant-Man", Arrays.asList(Genre.ACTION, Genre.COMEDY), 2015, "Text 3", "", 115, Arrays.asList("Director 3"), Arrays.asList("Writer 3"), Arrays.asList("Actor 3"), 7.5));
        allMovies.add(new Movie("4", "6 Underground", Arrays.asList(Genre.THRILLER, Genre.ACTION), 2019, "Text 4", "", 128, Arrays.asList("Director 4"), Arrays.asList("Writer 4"), Arrays.asList("Actor 4"), 9.0));

        //when
        List<Movie> filteredMoviesByYear = homeController.getMoviesBetweenYears(allMovies, 2005, 2015);

        //then
        assertEquals(2, filteredMoviesByYear.size());
        /*
        for (Movie movie : filteredMoviesByYear) {
            assertTrue(movie.getReleaseYear() >= 2005 && movie.getReleaseYear() <= 2015);
        }
        */
    }

    @Test
    public void filter_movies_between_two_years_with_null_list_throws_exception() {
        //given
        HomeController homeController = new HomeController();
        ObservableList<Movie> allMovies = null;

        //when & then
        assertThrowsExactly(NullPointerException.class, () -> homeController.getMoviesBetweenYears(allMovies, 2005, 2015));
    }

    @Test
    public void get_Most_Popular_Actor_with_null_list_throws_exception()
    {
        //given
        HomeController homeController = new HomeController();
        ObservableList<Movie> allMovies = null;

        //when & then
        assertThrowsExactly(NullPointerException.class, () -> homeController.getMostPopularActor(allMovies));
    }
    @Test
    public void get_Most_Popular_Actor_with_each_Actor_only_in_one_movie_returns_movie_Actor_from_last_movie()
    {
        //given
        HomeController homeController = new HomeController();
        ObservableList<Movie> allMovies = FXCollections.observableArrayList();
        allMovies.add(new Movie("1", "Akhanda", Arrays.asList(Genre.ACTION, Genre.FAMILY), 2021, "Text 1", "", 168, Arrays.asList("Director 1"), Arrays.asList("Writer 1"), Arrays.asList("Actor 1"), 6.5));
        allMovies.add(new Movie("2", "Shutter Island", Arrays.asList(Genre.THRILLER), 2010, "Text 2", "", 138, Arrays.asList("Director 2"), Arrays.asList("Writer 2"), Arrays.asList("Actor 2"), 8.0));
        allMovies.add(new Movie("3", "Ant-Man", Arrays.asList(Genre.ACTION, Genre.COMEDY), 2015, "Text 3", "", 115, Arrays.asList("Director 3"), Arrays.asList("Writer 3"), Arrays.asList("Actor 3"), 7.5));
        allMovies.add(new Movie("4", "6 Underground", Arrays.asList(Genre.THRILLER, Genre.ACTION), 2019, "Text 4", "", 128, Arrays.asList("Director 4"), Arrays.asList("Writer 4"), Arrays.asList("Actor 4"), 9.0));
        allMovies.add(new Movie("5", "7 Overwatch", Arrays.asList(Genre.THRILLER, Genre.ACTION), 2019, "Text 5", "", 128, Arrays.asList("Director 5"), Arrays.asList("Writer 5"), Arrays.asList("Actor 5"), 9.0));

        //when
        String result = homeController.getMostPopularActor(allMovies);

        //then
        assertEquals("Actor 5", result);
    }

    @Test
    public void get_Most_Popular_Actor_with_empty_list_returns_null()
    {
        //given
        HomeController homeController = new HomeController();
        ObservableList<Movie> allMovies = FXCollections.observableArrayList();

        //when
        String result = homeController.getMostPopularActor(allMovies);

        //then
        assertNull(result);
    }
}

