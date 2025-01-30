/*
 * Course: CSC1120
 * Week 2 Lab Quiz
 */
package week2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * A simple GUI program that adds two buttons
 * Button 1 will have the text "Load" and attach a handler that calls a load() method
 * Button 2 will have the text "Save" and attach a handler that calls a save() method
 */
public class Quiz2 extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Button load = new Button("Load");
        Button save = new Button("Save");
        load.setOnAction(_ -> load());
        save.setOnAction(_ -> save());
        HBox root = new HBox();
        root.getChildren().addAll(load, save);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Quiz 2");
        stage.show();
    }

    private void load() {
        System.out.println("Load stuff");
    }

    private void save() {
        System.out.println("Save stuff");
    }
}
