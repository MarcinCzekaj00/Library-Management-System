package messages;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class SuccessMessage {
    public static void getSuccessMessage (Label label){
        label.setTextFill(Color.GREEN);
        label.setText("Success!");
    }
}
