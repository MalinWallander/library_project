package com.library.view.librarian;

import com.library.service.EmployeeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateEmployeeController {

    @FXML
    private TextField fNameField;
    @FXML
    private TextField lNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label messageLabel;

    private EmployeeService employeeService;

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @FXML
    private void handleSubmit(ActionEvent event) { // TODO: Event unused, remove?
        try {
            employeeService.createEmployee(
                    fNameField.getText(),
                    lNameField.getText(),
                    emailField.getText(),
                    phoneField.getText(),
                    passwordField.getText()
            );

            Stage stage = (Stage) fNameField.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            messageLabel.setText(e.getMessage());
        }
    }
}
