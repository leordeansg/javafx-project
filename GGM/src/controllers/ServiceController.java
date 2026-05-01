package controllers;

import app.SharedInformation;
import app.Navigator;
import classes.Car;
import classes.Service;
import repository.ServiceRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ServiceController {
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
    private TextField serviceMiles;

    @FXML
    private TextField serviceNote;

    @FXML
    private Label serviceError;

    @FXML
    private Label serviceSelectedCar;

    @FXML
    private TableView<Service> serviceTable;

    @FXML
    private TableColumn<Service, String> serviceTableDate;

    @FXML
    private TableColumn<Service, Integer> serviceTableMiles;

    @FXML
    private TableColumn<Service, String> serviceTableNote;

    private final ServiceRepository repo = new ServiceRepository();
    private Car selected;
/* variables end */
/* functions start */
    private void refreshTable() {
        serviceTable.setItems(
            FXCollections.observableArrayList(repo.findByCar(selected.getCarid()))
        );
    }

    private void clearForm() {
        serviceMiles.clear();
        serviceNote.clear();
        serviceTable.getSelectionModel().clearSelection();
    }

    private Service readForm() {
        try {
            int miles = Integer.parseInt(serviceMiles.getText().trim());
            String note = serviceNote.getText().trim();
            return new Service(selected.getCarid(), miles, note);
        } catch (NumberFormatException e) {
            serviceError.setText("Miles must be a whole number");
            return null;
        }
    }
/* functions end */
/* initialize start */
    @FXML
    private void initialize() {
        selected = SharedInformation.selectedCar;

        if (selected == null) {
            serviceSelectedCar.setText("No car selected");
            serviceError.setText("Go back and select a car");
            return;
        }

        serviceSelectedCar.setText(selected.getNickname() + " | " + selected.getMake() + " " + selected.getModel());

        serviceTableDate.setCellValueFactory(new PropertyValueFactory<>("loggedOn"));
        serviceTableNote.setCellValueFactory(new PropertyValueFactory<>("note"));
        serviceTableMiles.setCellValueFactory(new PropertyValueFactory<>("miles"));

        refreshTable();
        serviceError.setText("");
    }
/* initialize end */
/* CRUD start */
    @FXML
    private void serviceDelete(ActionEvent event) {
        Service row = serviceTable.getSelectionModel().getSelectedItem();
        if (row == null) {
            serviceError.setText("Select an entry");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Service Entry");
        alert.setContentText("Delete selected service entry?");

        if (alert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        try {
            repo.delete(row.getServiceid());
            refreshTable();
            clearForm();
            serviceError.setText("");
        } catch (Exception e) {
            serviceError.setText("Failed to delete entry");
        }
    }

    @FXML
    private void serviceAdd(ActionEvent event) {
        if (selected == null) return;

        Service service = readForm();
        if (service == null) return;

        try {
            service.validate();
            repo.insert(service);
            refreshTable();
            clearForm();
            serviceError.setText("");
        } catch (IllegalArgumentException e) {
            serviceError.setText(e.getMessage());
        } catch (Exception e) {
            serviceError.setText("Car could not be added");
        }
    }
/* CRUD end */
}
