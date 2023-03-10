package at.ac.fhcampuswien.fhmdb;

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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

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
    public JFXButton sortBtn;

    public List<Movie> allMovies = Movie.initializeMovies();

    private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableMovies.addAll(allMovies);         // add dummy data to observable list
        // initialize UI stuff
        movieListView.setItems(observableMovies);   // set data of observable list to list view
        movieListView.setCellFactory(movieListView -> new MovieCell()); // use custom cell factory to display data

        // TODO add genre filter items with genreComboBox.getItems().addAll(...)
        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().addAll(Genre.values()); //add all genres to combobox

        // TODO add event handlers to buttons and call the regarding methods
        // either set event handlers in the fxml file (onAction) or add them here

        // Sort button example:
        sortBtn.setOnAction(actionEvent -> {
            if(sortBtn.getText().equals("Sort (asc)")) {
                // TODO sort observableMovies ascending a-z
                sortMoviesAscending(observableMovies);
                sortBtn.setText("Sort (desc)");
            } else {
                // TODO sort observableMovies descending z-a
                sortMoviesDescending(observableMovies);
                sortBtn.setText("Sort (asc)");
            }
        });


    }
    public void sortMoviesAscending(ObservableList<Movie> allMovies) {
        allMovies.sort(Comparator.comparing(Movie::getTitle));

        //First attempt
        /*
        Comparator<Movie> titlecomapre = new Comparator<Movie>() { //create Comparator for collection.sort
            @Override //is abstact methode in Comparator -> needs to be overrided
            public int compare(Movie m1, Movie m2) {
                return String.CASE_INSENSITIVE_ORDER.compare(m1.getTitle(), m2.getTitle());
            }
        };
        Collections.sort(allMovies, titlecomapre);
        */
    }
    public void sortMoviesDescending(ObservableList<Movie> allMovies) {
        allMovies.sort(Comparator.comparing(Movie::getTitle).reversed());
    }

}