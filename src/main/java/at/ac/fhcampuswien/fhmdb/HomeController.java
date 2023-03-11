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
import java.util.*;
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
            if(sortBtn.getText().equals("Sort (asc)") || sortBtn.getText().equals("Sort")) {
                // TODO sort observableMovies ascending a-z
                sortMoviesAscending(observableMovies);
                sortBtn.setText("Sort (desc)");
            } else {
                // TODO sort observableMovies descending z-a
                sortMoviesDescending(observableMovies);
                sortBtn.setText("Sort (asc)");
            }
        });

        searchBtn.setOnAction(actionEvent -> { //click on search button
            observableMovies.clear();
            observableMovies.addAll(allMovies);
            if (genreComboBox.getValue() != null)
            {
                filterMovies(observableMovies, searchField.getText(), genreComboBox.getValue().toString());

                if(sortBtn.getText().equals("Sort (asc)")) { //stick with the sorting order selected before
                    sortMoviesDescending(observableMovies); //"Sort asc" displayed means order was descending
                } else if(sortBtn.getText().equals("Sort (desc)")){
                    sortMoviesAscending(observableMovies); //"Sort desc" displayed means order was descending
                }
                //else stick with no order
            }
        });

    }
    public void filterMovies(ObservableList<Movie> allMovies, String searchText, String genre) {

        List<Movie> filtermovies = new ArrayList<>(); //list of movies with filter options
        for (Movie m : allMovies) {
            if(searchText != null) //if searchtext equals null, we are only filter after genre
            {
                searchText = searchText.trim(); //trim the spaces after the last char example: "the    " -> "the"
                if(!searchText.equals(" ")){ //if the searchtext is only spaces "     " it will be reduced to " " by trim
                    if((m.getTitle().toLowerCase().contains(searchText.toLowerCase()) ||
                            m.getDescription().toLowerCase().contains(searchText.toLowerCase())) && m.getGenres().contains(genre))
                    {
                        filtermovies.add(m);
                    }
                }
            }
            else
            {
                if(m.getGenres().contains(genre))
                {
                    filtermovies.add(m);
                }
            }
        }
        allMovies.clear();
        allMovies.addAll(filtermovies);
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

    public List<Movie> filterMovies(String query, Genre genre) {
        List<Movie> filteredList = new ArrayList<>();

        for (Movie movie : allMovies) {
            String movieTitle = movie.getTitle().toLowerCase();
            String movieDesctription = movie.getDescription().toLowerCase();

            if ((query == null || movieTitle.contains(query.toLowerCase()) || movieDesctription.contains(query.toLowerCase())) &&
               (genre == null || movie.getGenres().contains(genre.toString()) || genre == Genre.ALL_GENRES)){
                    filteredList.add(movie);
            }
           /* if (query == null && movie.getGenres().contains(genre.toString())) {
                filteredList.add(movie);
            }

            if (query != null ) {
                String movieTitle = movie.getTitle().toLowerCase();
                String movieDesctription = movie.getDescription().toLowerCase();

                if (movieTitle.contains(query.toLowerCase()) || movieDesctription.contains(query.toLowerCase())) {
                    if (movie.getGenres().contains(genre.toString()) || genre == Genre.ALL_GENRES){
                        filteredList.add(movie);
                    }
                }
            }

        }*/

        }
        return filteredList;
    }
}