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
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
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

public class WatchlistController implements Initializable {

    @FXML
    public JFXListView movieListView;
    @FXML
    public VBox vBox;
    public JFXButton returnBtn;

    public WatchlistRepository watchlistRepository = new WatchlistRepository();
    private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        returnBtn.setOnAction(actionEvent -> {
            FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("home-view.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 890, 620);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = (Stage)vBox.getScene().getWindow();
            scene.getStylesheets().add(Objects.requireNonNull(FhmdbApplication.class.getResource("styles.css")).toExternalForm());
            stage.setTitle("FHMDb");
            stage.setScene(scene);
            stage.show();
        });

    }


}