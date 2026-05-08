package com.library.view.librarian;

import com.library.config.AppContext;
import com.library.service.ReturnCopyService;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ReturnCopyController {

	@FXML
	private TextField barcodeField;

	@FXML
	private Label feedbackLabel;

	private ReturnCopyService returnCopyService;

	public void setReturnCopyService(ReturnCopyService returnCopyService) {
		this.returnCopyService = returnCopyService;
	}

	@FXML
	private void handleReturn() {

		setReturnCopyService(AppContext.getInstance().returnCopyService);

		String barcode = barcodeField.getText();

		if (barcode == null || barcode.isBlank()) {
			showError("Please enter a barcode.");
			return;
		}

		try {
			String copyId = returnCopyService.returnCopy(barcode);
			showSuccess("Copy returned successfully. Copy ID: " + copyId);
			barcodeField.clear();
		} catch (Exception e) {
			System.out.println("Error returning copy: " + e.getMessage());
		}
	}

	private void showError(String message) {
		feedbackLabel.setStyle("-fx-text-fill: red;");
		feedbackLabel.setText(message);
	}

	private void showSuccess(String message) {
		feedbackLabel.setStyle("-fx-text-fill: green;");
		feedbackLabel.setText(message);
	}
}
