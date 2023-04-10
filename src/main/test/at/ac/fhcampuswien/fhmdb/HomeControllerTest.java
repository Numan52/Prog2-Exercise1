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
        HomeController homeController = new HomeController();
        ObservableList<Movie> allMovies = null;
        //when & then
        assertThrowsExactly(NullPointerException.class, () -> homeController.sortMoviesAscending(allMovies));
    }

    @Test
    void sort_null_movielist_descending_throws_exception()
    {
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
        ObservableList<Movie> allmovies = FXCollections.observableArrayList();
        allmovies.add(new Movie("Dragon","Text 1", Arrays.asList(Genre.ACTION,Genre.COMEDY))); //d
        allmovies.add(new Movie("Akanda","Text 2", Arrays.asList(Genre.ACTION,Genre.FAMILY))); //a
        //when
        homeController.sortMoviesAscending(allmovies);
        //then
        assertEquals("Akanda", allmovies.get(0).getTitle()); //a
        assertEquals("Dragon", allmovies.get(1).getTitle()); //d
    }
    @Test
    void ascending_movielist_correct_sorting_descending()
    {
        //given
        HomeController homeController = new HomeController();
        ObservableList<Movie> allmovies = FXCollections.observableArrayList();
        allmovies.add(new Movie("A quiet place","Text 1", Arrays.asList(Genre.HORROR,Genre.ADVENTURE))); //a
        allmovies.add(new Movie("M3GAN","Text 2", Arrays.asList(Genre.HORROR,Genre.THRILLER))); //m
        //when
        homeController.sortMoviesDescending(allmovies);
        //then
        assertEquals("M3GAN", allmovies.get(0).getTitle()); //m
        assertEquals("A quiet place", allmovies.get(1).getTitle()); //a
    }
    @Test
    void already_ascending_list_sorting_ascending_stays_same()
    {
        //given
        HomeController homeController = new HomeController();
        ObservableList<Movie> allmovies = FXCollections.observableArrayList();
        allmovies.add(new Movie("Akanda","Text 1", Arrays.asList(Genre.ACTION,Genre.FAMILY)));
        allmovies.add(new Movie("Dragon","Text 2", Arrays.asList(Genre.ACTION,Genre.COMEDY)));
        //when
        homeController.sortMoviesAscending(allmovies);
        //then
        assertEquals("Akanda", allmovies.get(0).getTitle());
        assertEquals("Dragon", allmovies.get(1).getTitle());
    }

    @Test
    public void Give_The_Longest_Movie_Title() {
        //given
        HomeController homeController = new HomeController();
        ObservableList<Movie> allmovies = FXCollections.observableArrayList();
        allmovies.add(new Movie("Akanda","Text 1", Arrays.asList(Genre.ACTION,Genre.FAMILY)));
        allmovies.add(new Movie("Dragon","Text 2", Arrays.asList(Genre.ACTION,Genre.COMEDY)));
        allmovies.add(new Movie("M3GAN","Text 2", Arrays.asList(Genre.HORROR,Genre.THRILLER)));
        allmovies.add(new Movie("A quiet place","Text 1", Arrays.asList(Genre.HORROR,Genre.ADVENTURE)));
        //when
        int result = homeController.getLongestMovieTitle(allmovies);
        //then
        assertEquals(13, result);
    }


    @Test
    public void Filter_Movies_Between_Two_Years() {
        //given
        HomeController MovieAPI = new HomeController();
        ObservableList<Movie> allmovies = FXCollections.observableArrayList();
        allmovies.add(new Movie("1", "Akhanda", Arrays.asList(Genre.ACTION, Genre.FAMILY), 2021, "Text 1", "", 168, Arrays.asList("Director 1"), Arrays.asList("Writer 1"), Arrays.asList("Actor 1"), 6.5));
        allmovies.add(new Movie("2", "Shutter Island", Arrays.asList(Genre.THRILLER), 2010, "Text 2", "", 138, Arrays.asList("Director 2"), Arrays.asList("Writer 2"), Arrays.asList("Actor 2"), 8.0));
        allmovies.add(new Movie("3", "Ant-Man", Arrays.asList(Genre.ACTION, Genre.COMEDY), 2015, "Text 3", "", 115, Arrays.asList("Director 3"), Arrays.asList("Writer 3"), Arrays.asList("Actor 3"), 7.5));
        allmovies.add(new Movie("4", "6 Underground", Arrays.asList(Genre.THRILLER, Genre.ACTION), 2019, "Text 4", "", 128, Arrays.asList("Director 4"), Arrays.asList("Writer 4"), Arrays.asList("Actor 4"), 9.0));

        //when
        List<Movie> filteredMoviesByYear = MovieAPI.getMoviesBetweenYears(allmovies, 2005, 2015);

        //then
        assertEquals(2, filteredMoviesByYear.size());
        for (Movie movie : filteredMoviesByYear) {
            int releaseYear = movie.getReleaseYear();
            assertEquals(true, releaseYear >= 2005 && releaseYear <= 2015);
        }
    }



    /*
    @Test
    void filter_list_after_seachtext_Fire_no_genre_finds_movie_with_Fire_in_description()
    {
        //given
        HomeController homeController = new HomeController();
        ObservableList<Movie> allMovies = FXCollections.observableArrayList(
                new Movie("Blair Witch", "Description A", Arrays.asList(Genre.HORROR, Genre.MYSTERY, Genre.THRILLER)),
                new Movie("Free Guy", "Description Fire", Arrays.asList(Genre.COMEDY, Genre.ADVENTURE)),
                new Movie("Avengers: Infinity War","Description C", Arrays.asList(Genre.THRILLER, Genre.ACTION)));
        String searchText = "fire";
        Genre genre = null;
        //when
        homeController.filterMovies(allMovies, searchText, genre);
        //then
        assertTrue(allMovies.get(0).getDescription().contains("Fire"));
        assertEquals(1,allMovies.size());
    }

    @Test
    void filter_null_list_with_no_searchtext_and_no_genre_throw_exception_for_null_list()
    {
        //given
        HomeController homeController = new HomeController();
        ObservableList<Movie> allMovies = null;
        String searchText = null;
        Genre genre = null;
        //when & then
        assertThrowsExactly(NullPointerException.class, () -> homeController.filterMovies(allMovies,searchText,genre));
    }

    @Test
    void filter_empty_list_with_searchtext_and_genre_allmovies_doesnt_throw_exception()
    {
        //given
        HomeController homeController = new HomeController();
        ObservableList<Movie> allMovies = FXCollections.observableArrayList();
        String searchText = "A";
        Genre genre = Genre.ALL_GENRES;
        //when & then
        assertDoesNotThrow(() -> homeController.filterMovies(allMovies, searchText, genre));
    }

    @Test
    void testFilterMovies_noSearchText_allGenres() {
        //given
        HomeController homeController = new HomeController();
        ObservableList<Movie> allMovies = FXCollections.observableArrayList(
                new Movie("King Kong = best movie", "Description A", Arrays.asList(Genre.ACTION, Genre.ADVENTURE, Genre.THRILLER)),
                new Movie("Movie B", "Description B", Arrays.asList(Genre.DRAMA, Genre.ROMANCE)),
                new Movie("Movie C","Description C", Collections.singletonList(Genre.THRILLER)));

        String searchText = null;
        Genre genre = Genre.ALL_GENRES;
        //when
        homeController.filterMovies(allMovies, searchText, genre);
        //then
        assertEquals(3, allMovies.size());
    }
    @Test
    void testFilterMovies_searchText_part_of_movie_name_and_Genre_Horror_finds_exact_movie(){
        //given
        HomeController homeController = new HomeController();
        ObservableList<Movie> allMovies = FXCollections.observableArrayList(
                new Movie("Blair Witch", "Description A", Arrays.asList(Genre.HORROR, Genre.MYSTERY, Genre.THRILLER)),
                new Movie("Free Guy", "Description B", Arrays.asList(Genre.COMEDY, Genre.ADVENTURE)),
                new Movie("Avengers: Infinity War","Description C", Arrays.asList(Genre.THRILLER, Genre.ACTION)));
        String searchText = "witch";
        Genre genre = Genre.HORROR;
        //when
        homeController.filterMovies(allMovies, searchText, genre);
        //then
        assertEquals( "Blair Witch", allMovies.get(0).getTitle());
        assertTrue(allMovies.get(0).getGenres().contains(Genre.HORROR.toString()));
    }

    @Test
    void testFilterMovies_with_searchText_Free_with_Genre_null_finds_movie(){
        //given
        HomeController homeController = new HomeController();
        ObservableList<Movie> allMovies = FXCollections.observableArrayList(
                new Movie("Blair Witch", "Description A", Arrays.asList(Genre.HORROR, Genre.MYSTERY, Genre.THRILLER)),
                new Movie("Free Guy", "Description B", Arrays.asList(Genre.COMEDY, Genre.ADVENTURE)),
                new Movie("Avengers: Infinity War","Description C", Arrays.asList(Genre.THRILLER, Genre.ACTION)));
        String searchText = "Free";
        Genre genre = null;
        //when
        homeController.filterMovies(allMovies, searchText, genre);
        //then
        assertEquals("Free Guy",allMovies.get(0).getTitle());
    }
*/
}