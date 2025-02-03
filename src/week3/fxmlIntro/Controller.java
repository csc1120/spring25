package week3.fxmlIntro;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Controller {
    @FXML
    private TextField textField;

    @FXML
    private void print() {
        System.out.println(textField.getText());
    }
}
