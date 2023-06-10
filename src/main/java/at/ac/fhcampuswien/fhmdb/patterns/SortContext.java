package at.ac.fhcampuswien.fhmdb.patterns;

import at.ac.fhcampuswien.fhmdb.interfaces.SortState;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;

public class SortContext {

    private SortState currentState;

    public SortContext() {
        currentState = new DefaultSortState();
    }

    public void setSortState(SortState state) {
        currentState = state;
    }

    public void sortMovies(ObservableList<Movie> movies) {
        currentState.sortMovies(movies);
    }
}
