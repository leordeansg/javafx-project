package app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public final class Navigator {

    private static Stage stage;
    private static final double WIDTH = 1200;
    private static final double HEIGHT = 800;

    private Navigator() {}

    public static void setStage(Stage primaryStage) {
        stage = primaryStage;

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.getIcons().add(new Image("/images/GGM.png"));
        stage.setTitle("GGM");
    }

    public static void goTo(String fxmlPath) {
        if (stage == null) {
            throw new IllegalStateException("Navigator stage not set");
        }

        try {
            Parent root = FXMLLoader.load(Navigator.class.getResource(fxmlPath));
            Scene scene = new Scene(root, WIDTH, HEIGHT);
            scene.setFill(Color.TRANSPARENT);
            scene.getStylesheets().add("/styles/style.css");
            scene.getRoot().setStyle("-app-text: " + SharedInformation.accentColor + ";"); /* applying accent color to every page after css */
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load " + fxmlPath + " " + e);
        }
    }

    public static Stage getStage() {
        if (stage == null) {
            throw new IllegalStateException("Navigator stage not set");
        }
        return stage;
    }
}
