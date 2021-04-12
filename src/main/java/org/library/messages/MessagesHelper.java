package org.library.messages;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class MessagesHelper {
    public static void getMessage(Label label, String string,String color) {
        label.setText(string);
        label.setTextFill(Color.valueOf(color));
    }
}
