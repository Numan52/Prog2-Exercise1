package at.ac.fhcampuswien.fhmdb;


import at.ac.fhcampuswien.fhmdb.database.Database;
import javafx.application.Application;
import at.ac.fhcampuswien.fhmdb.patterns.MyFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;

public class FhmdbApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("home-view.fxml"));
        MyFactory myFactory = MyFactory.getInstance();
        fxmlLoader.setControllerFactory(myFactory);
        Scene scene = new Scene(fxmlLoader.load(), 890, 620);
        scene.getStylesheets().add(Objects.requireNonNull(FhmdbApplication.class.getResource("styles.css")).toExternalForm());
        stage.setTitle("FHMDb");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}