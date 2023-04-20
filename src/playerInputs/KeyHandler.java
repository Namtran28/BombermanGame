package playerInputs;


import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashSet;
import java.util.Set;


public class KeyHandler implements EventHandler<KeyEvent> {
    private final Set<KeyCode> keyCodes = new HashSet<>();

    @Override
    public void handle(KeyEvent keyEvent) {
        if (KeyEvent.KEY_PRESSED.equals(keyEvent.getEventType())) {
            keyCodes.add(keyEvent.getCode());
        }
        if (KeyEvent.KEY_RELEASED.equals(keyEvent.getEventType())) {
            keyCodes.remove(keyEvent.getCode());
        }
    }

    public KeyHandler(Scene scene) {
        scene.setOnKeyPressed(this);
        scene.setOnKeyReleased(this);
    }

    public boolean isPressed(KeyCode keyCode) {
        return keyCodes.contains(keyCode);
    }

    public boolean isReleased() {return keyCodes.size() == 0;}
}
