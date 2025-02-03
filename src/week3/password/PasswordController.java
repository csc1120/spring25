/*
 * Course: CSC1120
 * FXML Example
 */

package week3.password;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controller for hacking
 */
public class PasswordController {
    @FXML
    private TextField name;
    @FXML
    private PasswordField password;
    @FXML
    private Label label;

    @FXML
    private void hack() {
        if(!name.getText().isEmpty() && !password.getText().isEmpty()) {
            label.setText("Your password is: " + password.getText());
        }
    }
}
