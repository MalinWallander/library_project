package com.library.view.librarian;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.library.model.items.Book;
import com.library.model.items.Copy;
import com.library.model.items.Dvd;
import com.library.model.items.Item;
import com.library.service.ItemService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class AddItemController implements Initializable {

	// ── Item form fields ──
	@FXML
	private TextField itemTitleField;
	@FXML
	private ComboBox<String> itemTypeCombo;
	@FXML
	private TextField categoryIdField;
	@FXML
	private Label itemFeedbackLabel;

	@FXML
	private VBox bookFields;
	@FXML
	private TextField isbnField;
	@FXML
	private TextField genreField;
	@FXML
	private TextField mainAuthorField;
	@FXML
	private TextField publisherIdField;

	@FXML
	private VBox dvdFields;
	@FXML
	private TextField productionYearField;
	@FXML
	private TextField mainDirectorField;

	// ── Inline copy section (shown after item is added) ──
	@FXML
	private VBox inlineCopySection;
	@FXML
	private Label inlineCopyItemIdLabel;
	@FXML
	private VBox copyRowsContainer;
	@FXML
	private Label inlineCopyFeedbackLabel;

	// ── Standalone copy form fields ──
	@FXML
	private TextField copyItemIdField;
	@FXML
	private TextField copyItemTitleField;
	@FXML
	private TextField barcodeField;
	@FXML
	private TextField locationField;
	@FXML
	private DatePicker purchaseDatePicker;
	@FXML
	private CheckBox referenceCopyCheck;
	@FXML
	private Label copyFeedbackLabel;

	private ItemService itemService;
	private String lastAddedItemId;
	private String lastAddedItemTitle;

	// Holds references to each dynamically created copy row
	private final List<CopyRow> copyRows = new ArrayList<>();

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		itemTypeCombo.getItems().addAll("Book", "DVD", "Magazine");
		inlineCopySection.setVisible(false);
		inlineCopySection.setManaged(false);
	}

	@FXML
	private void onItemTypeChanged() {
		String selected = itemTypeCombo.getValue();
		boolean isBook = "Book".equals(selected);
		boolean isDvd = "DVD".equals(selected);
		bookFields.setVisible(isBook);
		bookFields.setManaged(isBook);
		dvdFields.setVisible(isDvd);
		dvdFields.setManaged(isDvd);
	}

	@FXML
	private void onAddItem() {
		itemFeedbackLabel.setStyle("-fx-text-fill: red;");
		itemFeedbackLabel.setText("");

		try {
			Item item = buildItem(itemTypeCombo.getValue());
			itemService.addItem(item);

			lastAddedItemId = item.getItemId();
			lastAddedItemTitle = item.getItemTitle();

			itemFeedbackLabel.setStyle("-fx-text-fill: green;");
			itemFeedbackLabel.setText("Item added! ID: " + lastAddedItemId);

			// Show the inline copy section pre-filled with the new item
			showInlineCopySection(lastAddedItemId, lastAddedItemTitle);
			clearItemForm();

		} catch (IllegalArgumentException e) {
			itemFeedbackLabel.setText("Validation error: " + e.getMessage());
		} catch (RuntimeException e) {
			itemFeedbackLabel.setText("Error saving item: " + e.getMessage());
		}
	}

	private void showInlineCopySection(String itemId, String itemTitle) {
		inlineCopyItemIdLabel.setText("Adding copies for item: " + itemTitle + " (ID: " + itemId + ")");
		copyRows.clear();
		copyRowsContainer.getChildren().clear();
		addCopyRow(); // start with one empty row
		inlineCopySection.setVisible(true);
		inlineCopySection.setManaged(true);
		inlineCopyFeedbackLabel.setText("");
	}

	// Called by the "＋ Add another copy" button
	@FXML
	private void onAddCopyRow() {
		addCopyRow();
	}

	private void addCopyRow() {
		CopyRow row = new CopyRow(copyRows.size() + 1);
		copyRows.add(row);
		copyRowsContainer.getChildren().add(row.getNode());
	}

	// Submit all copy rows at once
	@FXML
	private void onAddCopiesForItem() {
		inlineCopyFeedbackLabel.setStyle("-fx-text-fill: red;");
		inlineCopyFeedbackLabel.setText("");

		if (copyRows.isEmpty()) {
			inlineCopyFeedbackLabel.setText("Add at least one copy row.");
			return;
		}

		int successCount = 0;
		List<String> errors = new ArrayList<>();

		for (int i = 0; i < copyRows.size(); i++) {
			CopyRow row = copyRows.get(i);
			try {
				Copy copy = row.buildCopy(lastAddedItemId, lastAddedItemTitle);
				itemService.addCopy(copy);
				successCount++;
			} catch (RuntimeException e) {
				errors.add("Row " + (i + 1) + ": " + e.getMessage());
			}
		}

		if (errors.isEmpty()) {
			inlineCopyFeedbackLabel.setStyle("-fx-text-fill: green;");
			inlineCopyFeedbackLabel
					.setText(successCount + " cop" + (successCount == 1 ? "y" : "ies") + " added successfully!");
			// Hide the section after a successful save
			inlineCopySection.setVisible(false);
			inlineCopySection.setManaged(false);
		} else {
			String msg = successCount + " saved. Errors:\n" + String.join("\n", errors);
			inlineCopyFeedbackLabel.setText(msg);
		}
	}

	@FXML
	private void onSkipCopies() {
		inlineCopySection.setVisible(false);
		inlineCopySection.setManaged(false);
	}

	// ── Standalone copy form ──

	@FXML
	private void onAddCopy() {
		copyFeedbackLabel.setStyle("-fx-text-fill: red;");
		copyFeedbackLabel.setText("");
		try {
			Copy copy = buildStandaloneCopy();
			itemService.addCopy(copy);
			copyFeedbackLabel.setStyle("-fx-text-fill: green;");
			copyFeedbackLabel.setText("Copy added successfully! ID: " + copy.getCopyId());
			clearStandaloneCopyForm();
		} catch (IllegalArgumentException e) {
			copyFeedbackLabel.setText("Validation error: " + e.getMessage());
		} catch (RuntimeException e) {
			copyFeedbackLabel.setText("Error saving copy: " + e.getMessage());
		}
	}

	// ── Builders ──

	private Item buildItem(String type) {
		if (type == null)
			throw new IllegalArgumentException("Please select an item type.");
		return switch (type) {
			case "Book" -> buildBook();
			case "DVD" -> buildDvd();
			// case "Magazine" -> buildMagazine();
			default -> throw new IllegalArgumentException("Unknown type: " + type);
		};
	}

	private Book buildBook() {
		Book book = new Book();
		book.setItemTitle(itemTitleField.getText().trim());
		book.setItemType("Book");
		book.setCategoryId(categoryIdField.getText().trim());
		book.setIsbn(isbnField.getText().trim());
		book.setGenre(genreField.getText().trim());
		book.setMainAuthorName(mainAuthorField.getText().trim());
		book.setPublisherId(publisherIdField.getText().trim());
		return book;
	}

	private Dvd buildDvd() {
		Dvd dvd = new Dvd();
		dvd.setItemTitle(itemTitleField.getText().trim());
		dvd.setItemType("DVD");
		dvd.setCategoryId(categoryIdField.getText().trim());
		dvd.setMainDirectorName(mainDirectorField.getText().trim());
		String yearText = productionYearField.getText().trim();
		if (!yearText.isEmpty()) {
			try {
				dvd.setProductionYear(Integer.parseInt(yearText));
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Production year must be a number.");
			}
		}
		return dvd;
	}

	// private Item buildMagazine() {
	// Item magazine = new Item();
	// magazine.setItemTitle(itemTitleField.getText().trim());
	// magazine.setItemType("Magazine");
	// magazine.setCategoryId(categoryIdField.getText().trim());
	// return magazine;
	// }

	private Copy buildStandaloneCopy() {
		Copy copy = new Copy();
		copy.setItemId(copyItemIdField.getText().trim());
		copy.setItemTitle(copyItemTitleField.getText().trim());
		copy.setBarcode(barcodeField.getText().trim());
		copy.setLocation(locationField.getText().trim());
		copy.setReferenceCopy(referenceCopyCheck.isSelected());
		copy.setStatus("Available");
		if (purchaseDatePicker.getValue() != null)
			copy.setPurchaseDate(purchaseDatePicker.getValue());
		return copy;
	}

	// ── Clear helpers ──

	private void clearItemForm() {
		itemTitleField.clear();
		itemTypeCombo.setValue(null);
		categoryIdField.clear();
		isbnField.clear();
		genreField.clear();
		mainAuthorField.clear();
		publisherIdField.clear();
		productionYearField.clear();
		mainDirectorField.clear();
		bookFields.setVisible(false);
		bookFields.setManaged(false);
		dvdFields.setVisible(false);
		dvdFields.setManaged(false);
	}

	private void clearStandaloneCopyForm() {
		copyItemIdField.clear();
		copyItemTitleField.clear();
		barcodeField.clear();
		locationField.clear();
		purchaseDatePicker.setValue(null);
		referenceCopyCheck.setSelected(false);
	}

	// ── Inner class: one dynamic copy row ──

	private static class CopyRow {
		private final TextField barcodeField = new TextField();
		private final TextField locationField = new TextField();
		private final DatePicker datePicker = new DatePicker();
		private final CheckBox referenceCheck = new CheckBox("Reference copy");
		private final VBox node;

		CopyRow(int rowNumber) {
			barcodeField.setPromptText("Barcode *");
			locationField.setPromptText("Location");
			datePicker.setPromptText("Purchase date");

			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(6);

			ColumnConstraints col1 = new ColumnConstraints(110);
			ColumnConstraints col2 = new ColumnConstraints();
			col2.setHgrow(Priority.ALWAYS);
			grid.getColumnConstraints().addAll(col1, col2);

			grid.add(new Label("Barcode *"), 0, 0);
			grid.add(barcodeField, 1, 0);
			grid.add(new Label("Location"), 0, 1);
			grid.add(locationField, 1, 1);
			grid.add(new Label("Purchase date"), 0, 2);
			grid.add(datePicker, 1, 2);
			grid.add(referenceCheck, 1, 3);

			node = new VBox(4);
			node.setPadding(new Insets(8, 0, 8, 0));
			node.setStyle("-fx-border-color: #dddddd; -fx-border-radius: 4; -fx-padding: 10;");

			Label rowLabel = new Label("Copy #" + rowNumber);
			rowLabel.setStyle("-fx-font-weight: bold;");
			node.getChildren().addAll(rowLabel, grid);
		}

		VBox getNode() {
			return node;
		}

		Copy buildCopy(String itemId, String itemTitle) {
			if (barcodeField.getText().isBlank())
				throw new IllegalArgumentException("Barcode is required.");
			Copy copy = new Copy();
			copy.setItemId(itemId);
			copy.setItemTitle(itemTitle);
			copy.setBarcode(barcodeField.getText().trim());
			copy.setLocation(locationField.getText().trim());
			copy.setReferenceCopy(referenceCheck.isSelected());
			copy.setStatus("Available");
			if (datePicker.getValue() != null)
				copy.setPurchaseDate(datePicker.getValue());
			return copy;
		}
	}
}