/*
 * Course: CSC1120
 * Gaudy CSS Example
 */
package week3.css;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * No one should ever make their program look like this
 */
public class Controller implements Initializable {
    @FXML
    private Button button1;
    @FXML
    private Button button2;

    private final List<Color> colors = List.of(
            Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.PURPLE
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        animateNeonEffect(button1);
        animateNeonEffect(button2);
        animateBackground(button1.getParent());
    }

    private void animateBackground(Parent root) {
        final int timer = 5;
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), _ -> root.setStyle(
                        "-fx-background-color: " +
                                "linear-gradient(to right, #ff0000, #ff7300, #ffff00, " +
                                "#48ff00, #00ffff, #0000ff, #8000ff);"
                )),
                new KeyFrame(Duration.seconds(timer), _ -> root.setStyle(
                        "-fx-background-color: " +
                                "linear-gradient(to right, #8000ff, #0000ff, #00ffff, " +
                                "#48ff00, #ffff00, #ff7300, #ff0000);"
                ))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.play();
    }

    private void animateNeonEffect(Button button) {
        final int dropShadow = 15;
        final double keyFrameTime = 0.5;
        DropShadow glow = new DropShadow(dropShadow, Color.RED);
        button.setEffect(glow);

        Timeline timeline = new Timeline();
        for (int i = 0; i < colors.size(); i++) {
            final Color color = colors.get(i);
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(i * keyFrameTime),
                    _ -> {
                        button.setStyle("-fx-border-color: " + toHex(color) + ";");
                        glow.setColor(color);
                    }));
        }
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private String toHex(Color color) {
        final int colorScalar = 255;
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * colorScalar),
                (int) (color.getGreen() * colorScalar),
                (int) (color.getBlue() * colorScalar));
    }
}