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
    void list_with_movie_title_letter_d_first_and_a_later_correct_sorting_ascending()
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
    void list_with_movie_title_letter_a_first_and_m_later_correct_sorting_descending()
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
    void testFilterMovies_noSearchText_allGenres() {
        //given
        HomeController controller = new HomeController();
        ObservableList<Movie> allMovies = FXCollections.observableArrayList(
                new Movie("King Kong = best movie", "Description A", Arrays.asList(Genre.ACTION, Genre.ADVENTURE, Genre.THRILLER)),
                new Movie("Movie B", "Description B", Arrays.asList(Genre.DRAMA, Genre.ROMANCE)),
                new Movie("Movie C","Description C", Collections.singletonList(Genre.THRILLER)));

        String searchText = null;
        Genre genre = Genre.ALL_GENRES;
        //when
        controller.filterMovies(allMovies, searchText, genre);
        //then
        assertEquals(3, allMovies.size());
    }
    @Test
    void testFilterMovies_with_searchText_of_movie_in_list_and_Genre_Horror(){
        //given
        HomeController controller = new HomeController();
        ObservableList<Movie> allMovies = FXCollections.observableArrayList(
                new Movie("Blair Witch", "Description A", Arrays.asList(Genre.HORROR, Genre.MYSTERY, Genre.THRILLER)),
                new Movie("Free Guy", "Description B", Arrays.asList(Genre.COMEDY, Genre.ADVENTURE)),
                new Movie("Avengers: Infinity War","Description C", Arrays.asList(Genre.THRILLER, Genre.ACTION)));
        String searchText = "witch";
        Genre genre = Genre.HORROR;
        //when
        controller.filterMovies(allMovies, searchText, genre);
        //then
        assertEquals( "Blair Witch", allMovies.get(0).getTitle());
    }

    @Test
    void testFilterMovies_with_searchText_of_movie_in_list_with_No_Genre(){
        //given
        HomeController controller = new HomeController();
        ObservableList<Movie> allMovies = FXCollections.observableArrayList(
                new Movie("Blair Witch", "Description A", Arrays.asList(Genre.HORROR, Genre.MYSTERY, Genre.THRILLER)),
                new Movie("Free Guy", "Description B", Arrays.asList(Genre.COMEDY, Genre.ADVENTURE)),
                new Movie("Avengers: Infinity War","Description C", Arrays.asList(Genre.THRILLER, Genre.ACTION)));
        String searchText = "Free";
        Genre genre = null;
        //when
        controller.filterMovies(allMovies, searchText, genre);
        //then
        assertEquals("Free Guy",allMovies.get(0).getTitle());
    }

    @Test
    void testFilterMovies_Genre_ascending(){
        //given
        HomeController controller = new HomeController();
        ObservableList<Movie> allMovies = FXCollections.observableArrayList(
                new Movie("Divergent", "Description A", Arrays.asList(Genre.ADVENTURE, Genre.MYSTERY, Genre.ACTION)),
                new Movie("Titanic", "Description B", Arrays.asList(Genre.DRAMA, Genre.ROMANCE)),
                new Movie("Avengers: Infinity War","Description C", Arrays.asList(Genre.ADVENTURE, Genre.ACTION)));
        String searchText = null;
        Genre genre = Genre.ADVENTURE;
        //when
        controller.sortMoviesAscending(allmovies, searchText, genre);
        //then
        assertEquals("Avengers: Infinity War", allmovies.get(0).getTitle());
        assertEquals("Divergent", allmovies.get(1).getTitle());
    }
}