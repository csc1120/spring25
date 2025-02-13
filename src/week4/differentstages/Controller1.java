/*
 * Course: CSC1120
 * Different Stages Examples
 */
package week4.differentstages;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * First controller. Where the action is.
 */
public class Controller1 {
    @FXML
    private Button showButton;
    private Stage stage;
    private Stage stage2;
    private Controller2 c2;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setStage2(Stage stage2) {
        this.stage2 = stage2;
    }

    public void setC2(Controller2 c2) {
        this.c2 = c2;
    }

    @FXML
    private void show() {
        // need stage2
        stage2.setOnHiding(_ -> showButton.setText("Show"));
        stage2.setX(stage.getX() + stage.getWidth());
        stage2.setY(stage.getY());
        if(showButton.getText().equals("Show")) {
            showButton.setText("Hide");
            stage2.show();
        } else {
            showButton.setText("Show");
            stage2.close();
        }
    }
}
