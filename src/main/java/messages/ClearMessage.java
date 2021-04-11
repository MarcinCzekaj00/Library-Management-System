package messages;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class ClearMessage {
    public static void clear(Label l) {
        l.setText("");
    }
}
