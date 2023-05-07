package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.database.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.database.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.interfaces.ClickEventHandler;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.sql.SQLException;
import java.util.stream.Collectors;

public class MovieCell extends ListCell<Movie> {

    private final Label title = new Label();
    private final Label description = new Label();
    private final Label genres = new Label();
    private final Button addmoviebutton = new Button();
    private final Label releaseYear = new Label();
    private final Label lengthInMinutes = new Label();
    private  final Label directors = new Label();
    private final Label rating = new Label();

    private final VBox details = new VBox(releaseYear,lengthInMinutes,directors,rating);
    private final VBox layout = new VBox(title, description, genres, addmoviebutton);


    public MovieCell(ClickEventHandler addToWatchlistClicked) {
        super();
        addmoviebutton.setOnMouseClicked(mouseEvent -> {
            addToWatchlistClicked.onClick(getItem());
        });
    }

    public MovieCell() {

    }

    @Override
    protected void updateItem(Movie movie, boolean empty) {
        super.updateItem(movie, empty);
        if (empty || movie == null) {
            setText(null);
            setGraphic(null);

        } else {
            this.getStyleClass().add("movie-cell");
            title.setText(movie.getTitle());
            description.setText(
                    movie.getDescription() != null
                            ? movie.getDescription()
                            : "No description available"
            );
            genres.setText(
                    movie.getGenres() != null
                            ? movie.getGenres().stream()
                            .map(Genre::name)
                            .collect(Collectors.joining(","))
                            : "No genre available"
            );
            addmoviebutton.setText("Watchlist");
            directors.setText("Directors: " + movie.getDirectors());
            releaseYear.setText("Release Year: " + movie.getReleaseYear());
            lengthInMinutes.setText("Length: " + movie.getLengthInMinutes());
            rating.setText("Rating: " + movie.getRating());



            // color scheme
            title.getStyleClass().add("text-yellow");
            description.getStyleClass().add("text-white");
            genres.getStyleClass().add("text-white");
            addmoviebutton.getStyleClass().add("background-yellow");
            directors.getStyleClass().add("text-white");
            releaseYear.getStyleClass().add("text-white");
            lengthInMinutes.getStyleClass().add("text-white");
            rating.getStyleClass().add("text-white");
            layout.setBackground(new Background(new BackgroundFill(Color.web("#454545"), null, null)));


            // layout
            title.fontProperty().set(title.getFont().font(20));
            description.setMaxWidth(this.getScene().getWidth() - 30);
            description.setWrapText(true);
            genres.fontProperty().set(genres.getFont().font(13));
            layout.setPadding(new Insets(10));
            layout.spacingProperty().set(10);
            layout.alignmentProperty().set(javafx.geometry.Pos.CENTER_LEFT);


            setGraphic(layout);

        }
    }
}
