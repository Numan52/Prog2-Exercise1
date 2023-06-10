package at.ac.fhcampuswien.fhmdb.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public final class UIAlerts {
    public static void errormessage(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }
}
