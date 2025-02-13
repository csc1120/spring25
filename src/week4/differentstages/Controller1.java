package week4.differentstages;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Controller1 {
    @FXML
    private Button showButton;
    private Controller2 controller2;
    private Stage stage;
    private Stage stage2;
    private int count = 0;

    public void setController2(Controller2 controller2) {
        this.controller2 = controller2;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setStage2(Stage stage2) {
        this.stage2 = stage2;
    }

    @FXML
    private void show() {
        stage2.setOnHiding(_ -> showButton.setText("Show"));
        stage2.setX(stage.getX() + stage.getWidth());
        stage2.setY(stage.getY());
        if(showButton.getText().equals("Show")) {
            showButton.setText("Hide");
            Label label = controller2.getLabel();
            label.setText("I am a different stage!\n" + ++count);
            stage2.show();
        } else {
            showButton.setText("Show");
            stage2.close();
        }
    }
}
