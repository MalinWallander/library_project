package com.library.view.librarian;

import java.time.LocalDate;

import com.library.service.UserService;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateUserController {
    @FXML
    private TextField fNameField;
    @FXML
    private TextField lNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private ComboBox<String> roleDropdown;
    @FXML
    private DatePicker dobPicker;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label messageLabel;

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @FXML
    private void handleSubmit() {
        try {
            String fName = fNameField.getText();
            String lName = lNameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String role = roleDropdown.getValue();
            LocalDate dob = dobPicker.getValue();
            String password = passwordField.getText();

            userService.createUser(fName, lName, email, dob, role, phone, password);

            Stage stage = (Stage) fNameField.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            messageLabel.setText(e.getMessage());
        }
    }
}
