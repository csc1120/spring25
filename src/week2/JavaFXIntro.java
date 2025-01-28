/*
 * Course: CSC-1120
 * JavaFX Intro
 */

package week2;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Sample JavaFX program
 */
public class JavaFXIntro extends Application {
    // when you need to access the node
    // in code, make it an instance variable
    private Button button2;

    public static void main(String[] args) {
        launch(args);
    }
    /*
     * Pane-https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/Pane.html
     * StackPane-https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/StackPane.html
     * BorderPane-https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/BorderPane.html
     * AnchorPane-https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/AnchorPane.html
     * FlowPane-https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/FlowPane.html
     * VBox-https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html
     * HBox-https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/HBox.html
     */
    @Override
    public void start(Stage stage) {
        Button button = new Button("Click Me!");
        // Set ActionEvent handler
        button.setOnAction(e -> clickMe(e));
        button2 = new Button("No, Click Me!");
        TextField textField = new TextField();
        // Set ActionEvent handler
        textField.setOnAction(e -> changeButtonText(e));

        final int spacing = 10;
        final int width = 350;
        final int height = 100;
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setSpacing(spacing);
        hBox.getChildren().addAll(button, button2);
        VBox vBox = new VBox();
        vBox.setSpacing(spacing);
        vBox.getChildren().addAll(textField, hBox);
        Scene scene = new Scene(vBox, width, height);
        stage.setScene(scene);
        stage.setTitle("JavaFX Intro");
        stage.show();
    }

    /**
     * Triggered when the clickMe button is pressed.
     * If the button has no effect, the button is blurred
     * If the button is already blurred, the effect is removed
     *
     * @param event the ActionEvent object containing information about the event
     *              including the source node
     */
    private void clickMe(ActionEvent event) {
        Button b = (Button) event.getSource();
        if (b.getEffect() == null) {
            b.setEffect(new BoxBlur());
        } else {
            b.setEffect(null);
        }
    }

    /**
     * Triggered when the user pressed enter when in the text field.
     * If the text field is empty, nothing happens
     * If the text field has text, that text is set as the text for button2
     *
     * @param event the ActionEvent object containing information about the event
     *              including the source node
     */
    private void changeButtonText(ActionEvent event) {
        TextField tf = (TextField) event.getSource();
        if (!tf.getText().isEmpty()) {
            // button2 is accessible because it is an instance variable
            button2.setText(tf.getText());
        }
    }
}
