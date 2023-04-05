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
import java.util.ResourceBundle;
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

        //add genre filter items with genreComboBox.getItems().addAll(...)
        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().addAll(Genre.values()); //add all genres to combobox

        List<Double> rat = observableMovies.stream()
                .map(Movie::getRating)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        ratingComboBox.getItems().addAll(rat);

        List<Integer> ry = observableMovies.stream()
                .map(Movie::getReleaseYear)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        releaseyearComboBox.getItems().addAll(ry);


        // add event handlers to buttons and call the regarding methods
        // either set event handlers in the fxml file (onAction) or add them here

        // Sort button example:
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

        resetBtn.setOnAction(actionEvent -> {
            searchField.setText("");
            genreComboBox.setValue(null);
            releaseyearComboBox.setValue(null);
            ratingComboBox.setValue(null);
            observableMovies.clear();
            observableMovies.addAll(MovieAPI.getAllMovies());
            sortBtn.setText("Sort");
        });

        searchBtn.setOnAction(actionEvent -> { //click on search button
            observableMovies.clear();
            if (genreComboBox.getValue() == null) //if no genre is selected its auto. All_genres
            {
                genreComboBox.setValue(Genre.ALL_GENRES);
            }
            String releaseyear = (releaseyearComboBox.getValue() != null) ? releaseyearComboBox.getValue().toString() : null;
            String rating = (ratingComboBox.getValue() != null) ? ratingComboBox.getValue().toString() : null;
            observableMovies.addAll(MovieAPI.getAllMovies(searchField.getText(), Genre.valueOf(genreComboBox.getValue().toString()),
                    releaseyear, rating));
            if(sortBtn.getText().equals("Sort (asc)")) { //stick with the sorting order selected before
                sortMoviesDescending(observableMovies); //"Sort asc" displayed means order was descending
            } else if(sortBtn.getText().equals("Sort (desc)")){
                sortMoviesAscending(observableMovies); //"Sort desc" displayed means order was descending
            }

        });

    }


    public void sortMoviesAscending(ObservableList<Movie> allMovies) {
        if(allMovies == null)
        {
            throw new NullPointerException("List is null");
        }
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
        if(allMovies == null)
        {
            throw new NullPointerException("List is null");
        }
        allMovies.sort(Comparator.comparing(Movie::getTitle).reversed());
    }
/*
    public void filterMovies(ObservableList<Movie> allMovies, String searchText, Genre genre) {
        if(allMovies == null)
        {
            throw new NullPointerException("List is null");
        }
        else {
            if(genre == null)
            {
                genre = Genre.ALL_GENRES;
            }
            List<Movie> filtermovies = new ArrayList<>(); //list of movies with filter options
            for (Movie m : allMovies) {
                if(searchText != null) //if searchtext equals null, we are only filter after genre
                {
                    searchText = searchText.trim(); //trim the spaces after the last char example: "the    " -> "the"
                    if(!searchText.equals(" ")){ //if the searchtext is only spaces "     " it will be reduced to " " by trim
                        if((m.getTitle().toLowerCase().contains(searchText.toLowerCase()) ||
                                m.getDescription().toLowerCase().contains(searchText.toLowerCase())) && (m.getGenres().contains(genre.toString()) || genre.equals(Genre.ALL_GENRES)))
                        {
                            filtermovies.add(m);
                        }
                    }
                }
                else
                {
                    if(m.getGenres().contains(genre.toString()) || genre.equals(Genre.ALL_GENRES))
                    {
                        filtermovies.add(m);
                    }
                }
            }
            allMovies.clear();
            allMovies.addAll(filtermovies);
        }
    }
    */
    //could make sorting movies ascending und descending in one methode with second parameter true=ascending,false=descending TODO look at benefits
}