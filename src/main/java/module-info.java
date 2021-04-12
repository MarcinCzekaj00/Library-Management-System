module org.library {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires mysql.connector.java;
    requires jbcrypt;

    opens org.library to javafx.fxml;
    exports org.library;
    opens org.library.controllers to javafx.fxml;
    exports org.library.controllers;
    opens org.library.data to javafx.fxml;
    exports org.library.data;
}