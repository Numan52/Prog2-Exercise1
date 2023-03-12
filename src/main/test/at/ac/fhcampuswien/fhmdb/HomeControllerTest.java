package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {

    @Test
    void list_with_movie_title_letter_d_first_and_a_later_correct_sorting_ascending_a_to_d()
    {
        //given
        HomeController homeController = new HomeController();
        ObservableList<Movie> allmovies = FXCollections.observableArrayList();
        allmovies.add(new Movie("Dragon","skr", Arrays.asList(Genre.ACTION,Genre.COMEDY))); //d
        allmovies.add(new Movie("Akanda","skr", Arrays.asList(Genre.ACTION,Genre.FAMILY))); //a
        //when
        homeController.sortMoviesAscending(allmovies);
        //then
        assertEquals("Akanda", allmovies.get(0).getTitle()); //a
        assertEquals("Dragon", allmovies.get(1).getTitle()); //d
    }
    @Test
    void testFilterMovies_noSearchText_allGenres() {
        //given
        HomeController controller = new HomeController();
        ObservableList<Movie> allMovies = FXCollections.observableArrayList(
                new Movie("King Kong = best movie", "Description A", Arrays.asList(Genre.ACTION, Genre.ADVENTURE, Genre.THRILLER)),
                new Movie("Movie B", "Description B", Arrays.asList(Genre.DRAMA, Genre.ROMANCE)),
                new Movie("Movie C","Description C", Arrays.asList(Genre.THRILLER)));

        String searchText = null;
        Genre genre = Genre.ALL_GENRES;
        //when
        controller.filterMovies(allMovies, searchText, genre);
        //then
        assertEquals(3, allMovies.size());
    }
    @Test
    void testFilterMovies_searchText_Horror_Genre(){
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
        assertEquals(allMovies.get(0).getTitle(), "Blair Witch");
    }

}