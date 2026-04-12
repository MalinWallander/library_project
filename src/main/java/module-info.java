module com.library {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring;
    requires java.sql;
    requires com.zaxxer.hikari;

    opens com.library to javafx.graphics, javafx.fxml;
    opens com.library.view.librarian to javafx.fxml;

    exports com.library;
}
