/*
 * Course: CSC1120
 * CSS Example
 */
package week3.css;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Main class for using CSS with JavaFX
 */
public class CssExample extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        final int width = 200;
        final int height = 100;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("cssexample.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, width, height);
        scene.getStylesheets()
                .add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
