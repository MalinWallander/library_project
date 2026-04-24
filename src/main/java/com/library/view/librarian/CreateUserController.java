package com.library.view.librarian;

import java.time.LocalDate;

import com.library.service.UserService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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

	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@FXML
	private void handleSubmit(ActionEvent event) {
		String fName = fNameField.getText();
		String lName = lNameField.getText();
		String email = emailField.getText();
		String phone = phoneField.getText();
		String role = roleDropdown.getValue();
		LocalDate dob = dobPicker.getValue();
		userService.createUser(fName, lName, email, dob, role, phone);
		System.out.println("hello " + fName + " " + lName + ", email: " + email + ", phone: " + phone + ", date of birth: "
				+ dob + ", role: " + role);

		Stage stage = (Stage) fNameField.getScene().getWindow();
		stage.close();
	}
}
