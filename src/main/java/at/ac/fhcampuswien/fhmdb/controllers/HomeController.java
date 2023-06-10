package at.ac.fhcampuswien.fhmdb.controllers;

import at.ac.fhcampuswien.fhmdb.FhmdbApplication;
import at.ac.fhcampuswien.fhmdb.api.MovieAPI;
import at.ac.fhcampuswien.fhmdb.database.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.database.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.exceptions.MovieApiException;
import at.ac.fhcampuswien.fhmdb.interfaces.ClickEventHandler;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.patterns.AscendingSortState;
import at.ac.fhcampuswien.fhmdb.patterns.DefaultSortState;
import at.ac.fhcampuswien.fhmdb.patterns.DescendingSortState;
import at.ac.fhcampuswien.fhmdb.patterns.SortContext;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import at.ac.fhcampuswien.fhmdb.ui.UIAlerts;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
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

    @FXML
    public VBox vBox;
    public JFXButton resetBtn;

    public JFXButton watchlistBtn;
    public List<Movie> allMovies = Movie.initializeMovies();
    public WatchlistRepository watchlistRepository = new WatchlistRepository();
    private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes

    private SortContext sortContext = new SortContext();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try
        {
            observableMovies.addAll(MovieAPI.getAllMovies());
        }
        catch(MovieApiException e)
        {
            UIAlerts.errormessage("An Error Occurred While Loading Movies");
            //observableMovies.addAll(Movie.initializeMovies());
        }
        // add dummy data to observable list
        // initialize UI stuff
        movieListView.setItems(observableMovies);   // set data of observable list to list view
        movieListView.setCellFactory(movieListView -> new MovieCell(onAddToWatchlistClicked)); // use custom cell factory to display data

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
                sortContext.setSortState(new AscendingSortState());
                sortContext.sortMovies(observableMovies);
                sortBtn.setText("Sort (desc)");
            } else {
                //sort observableMovies descending z-a
                sortContext.setSortState(new DescendingSortState());
                sortContext.sortMovies(observableMovies);
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
            //observableMovies.addAll(MovieAPI.getAllMovies());
            try {
                observableMovies.addAll(MovieAPI.getAllMovies());
            } catch (MovieApiException e) {
                UIAlerts.errormessage("An Error Occurred While Loading Movies");
            }
            sortContext.setSortState(new DefaultSortState());
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
            sortContext.sortMovies(observableMovies);
        });

        watchlistBtn.setOnAction(actionEvent -> {
            FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("watchlist.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 890, 620);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = (Stage)vBox.getScene().getWindow();
            scene.getStylesheets().add(Objects.requireNonNull(FhmdbApplication.class.getResource("styles.css")).toExternalForm());
            stage.setTitle("Watchlist");
            stage.setScene(scene);
            stage.show();
        });

    }

    private final ClickEventHandler onAddToWatchlistClicked = (clickedItem) ->
    {
        if(clickedItem instanceof Movie)
        {
            try {
                watchlistRepository.addToWatchlist(transformMovieToMovieEntity((Movie)clickedItem));
            } catch(DatabaseException e) {
                UIAlerts.errormessage("An Error Occurred While Loading Movies");
            }
        }

    };

    public  WatchlistMovieEntity transformMovieToMovieEntity(Movie movie)
    {

        return new WatchlistMovieEntity(movie.getId(),movie.getTitle(),movie.getDescription(),movie.getGenres(),movie.getReleaseYear(),movie.getImgUrl(),movie.getLengthInMinutes(),movie.getRating());
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

    public void filterMoviesAPI(ObservableList<Movie> movies, String searchText, Genre genre, String releaseYear, String rating) {
        try {
            if (movies != null) {
                movies.addAll(MovieAPI.getAllMovies(searchText, genre, releaseYear, rating));
            } else {
                throw new NullPointerException("List is null!");
            }
        }catch (MovieApiException e){
            String errorMessage = "An Error Occurred While Loading Movies. Try Again Later";
            Alert alert = new Alert(Alert.AlertType.ERROR, errorMessage, ButtonType.OK);
            alert.showAndWait();
        }
    }

}