/*
 * Course: CSC1120
 * FX and Functional Programming Example
 */
package week3.calculate;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * Controller class for Calculate
 */
public class Controller implements Initializable {
    @FXML
    private ChoiceBox<String> operator;
    @FXML
    private TextField operandA;
    @FXML
    private TextField operandB;
    @FXML
    private TextField result;
    private final String[] operators = {"+", "-", "*", "/", "%"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        operator.setItems(FXCollections.observableArrayList(Arrays.asList(operators)));
        operator.getSelectionModel().selectedIndexProperty()
                .addListener((_, _, newValue) -> apply(newValue.intValue()));
    }

    private void apply(int operation) {
        int opA = Integer.parseInt(operandA.getText());
        int opB = Integer.parseInt(operandB.getText());
        result.setText("" + switch (operation) {
            case 0 -> math(opA, opB, Integer::sum);
            case 1 -> math(opA, opB, (a, b) -> a - b);
            case 2 -> math(opA, opB, (a, b) -> a * b);
            case 3 -> math(opA, opB, (a, b) -> a / b);
            case 4 -> math(opA, opB, (a, b) -> a % b);
            default -> 0;
        });
    }

    private int math(int a, int b, DoIntMath doIt) {
        return doIt.doMath(a, b);
    }
}
