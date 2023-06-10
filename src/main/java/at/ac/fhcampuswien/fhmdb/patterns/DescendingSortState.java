package at.ac.fhcampuswien.fhmdb.patterns;

import at.ac.fhcampuswien.fhmdb.interfaces.SortState;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;

import java.util.Comparator;

public class DescendingSortState implements SortState {
    @Override
    public void sortMovies(ObservableList<Movie> movies) {
        if(movies != null)
        {
            movies.sort(Comparator.comparing(Movie::getTitle).reversed());
        }
        else
        {
            throw new NullPointerException("List is null");
        }

    }
}
