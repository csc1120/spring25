/*
 * Course: CSC1120
 * Event Handling
 */
package week2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Bloom;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * An example of Event Handling in JavaFX
 */
public class EventHandling extends Application {
    private Button button;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Pane root = new FlowPane();
        button = new Button("JavaFX Day 2");
        button.setOnAction(this::buttonHandler); // method reference
        // or button.setOnAction(e -> buttonHandler(e)); // lambda expression
        // or button.setOnAction(e -> {
        //         if (button.getEffect() == null) {
        //            button.setEffect(new Bloom());
        //        } else {
        //            button.setEffect(null);
        //        }
        // });
        // or anonymous inner class
        //   button.setOnAction(new EventHandler<ActionEvent> -> {
        //       if (button.getEffect() == null) {
        //            button.setEffect(new Bloom());
        //       } else {
        //           button.setEffect(null);
        //       }
        //});
        // or private inner class (see below)
        // or separate class (see ButtonHandler.java)
        root.getChildren().add(button);
        final int width = 400;
        final int height = 300;
        stage.setScene(new Scene(root, width, height));
        stage.show();
    }

    private void buttonHandler(ActionEvent e) {
        if (button.getEffect() == null) {
            button.setEffect(new Bloom());
        } else {
            button.setEffect(null);
        }
    }

    private class ButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            if (button.getEffect() == null) {
                button.setEffect(new Bloom());
            } else {
                button.setEffect(null);
            }
        }
    }
}