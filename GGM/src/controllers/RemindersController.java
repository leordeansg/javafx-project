package controllers;

import app.Navigator;
import classes.Reminder;
import repository.ReminderRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDate;

public class RemindersController {
/* buttons start */   
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
/* buttons end */    
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
    private TextField remindersName;

    @FXML
    private TextField remindersDescription;

    @FXML
    private DatePicker remindersDueDate;

    @FXML
    private ListView<Reminder> remindersList;

    @FXML
    private Label remindersError;

    private final ReminderRepository repo = new ReminderRepository();
/* variables end */ 
/* functions start */ 
    private void refreshList() {
        remindersList.setItems(
            FXCollections.observableArrayList(repo.findAll())
        );
    }

    private void clearForm() {
        remindersName.clear();
        remindersDescription.clear();
        remindersDueDate.setValue(null);
        remindersList.getSelectionModel().clearSelection();
    }

    private Reminder readForm() {
        LocalDate due = remindersDueDate.getValue();

        return new Reminder(
            remindersName.getText().trim(),
            remindersDescription.getText().trim(),
            due == null ? null : due.toString()
        );
    }
/* functions end */
/* initialize start */
    @FXML
    private void initialize() {
        refreshList();
        remindersError.setText("");
    }
/* initialize end */
/* CRUD start */
    @FXML
    private void remindersAdd(ActionEvent event) {
        Reminder r = readForm();
        if (r == null) return;

        try {
            r.validate();
            repo.insert(r);
            refreshList();
            clearForm();
            remindersError.setText("");
        } catch (IllegalArgumentException e) {
            remindersError.setText(e.getMessage());
        } catch (Exception e) {
            remindersError.setText("Reminder could not be added");
        }
    }

    @FXML 
    private void remindersDelete(ActionEvent event) {
        Reminder selected = remindersList.getSelectionModel().getSelectedItem();
        if (selected == null) {
            remindersError.setText("Select a reminder first");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Reminder");
        alert.setContentText("Delete selected reminder?");

        if (alert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        try {
            repo.delete(selected.getReminderid());
            refreshList();
            clearForm();
            remindersError.setText("");
        } catch (Exception e) {
            remindersError.setText("Failed to delete reminder");
        }
    }
/* CRUD end */
}