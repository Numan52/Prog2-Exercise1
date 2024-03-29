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
import at.ac.fhcampuswien.fhmdb.observerpattern.Observer;
import at.ac.fhcampuswien.fhmdb.patterns.MyFactory;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import at.ac.fhcampuswien.fhmdb.ui.WatchlistMovieCell;
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

public class WatchlistController implements Initializable, Observer {

    @FXML
    public JFXListView movieListView;
    @FXML
    public VBox vBox;
    public JFXButton returnBtn;

    public WatchlistRepository watchlistRepository = WatchlistRepository.getInstance();
    private final ObservableList<WatchlistMovieEntity> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        watchlistRepository.addObserver(this);
        try {
            loadmovies();
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        movieListView.setItems(observableMovies);   // set data of observable list to list view
        movieListView.setCellFactory(movieListView -> new WatchlistMovieCell(removeFromWatchlist)); // use custom cell factory to display data


        returnBtn.setOnAction(actionEvent -> {
            FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("home-view.fxml"));
            fxmlLoader.setControllerFactory(MyFactory.getInstance());
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 890, 620);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            watchlistRepository.removeObserver(this);
            Stage stage = (Stage)vBox.getScene().getWindow();
            scene.getStylesheets().add(Objects.requireNonNull(FhmdbApplication.class.getResource("styles.css")).toExternalForm());
            stage.setTitle("FHMDb");
            stage.setScene(scene);
            stage.show();
        });

    }
    private final ClickEventHandler removeFromWatchlist = (clickedItem) ->
    {
        if(clickedItem instanceof WatchlistMovieEntity)
        {
            try {
                watchlistRepository.removeFromWatchlist(((WatchlistMovieEntity)clickedItem));
                loadmovies();

            } catch(DatabaseException e) {
                String errorMessage = "An Error Occurred. Cannot Add Movie To Watchlist.";
                Alert alert = new Alert(Alert.AlertType.ERROR, errorMessage, ButtonType.OK);
                alert.showAndWait();
            }
        }

    };
    public void loadmovies() throws DatabaseException {
        observableMovies.clear();
        observableMovies.addAll(watchlistRepository.getAll());
    }


    @Override
    public void update(String message) {
        Alert alert = new Alert(Alert.AlertType.NONE, message, ButtonType.OK);
        alert.showAndWait();
    }
}