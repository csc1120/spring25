/*
 * Course: CSC1120
 * Different Stages Examples
 */
package week4.differentstages;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class. Sets up the two Stages. Here is where we get the
 * controller instances from the FXMLLoader objects and can
 * get and set instance variables as we need for our code.
 */
public class DifferentStages extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Stage stage2 = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("stages.fxml"));
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("stages2.fxml"));
        Parent root1 = loader.load(); // Controllers created here
        Parent root2 = loader2.load();
        stage.setScene(new Scene(root1));
        stage2.setScene(new Scene(root2));
        stage.setTitle("Stage 1");
        stage2.setTitle("Stage 2");
        // get and set the controls needed for the code
        Controller1 c1 = loader.getController();
        Controller2 c2 = loader2.getController();
        c1.setStage(stage);
        c1.setStage2(stage2);
        c1.setC2(c2);

        stage.show();
    }
}
