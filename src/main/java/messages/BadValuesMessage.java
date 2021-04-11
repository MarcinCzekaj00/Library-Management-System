package messages;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class BadValuesMessage {
    public static void getBadValuesMessage (Label label){
        label.setTextFill(Color.RED);
        label.setText("Bad values!");
    }
}
