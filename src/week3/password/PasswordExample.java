/*
 * Course: CSC1120
 */
package week3.password;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Simple FXML example for practice
 */
public class PasswordExample extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("password.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("ICanHazPasswords");
        stage.show();
    }
}
