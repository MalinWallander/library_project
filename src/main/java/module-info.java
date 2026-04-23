module com.library {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring;
    requires java.sql;
    requires com.zaxxer.hikari;

    opens com.library to javafx.graphics, javafx.fxml;
    opens com.library.view.librarian to javafx.fxml;
    opens com.library.view.shared to javafx.fxml;
    opens com.library.model.items to javafx.base;
    opens com.library.db to javafx.base;
    opens com.library.view.login to javafx.fxml;
    opens com.library.view.user to javafx.fxml;




    exports com.library;
}
