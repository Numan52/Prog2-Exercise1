package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

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

}