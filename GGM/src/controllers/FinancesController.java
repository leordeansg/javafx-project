package controllers;

import app.SharedInformation;
import app.Navigator;
import classes.Car;
import classes.Finance;
import repository.FinanceRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class FinancesController {
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
    private TextField financeAmount;

    @FXML
    private TextField financeNote;

    @FXML
    private Label financeError;

    @FXML
    private Label financeSelectedCar;

    @FXML
    private TableView<Finance> financeTable;

    @FXML
    private TableColumn<Finance, String> financeTableDate;

    @FXML
    private TableColumn<Finance, Integer> financeTableAmount;

    @FXML
    private TableColumn<Finance, String> financeTableNote;

    private final FinanceRepository repo = new FinanceRepository();
    private Car selected;
/* variables end */
/* functions start */
    private void refreshTable() {
        financeTable.setItems(
            FXCollections.observableArrayList(repo.findByCar(selected.getCarid()))
        );
    }

    private void clearForm() {
        financeAmount.clear();
        financeNote.clear();
        financeTable.getSelectionModel().clearSelection();
    }

    private Finance readForm() {
        try {
            int amount = Integer.parseInt(financeAmount.getText().trim());
            String note = financeNote.getText().trim();
            return new Finance(selected.getCarid(), amount, note);
        } catch (NumberFormatException e) {
            financeError.setText("Amount must be a whole number");
            return null;
        }
    }
/* functions end */
/* initialize start */
    @FXML
    private void initialize() {
        selected = SharedInformation.selectedCar;

        if (selected == null) {
            financeSelectedCar.setText("No car selected");
            financeError.setText("Go back and select a car");
            return;
        }

        financeSelectedCar.setText(selected.getNickname() + " | " + selected.getMake() + " " + selected.getModel());

        financeTableDate.setCellValueFactory(new PropertyValueFactory<>("loggedOn"));
        financeTableNote.setCellValueFactory(new PropertyValueFactory<>("note"));
        financeTableAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        refreshTable();
        financeError.setText("");
    }
/* initialize end */
/* CRUD start */
    @FXML
    private void financeDelete(ActionEvent event) {
        Finance row = financeTable.getSelectionModel().getSelectedItem();
        if (row == null) {
            financeError.setText("Select an entry");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Finance Entry");
        alert.setContentText("Delete selected finance entry?");

        if (alert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        try {
            repo.delete(row.getFinanceid());
            refreshTable();
            clearForm();
            financeError.setText("");
        } catch (Exception e) {
            financeError.setText("Failed to delete entry");
        }
    }

    @FXML
    private void financeAdd(ActionEvent event) {
        if (selected == null) return;

        Finance finance = readForm();
        if (finance == null) return;

        try {
            finance.validate();
            repo.insert(finance);
            refreshTable();
            clearForm();
            financeError.setText("");
        } catch (IllegalArgumentException e) {
            financeError.setText(e.getMessage());
        } catch (Exception e) {
            financeError.setText("Expense could not be added");
        }
    }
/* CRUD end */
}
