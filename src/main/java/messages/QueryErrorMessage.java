package messages;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class QueryErrorMessage {

    public static void getQueryErrorMessage(Label id) {
        id.setTextFill(Color.RED);
        id.setText("Query Error");
    }
}
