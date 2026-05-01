package controllers;

import app.Navigator;
import app.SharedInformation;
import classes.Car;
import repository.CarRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CarsController {
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
    private void goToReminders(ActionEvent event) {
        Navigator.goTo("/views/reminders.fxml");
    }

    @FXML
    private void goToSettings(ActionEvent event) {
        Navigator.goTo("/views/settings.fxml");
    }

    @FXML
    private void goToFinances(ActionEvent event) {
        Car selected = carsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            carsError.setText("Select a car first");
            return;
        }
        SharedInformation.selectedCar = selected;
        Navigator.goTo("/views/finances.fxml");
    }

    @FXML
    private void goToService(ActionEvent event) {
        Car selected = carsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            carsError.setText("Select a car first");
            return;
        }
        SharedInformation.selectedCar = selected;
        Navigator.goTo("/views/service.fxml");
    }
/* navigation end */
/* variables start */
    @FXML
    private TextField carsNickname;

    @FXML
    private TextField carsMake;

    @FXML
    private TextField carsModel;

    @FXML
    private TextField carsYear;

    @FXML
    private TextField carsPlate;

    @FXML
    private TextField carsMiles;

    @FXML
    private Label carsError;

    @FXML
    private TableView<Car> carsTable;

    @FXML
    private TableColumn<Car, String> carsTableNickname;

    @FXML
    private TableColumn<Car, String> carsTableMake;

    @FXML
    private TableColumn<Car, String> carsTableModel;

    @FXML
    private TableColumn<Car, Integer> carsTableYear;

    @FXML
    private TableColumn<Car, String> carsTablePlate;

    @FXML
    private TableColumn<Car, Integer> carsTableMiles;

    private final CarRepository repo = new CarRepository();
/* variables end */
/* functions start */
    private void refreshTable() {
        carsTable.setItems(
            FXCollections.observableArrayList(repo.findAll())
        );
    }

    private void clearForm() {
        carsNickname.clear();
        carsMake.clear();
        carsModel.clear();
        carsYear.clear();
        carsPlate.clear();
        carsMiles.clear();
        carsTable.getSelectionModel().clearSelection();
    }

    private void fillForm(Car c) {
        carsNickname.setText(c.getNickname());
        carsMake.setText(c.getMake());
        carsModel.setText(c.getModel());
        carsYear.setText(String.valueOf(c.getCarYear()));
        carsPlate.setText(c.getPlate());
        carsMiles.setText(String.valueOf(c.getCurrentMiles()));
    }

    private Car readForm() {
        try {
            int year = Integer.parseInt(carsYear.getText());
            int miles = Integer.parseInt(carsMiles.getText());

            return new Car(
                carsNickname.getText(),
                carsMake.getText(),
                carsModel.getText(),
                year,
                carsPlate.getText(),
                miles
            );
        } catch (NumberFormatException e) {
            carsError.setText("Year and Miles must be Numbers");
            return null;
        }
    }
/* functions end */
/* initialize start */
@FXML
private void initialize() {
    carsTableNickname.setCellValueFactory(new PropertyValueFactory<>("nickname"));
    carsTableMake.setCellValueFactory(new PropertyValueFactory<>("make"));
    carsTableModel.setCellValueFactory(new PropertyValueFactory<>("model"));
    carsTableYear.setCellValueFactory(new PropertyValueFactory<>("carYear"));
    carsTablePlate.setCellValueFactory(new PropertyValueFactory<>("plate"));
    carsTableMiles.setCellValueFactory(new PropertyValueFactory<>("currentMiles"));

    carsTable.getSelectionModel().selectedItemProperty().addListener(
        (obs, oldCar, newCar) -> {
            if (newCar != null) fillForm(newCar);
        }
    );

    refreshTable();
    carsError.setText("");
}
/* initialize end */
/* CRUD start */
@FXML
private void carsAdd(ActionEvent event) {
    Car car = readForm();
    if (car == null) return;

    try {
        car.validate();
        repo.insert(car);
        refreshTable();
        clearForm();
        carsError.setText("");
    } catch (IllegalArgumentException e) {
        carsError.setText(e.getMessage());
    } catch (Exception e) {
        carsError.setText("Car could not be added");
    }
}

@FXML
private void carsUpdate(ActionEvent event) {
    Car selected = carsTable.getSelectionModel().getSelectedItem();
    if (selected == null) {
        carsError.setText("Please select a car before Updating");
        return;
    }

    Car updated = readForm();
    if (updated == null) return;

    Car car = new Car(
        selected.getCarid(),
        updated.getNickname(),
        updated.getMake(),
        updated.getModel(),
        updated.getCarYear(),
        updated.getPlate(),
        updated.getCurrentMiles(),
        selected.getAddedOn()
    );

    try {
        car.validate();
        repo.update(car);
        refreshTable();
        clearForm();
        carsError.setText("");
    } catch (IllegalArgumentException e) {
        carsError.setText(e.getMessage());
    } catch (Exception e) {
        carsError.setText("Car could not be Updated");
    }
}

@FXML
private void carsDelete(ActionEvent event) {
    Car selected = carsTable.getSelectionModel().getSelectedItem();
    if (selected == null) {
        carsError.setText("Please select a car to Delete");
        return;
    }

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Delete Car");
    alert.setContentText("Are you sure you want to delete the selected car? All related records will also be removed");

    if (alert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
        return;
    }
    try {
        repo.delete(selected.getCarid());
        refreshTable();
        clearForm();
        carsError.setText("");
    } catch (Exception e) {
        carsError.setText("Failed to delete car");
    }
}

@FXML
private void carsClear(ActionEvent event) {
    clearForm();
    carsError.setText("");
}
/* CRUD end */
}