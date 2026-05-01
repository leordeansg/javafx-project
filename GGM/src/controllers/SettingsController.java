package controllers;

import app.Navigator;
import app.SharedInformation;
import database.DBUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import java.awt.Desktop;
import java.nio.file.Path;
import java.util.Optional;
import javafx.application.Platform;


public class SettingsController {
/* button start */
    @FXML
    private VBox vbox;

    @FXML
    private Pane dragpane;

    @FXML
    private Label title;

    private double xOffset;
    private double yOffset;
    
    @FXML
    private void min() {
        Navigator.getStage().setIconified(true);
    }

    @FXML
    private void close() {
        Navigator.getStage().close();
    }

    @FXML
    private void press(MouseEvent event) {
        Stage stage = Navigator.getStage();
        xOffset = stage.getX() - event.getScreenX();
        yOffset = stage.getY() - event.getScreenY();
    }

    @FXML
    private void drag(MouseEvent event) {
        Stage stage = Navigator.getStage();
        stage.setX(event.getScreenX() + xOffset);
        stage.setY(event.getScreenY() + yOffset);
    }

/* button end */
/* navigation start */
    @FXML
    private void goToHome(ActionEvent event) {
        Navigator.goTo("/views/home.fxml");
    }

    @FXML
    private void goToCars(ActionEvent event) {
        Navigator.goTo("/views/cars.fxml");
    }

    @FXML
    private void goToFinances(ActionEvent event) {
        Navigator.goTo("/views/finances.fxml");
    }

    @FXML
    private void goToReminders(ActionEvent event) {
        Navigator.goTo("/views/reminders.fxml");
    }

    @FXML
    private void goToSettings(ActionEvent event) {
        Navigator.goTo("/views/settings.fxml");
    }
/* navigation end */
/* variables start */
    @FXML
    private ColorPicker settingsColorPicker;
/* variables end */
/* initialize start */
    @FXML
    private void initialize() {
        settingsColorPicker.setValue(Color.web(SharedInformation.accentColor));
        settingsColorPicker.setOnAction(event -> applyAccentColor());
    }
/* initialize end */
/* functions start */
    @FXML
    private void applyAccentColor() {
        Color color = settingsColorPicker.getValue();
        if (color == null) color = Color.WHITE;
        String csscolor = color.toString().replace("0x", "#"); /* removing 0x from java color (0x83ff94ff) */
        SharedInformation.saveAccent(csscolor);
        Navigator.getStage()
            .getScene()
            .getRoot()
            .setStyle("-app-text: " + SharedInformation.accentColor + ";");
    }

    @FXML
    private void openDatabase() {
        try {
            Path dbPath = DBUtil.getDatabasePath();
            Desktop.getDesktop().open(dbPath.getParent().toFile());
        } catch (Exception e) {
            throw new RuntimeException("Can't get path to Database", e);
        }
    }

    @FXML
    private void resetDatabase() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Reset");
        alert.setHeaderText("Reset the Application?");
        alert.setContentText("This will permanently delete all data, are you sure you want to proceed?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isEmpty() || result.get() != ButtonType.OK) {
            return;
        }
        try {
            DBUtil.resetDatabase();

            Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
            confirmation.setTitle("Reset Complete");
            confirmation.setHeaderText("Database reset complete");
            confirmation.setContentText("The application will now close.");
            confirmation.showAndWait();
            
            Platform.exit();
        } catch (Exception e) {
            throw new RuntimeException("Database reset has failed", e);
        }

    }
/* functions end */
}
