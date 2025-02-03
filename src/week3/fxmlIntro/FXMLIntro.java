package week3.fxmlIntro;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FXMLIntro extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("fxmlintro.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.setTitle("FXML Intro");
        stage.show();
    }
}
