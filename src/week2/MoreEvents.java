/*
 * Course: CSC1120
 * Exercise 2 - JavaFX Events
 * Name: Sean Jones
 */
package week2;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

/**
 * JavaFX Event Exercise
 */
public class MoreEvents extends Application {
    // These are instance variables rather than local to the start method
    // so that other methods can access them
    private final Rainbow[] rainbows = Rainbow.values();
    private int valueCount = 0;
    private Label label;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // Set default values
        final int width = 200;
        final int height = 150;
        final int spacing = 10;
        // Make parent container
        VBox vBox = new VBox(spacing);
        vBox.setAlignment(Pos.CENTER);
        // Make controls
        label = new Label("Watch me change!");
        TextField textField = new TextField("Enter some text here");
        Button button = new Button("Click Me!");
        Label coordinates = new Label();
        // Add event handlers inline as a lambda
        // inline = you don't need to write a full method header, just the contents
        // of the handling method. This is good for single line methods
        label.setOnMouseEntered(_ -> label.setTextFill(Paint.valueOf("purple")));
        label.setOnMouseExited(_ -> label.setTextFill(Paint.valueOf("black")));
        // Add a listener to the TextField that will update the contents of label to whatever is in
        // the TextField when the user presses Enter/Return
        textField.setOnAction(_ -> label.setText(textField.getText()));
        // Add a MouseEvent listener to the parent container that will display the current
        // coordinates of the mouse pointer in the coordinates label whenever the pointer
        // is moved inside the GUI window. You do not need to worry about what happens when
        // the mouse pointer leaves the window.
        vBox.setOnMouseMoved(e -> coordinates.setText((int)e.getX() + ", " + (int)e.getY()));
        button.setOnAction(_ -> label.setText("Watch me change!"));
        // When the method is in the same class and has an event parameter like
        // handler(Event e), you can use a method reference. If you omit the
        // event parameter, you must use a lambda expression
        textField.setOnKeyReleased(this::changeColor);
        // Add controls to the parent
        vBox.getChildren().addAll(label, textField, button, coordinates);
        // Add parent container to the Scene and set the dimensions
        Scene scene = new Scene(vBox, width, height);
        // Add the scene to the stage and give it a title
        stage.setScene(scene);
        stage.setTitle("Events!");
        // Show the stage
        stage.show();
    }

    // Separate handler method
    private void changeColor(KeyEvent e) {
        label.setTextFill(rainbows[valueCount++ % rainbows.length].color);
    }

    // Private inner enumeration
    private enum Rainbow {
        RED(Color.RED),
        ORANGE(Color.ORANGE),
        YELLOW(Color.YELLOW),
        GREEN(Color.GREEN),
        BLUE(Color.BLUE),
        INDIGO(Color.INDIGO),
        VIOLET(Color.VIOLET);

        private final Color color;

        Rainbow(Color color) {
            this.color = color;
        }
    }
}
