package com.library.view.librarian;

import java.io.IOException;

import com.library.config.AppContext;
import com.library.model.administration.Loan;
import com.library.model.administration.Receipt;
import com.library.service.LoanService;
import com.library.view.shared.ReceiptController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

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
		try {
			String copyId = copyIdField.getText().trim();
			String userId = userIdField.getText().trim();
			if (copyId.isBlank() || userId.isBlank()) {
				showError("Both Copy ID and User ID are required.");
				return;
			}

			if (loanService == null) {
				setLoanService(AppContext.getInstance().loanService);
			}

			Loan loan = loanService.addLoan(copyId, userId);
			Receipt receipt = loanService.receipt(loan.getLoanId().toString())
					.orElseThrow(() -> new IllegalStateException("Could not load receipt data."));

			Stage stage = (Stage) copyIdField.getScene().getWindow();
			Window owner = stage.getOwner();
			stage.close();

			showReceipt(receipt, owner);
		} catch (RuntimeException | IOException e) {
			showError("Could not create loan: " + e.getMessage());
		}
	}

	@FXML
	private void handleCancel(ActionEvent event) {
		Stage stage = (Stage) copyIdField.getScene().getWindow();
		stage.close();
	}

	private void showReceipt(Receipt receipt, Window owner) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/library/receipt.fxml"));
		Parent root = loader.load();

		ReceiptController receiptController = loader.getController();
		receiptController.receipt(receipt);

		Stage receiptStage = new Stage();
		receiptStage.setTitle("Receipt");
		if (owner != null) {
			receiptStage.initOwner(owner);
			receiptStage.initModality(Modality.WINDOW_MODAL);
		} else {
			receiptStage.initModality(Modality.APPLICATION_MODAL);
		}
		receiptStage.setScene(new Scene(root));
		receiptStage.showAndWait();
	}

	private void showError(String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Loan Error");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
