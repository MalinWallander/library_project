package com.library.view.librarian;

import com.library.config.AppContext;
import com.library.service.LoanService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddLoanController {

	@FXML
	private TextField copyIdField;

	@FXML
	private TextField userIdField;

	private LoanService loanService;

	public void setLoanService(LoanService loanService) {
		this.loanService = loanService;
	}

	@FXML
	private void handleSubmit(ActionEvent event) {
		String copyId = copyIdField.getText();
		String userId = userIdField.getText();
		setLoanService(AppContext.getInstance().loanService);
		loanService.addLoan(copyId, userId);

		System.out.println("Loan created — copyId: " + copyId + ", userId: " + userId);

		Stage stage = (Stage) copyIdField.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void handleCancel(ActionEvent event) {
		Stage stage = (Stage) copyIdField.getScene().getWindow();
		stage.close();
	}
}