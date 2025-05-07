/*
 * Course: CSC-1120
 * JavaFX question
 */
package practicefinal;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Controller class for Q2
 */
public class Controller {
    @FXML
    private TextField input;
    @FXML
    private Label result;

    @FXML
    private void handleButton() {
        result.setText(input.getText().toUpperCase());
    }
}
