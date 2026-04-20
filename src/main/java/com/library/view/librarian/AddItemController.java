package com.library.view.librarian;

import com.library.model.items.Book;
import com.library.model.items.Copy;
import com.library.model.items.Dvd;
import com.library.model.items.Item;
import com.library.service.ItemService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

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

	// Book-specific
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

	// DVD-specific
	@FXML
	private VBox dvdFields;
	@FXML
	private TextField productionYearField;
	@FXML
	private TextField mainDirectorField;

	// ── Copy form fields ──
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

	// Injected via setter or constructor before load
	private ItemService itemService;

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		itemTypeCombo.getItems().addAll("Book", "DVD", "Magazine");
	}

	// Show/hide type-specific fields when the combo changes
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

		String type = itemTypeCombo.getValue();

		try {
			Item item = buildItem(type);
			itemService.addItem(item);

			itemFeedbackLabel.setStyle("-fx-text-fill: green;");
			itemFeedbackLabel.setText("Item added successfully! ID: " +
					item.getItemId());
			clearItemForm();

		} catch (IllegalArgumentException e) {
			itemFeedbackLabel.setText("Validation error: " + e.getMessage());
		} catch (RuntimeException e) {
			itemFeedbackLabel.setText("Error saving item: " + e.getMessage());
		}
	}

	@FXML
	private void onAddCopy() {

		copyFeedbackLabel.setStyle("-fx-text-fill: red;");
		copyFeedbackLabel.setText("");

		try {
			Copy copy = buildCopy();
			itemService.addCopy(copy);

			copyFeedbackLabel.setStyle("-fx-text-fill: green;");
			copyFeedbackLabel.setText("Copy added successfully! ID: " +
					copy.getCopyId());
			clearCopyForm();

		} catch (IllegalArgumentException e) {
			copyFeedbackLabel.setText("Validation error: " + e.getMessage());
		} catch (RuntimeException e) {
			copyFeedbackLabel.setText("Error saving copy: " + e.getMessage());
		}
	}

	// ── Builders ──

	private Item buildItem(String type) {
		if (type == null) {
			throw new IllegalArgumentException("Please select an item type.");
		}
		return switch (type) {
			case "Book" -> buildBook();
			case "DVD" -> buildDvd();
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

	private Copy buildCopy() {
		Copy copy = new Copy();
		copy.setItemId(copyItemIdField.getText().trim());
		copy.setItemTitle(copyItemTitleField.getText().trim());
		copy.setBarcode(barcodeField.getText().trim());
		copy.setLocation(locationField.getText().trim());
		copy.setReferenceCopy(referenceCopyCheck.isSelected());
		copy.setStatus("Available");

		if (purchaseDatePicker.getValue() != null) {
			copy.setPurchaseDate(purchaseDatePicker.getValue());
		}
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

	private void clearCopyForm() {
		copyItemIdField.clear();
		copyItemTitleField.clear();
		barcodeField.clear();
		locationField.clear();
		purchaseDatePicker.setValue(null);
		referenceCopyCheck.setSelected(false);
	}
}