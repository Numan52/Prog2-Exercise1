package at.ac.fhcampuswien.fhmdb.interfaces;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;

public interface SortState { // each sorting functionality gets its own class which implements this interface
    void sortMovies(ObservableList<Movie> movies);
}
