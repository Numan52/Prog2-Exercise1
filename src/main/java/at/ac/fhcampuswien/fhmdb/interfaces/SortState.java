package at.ac.fhcampuswien.fhmdb.interfaces;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;

public interface SortState {
    void sortMovies(ObservableList<Movie> movies);
}
