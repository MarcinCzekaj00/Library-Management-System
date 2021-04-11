module org.library {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires mysql.connector.java;

    opens org.library to javafx.fxml;
    exports org.library;
    opens controllers to javafx.fxml;
    exports controllers;
    opens data to javafx.fxml;
    exports data;
}