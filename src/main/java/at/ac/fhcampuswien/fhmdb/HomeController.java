package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.api.MovieAPI;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView movieListView;

    @FXML
    public JFXComboBox genreComboBox;

    @FXML
    public JFXComboBox releaseyearComboBox;

    @FXML
    public JFXComboBox ratingComboBox;

    @FXML
    public JFXButton sortBtn;

    public JFXButton resetBtn;

    public List<Movie> allMovies = Movie.initializeMovies();

    private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try
        {
       observableMovies.addAll(MovieAPI.getAllMovies());
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
            observableMovies.addAll(Movie.initializeMovies());
        }
        // add dummy data to observable list
        // initialize UI stuff
        movieListView.setItems(observableMovies);   // set data of observable list to list view
        movieListView.setCellFactory(movieListView -> new MovieCell()); // use custom cell factory to display data

        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().addAll(Genre.values()); //add all genres to combobox

        List<Double> allratings = observableMovies.stream()
                .map(Movie::getRating)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        ratingComboBox.getItems().addAll(allratings);

        List<Integer> allreleaseyears = observableMovies.stream()
                .map(Movie::getReleaseYear)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        releaseyearComboBox.getItems().addAll(allreleaseyears);


        // add event handlers to buttons and call the regarding methods
        // either set event handlers in the fxml file (onAction) or add them here

        //Sort Button:
        sortBtn.setOnAction(actionEvent -> {
            if(sortBtn.getText().equals("Sort (asc)") || sortBtn.getText().equals("Sort")) {
                //sort observableMovies ascending a-z
                sortMoviesAscending(observableMovies);
                sortBtn.setText("Sort (desc)");
            } else {
                //sort observableMovies descending z-a
                sortMoviesDescending(observableMovies);
                sortBtn.setText("Sort (asc)");
            }
        });

        //Reset Button:
        resetBtn.setOnAction(actionEvent -> {
            searchField.setText("");
            genreComboBox.setValue(null);
            releaseyearComboBox.setValue(null);
            ratingComboBox.setValue(null);
            observableMovies.clear();
            observableMovies.addAll(MovieAPI.getAllMovies());
            sortBtn.setText("Sort");
        });

        //Search Button:
        searchBtn.setOnAction(actionEvent -> { //click on search button
            observableMovies.clear();

            if (genreComboBox.getValue() == null) //if no genre is selected its auto. All_genres
            {
                genreComboBox.setValue(Genre.ALL_GENRES);
            }
            String releaseYear = (releaseyearComboBox.getValue() != null) ? releaseyearComboBox.getValue().toString() : null;
            String rating = (ratingComboBox.getValue() != null) ? ratingComboBox.getValue().toString() : null;


            filterMoviesAPI(observableMovies, searchField.getText(), (Genre) genreComboBox.getValue(),releaseYear,rating);

            //stick with the sorting order selected before
            if(sortBtn.getText().equals("Sort (asc)")) {
                sortMoviesDescending(observableMovies);
            } else if(sortBtn.getText().equals("Sort (desc)")){
                sortMoviesAscending(observableMovies);
            }
        });

    }

    /*
    creates a single stream of actors from a list of movies,
    groups the actors by name and counts their occurrences,x
    finds the actor with the highest count, and returns their name as a String
    */
    public String getMostPopularActor(ObservableList<Movie> movies){
        if(movies != null)
        {
            return movies.stream()
                    .flatMap(movie -> movie.getMainCast().stream())
                    .collect(Collectors.groupingBy(actor -> actor, Collectors.counting()))
                    .entrySet().stream()
                    .max((entry1, entry2) -> Long.compare(entry1.getValue(), entry2.getValue()))
                    .map(entry -> entry.getKey())
                    .orElse(null);
        }
        else
        {
            throw new NullPointerException("List is null!");
        }
    }


    /*
    maps each Movie object to its title length as an int,
    finds the maximum title length in the stream of title lengths,
    returns 0 if the stream is empty (i.e., if the input list is empty) and there is no maximum title length.
    */
    public int getLongestMovieTitle(ObservableList<Movie> movies){
        if(movies != null) {
            return movies.stream()
                    .mapToInt(movie -> movie.getTitle().length())
                    .max()
                    .orElse(0);
        }
        else
        {
            throw new NullPointerException("List is null!");
        }
    }

    public List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear) {
        if(movies != null)
        {
            if(startYear >= 0 && endYear >= 0 )
            {
                if(startYear <= endYear)
                {
                    return movies.stream()
                            .filter(movie -> movie.getReleaseYear() >= startYear && movie.getReleaseYear() <= endYear)
                            .collect(Collectors.toList());
                }
                System.err.println("Start-year is bigger than End-year!");
                return null;
            }
            else {
                throw new IllegalArgumentException("Start-year or End-year is negativ");
            }
        }
        else
        {
            throw new NullPointerException("List is null!");
        }
    }


    public long countMoviesFrom(List<Movie> movies, String director) {
        if(movies != null) {
            if(director != null) {
                long counter = movies.stream()
                        .filter(movie -> movie.getDirectors().contains(director))
                        .count();
                return counter;
            } else {
                throw new IllegalArgumentException("Director is null");
            }
        } else {
            throw new NullPointerException("List is null!");
        }
    }

    public void sortMoviesAscending(ObservableList<Movie> allMovies) {
        if(allMovies == null)
        {
            throw new NullPointerException("List is null");
        }
        allMovies.sort(Comparator.comparing(Movie::getTitle));

    }
    public void sortMoviesDescending(ObservableList<Movie> allMovies) {
        if(allMovies == null)
        {
            throw new NullPointerException("List is null");
        }
        allMovies.sort(Comparator.comparing(Movie::getTitle).reversed());
    }

    public void filterMoviesAPI(ObservableList<Movie> movies, String searchText, Genre genre, String releaseYear, String rating)
    {
        if(movies != null)
        {
            movies.addAll(MovieAPI.getAllMovies(searchText, genre, releaseYear, rating));
        }
        else {
            throw new NullPointerException("List is null!");
        }
    }

}