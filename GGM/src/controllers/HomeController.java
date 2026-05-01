package controllers;

import app.Navigator;
import classes.Reminder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import repository.CarRepository;
import repository.FinanceRepository;
import repository.ReminderRepository;
import repository.ServiceRepository;

public class HomeController {
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

        @FXML
    private void goToService(ActionEvent event) {
        Navigator.goTo("/views/service.fxml");
    }
/* navigation end */
/* variables start */
    @FXML
    private Label homeCars;

    @FXML
    private Label homeMileage;

    @FXML
    private Label homeHighestMileage;

    @FXML
    private Label homeAmountSpent;

    @FXML
    private Label homeMostExpensiveName;

    @FXML
    private Label homeMostExpensivePrice;

    @FXML
    private Label homeServices;

    @FXML
    private Label homeLastServicedCar;

    @FXML
    private Label homeLastServicedDate;

    @FXML
    private Label homeReminderNext;

    @FXML
    private Label homeReminderDueDate;

    @FXML
    private Label homeReminderNote;

    private final CarRepository carRepository = new CarRepository();
    private final ServiceRepository serviceRepository = new ServiceRepository();
    private final FinanceRepository financeRepository = new FinanceRepository();
    private final ReminderRepository reminderRepository = new ReminderRepository();
/* variables end */
/* initialize start */
    @FXML
    private void initialize() {
        homeCars.setText(String.valueOf(carRepository.countCars()));
        homeMileage.setText(String.valueOf(carRepository.countMiles()));
        homeHighestMileage.setText(String.valueOf(carRepository.highestMileage()));

        homeServices.setText(String.valueOf(serviceRepository.countServices()));
        homeLastServicedCar.setText(String.valueOf(serviceRepository.lastServiced()));
        String datetrim = String.valueOf(serviceRepository.lastServicedDate());
        homeLastServicedDate.setText(datetrim.length() >= 10 ? datetrim.substring(0, 10) : datetrim);

        homeAmountSpent.setText("£" + String.valueOf(financeRepository.totalAmount()));
        homeMostExpensivePrice.setText("£" + String.valueOf(financeRepository.mostExpensivePrice()));
        homeMostExpensiveName.setText(String.valueOf(financeRepository.mostExpensiveName()));

        Reminder next = reminderRepository.findNext();
        if (next == null) {
            homeReminderNext.setText("");
            homeReminderDueDate.setText("");
            homeReminderNote.setText("");
        } else {
            homeReminderNext.setText(next.getName());
            homeReminderDueDate.setText(next.getDueDate());
            homeReminderNote.setText(next.getDescription());
        }
    }

/* initialize end */
}
