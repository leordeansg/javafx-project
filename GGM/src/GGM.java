import app.Navigator;
import database.DatabaseInit;
import javafx.application.Application;
import javafx.stage.Stage;

public class GGM extends Application {

@Override
public void start(Stage stage) throws Exception {
    DatabaseInit.init();
    Navigator.setStage(stage);
    Navigator.goTo("/views/home.fxml");
}
 
    public static void main(String[] args) {
        launch();
    }
}