/*
 * Course: CSC1120
 * File Chooser Example
 */
package week2;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * A program that will load a file and display the name of the file.
 * If the user does not select a file, the display will be empty.
 */
public class FileChooserExample extends Application {
    // Declared as instance variables so they can be used in
    // handler methods
    private Button load;
    private Label label;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        final int dimensions = 200;
        final int spacing = 10;
        load = new Button("load");
        // If the handler does not use the event object, it can be omitted
        // and replaced with an underscore. The method being called should
        // then have no parameter
        load.setOnAction(_ -> load());
        label = new Label();
        VBox root = new VBox();
        root.setSpacing(spacing);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(load, label);
        stage.setScene(new Scene(root, dimensions, dimensions));
        stage.show();
    }

    private void load() {
        FileChooser chooser = new FileChooser();
        // load.getScene().getWindow() returns the window created by the Stage
        // by getting the Scene, then the window that the Scene inhabits.
        // This will attach the FileChooser to the window. You can also
        // pass null -> showOpenDialog(null) which will create a separate window
        // for the FileChooser
        File file = chooser.showOpenDialog(load.getScene().getWindow());
        // Always check if a file is null before using it!
        if(file != null) {
            label.setText(file.getName());
        } else {
            label.setText("");
        }
    }
}
