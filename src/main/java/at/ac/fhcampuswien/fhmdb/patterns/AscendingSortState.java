package at.ac.fhcampuswien.fhmdb.patterns;

import at.ac.fhcampuswien.fhmdb.interfaces.SortState;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;

import java.util.Comparator;

public class AscendingSortState implements SortState {
    @Override
    public void sortMovies(ObservableList<Movie> movies) {
        if(movies != null)
        {
            movies.sort(Comparator.comparing(Movie::getTitle));
        }
        else
        {
            throw new NullPointerException("List is null");
        }

    }
}
