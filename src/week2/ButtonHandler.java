/*
 * Course: CSC1120
 * Event Handler
 */
package week2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.Bloom;

/**
 * An Event Handler for a button that adds a Bloom effect to the button
 */
public class ButtonHandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent e) {
        Button b = (Button) e.getSource();
        if(b.getEffect() == null) {
            b.setEffect(new Bloom());
        } else {
            b.setEffect(null);
        }
    }
}
