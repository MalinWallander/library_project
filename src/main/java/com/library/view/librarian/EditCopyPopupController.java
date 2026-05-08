package com.library.view.librarian;

import com.library.config.AppContext;
import com.library.model.items.Copy;
import com.library.service.ItemService;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditCopyPopupController {

	@FXML
	private Label itemTitleLabel;
	@FXML
	private TextField barcodeField;
	@FXML
	private TextField locationField;
	@FXML
	private ComboBox<String> statusCombo;
	@FXML
	private DatePicker purchaseDatePicker;
	@FXML
	private CheckBox referenceCopyCheck;
	@FXML
	private Label feedbackLabel;

	private Copy copy;
	private ItemService itemService;

	@FXML
	public void initialize() {
		itemService = AppContext.getInstance().itemService;
		statusCombo.getItems().addAll("Available", "On Loan", "Withdrawn");
	}

	public void setCopy(Copy copy) {
		this.copy = copy;

		itemTitleLabel.setText(copy.getItemTitle() != null ? copy.getItemTitle() : "");
		barcodeField.setText(copy.getBarcode() != null ? copy.getBarcode() : "");
		locationField.setText(copy.getLocation() != null ? copy.getLocation() : "");
		statusCombo.setValue(copy.getStatus() != null ? copy.getStatus() : "Available");
		purchaseDatePicker.setValue(copy.getPurchaseDate());
		referenceCopyCheck.setSelected(copy.isReferenceCopy());
	}

	@FXML
	private void handleSave() {
		try {
			if (barcodeField.getText().isBlank()) {
				feedbackLabel.setStyle("-fx-text-fill: red;");
				feedbackLabel.setText("Barcode is required.");
				return;
			}

			copy.setBarcode(barcodeField.getText().trim());
			copy.setLocation(locationField.getText().trim());
			copy.setStatus(statusCombo.getValue());
			copy.setPurchaseDate(purchaseDatePicker.getValue());
			copy.setReferenceCopy(referenceCopyCheck.isSelected());

			itemService.updateCopy(copy);

			feedbackLabel.setStyle("-fx-text-fill: green;");
			feedbackLabel.setText("Copy updated successfully!");

		} catch (Exception e) {
			feedbackLabel.setStyle("-fx-text-fill: red;");
			feedbackLabel.setText("Error: " + e.getMessage());
		}
	}

	@FXML
	private void handleCancel() {
		Stage stage = (Stage) barcodeField.getScene().getWindow();
		stage.close();
	}
}