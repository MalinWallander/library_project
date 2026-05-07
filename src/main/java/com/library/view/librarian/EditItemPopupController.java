package com.library.view.librarian;

import com.library.config.AppContext;
import com.library.model.items.Book;
import com.library.model.items.Dvd;
import com.library.model.items.Item;
import com.library.service.ItemService;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EditItemPopupController {

	@FXML
	private ComboBox<String> typeCombo;
	@FXML
	private TextField titleField;
	@FXML
	private TextField categoryField;
	@FXML
	private VBox bookFields;
	@FXML
	private TextField authorField;
	@FXML
	private TextField isbnField;
	@FXML
	private TextField genreField;
	@FXML
	private TextField publisherField;
	@FXML
	private VBox dvdFields;
	@FXML
	private TextField directorField;
	@FXML
	private TextField yearField;
	@FXML
	private Label feedbackLabel;

	private Item item;
	private ItemService itemService;

	@FXML
	public void initialize() {
		itemService = AppContext.getInstance().itemService;
		typeCombo.getItems().addAll("Book", "DVD", "Magazine");
		typeCombo.setOnAction(e -> updateFieldVisibility());
	}

	public void setItem(Item item) {
		this.item = item;

		// Pre-fill shared fields
		typeCombo.setValue(item.getItemType());
		titleField.setText(item.getItemTitle());
		categoryField.setText(item.getCategoryId());

		// Pre-fill type-specific fields
		if (item instanceof Book book) {
			authorField.setText(book.getMainAuthorName() != null ? book.getMainAuthorName() : "");
			isbnField.setText(book.getIsbn() != null ? book.getIsbn() : "");
			genreField.setText(book.getGenre() != null ? book.getGenre() : "");
			publisherField.setText(book.getPublisherId() != null ? book.getPublisherId() : "");
		} else if (item instanceof Dvd dvd) {
			directorField.setText(dvd.getMainDirectorName() != null ? dvd.getMainDirectorName() : "");
			yearField.setText(dvd.getProductionYear() != null ? dvd.getProductionYear().toString() : "");
		}

		updateFieldVisibility();
	}

	private void updateFieldVisibility() {
		String type = typeCombo.getValue();
		boolean isBook = "Book".equals(type);
		boolean isDvd = "DVD".equals(type);
		bookFields.setVisible(isBook);
		bookFields.setManaged(isBook);
		dvdFields.setVisible(isDvd);
		dvdFields.setManaged(isDvd);
	}

	@FXML
	private void handleSave() {
		try {
			item.setItemTitle(titleField.getText().trim());
			item.setCategoryId(categoryField.getText().trim());

			if (item instanceof Book book) {
				book.setMainAuthorName(authorField.getText().trim());
				book.setIsbn(isbnField.getText().trim());
				book.setGenre(genreField.getText().trim());
				book.setPublisherId(publisherField.getText().trim());
			} else if (item instanceof Dvd dvd) {
				dvd.setMainDirectorName(directorField.getText().trim());
				String year = yearField.getText().trim();
				if (!year.isEmpty())
					dvd.setProductionYear(Integer.parseInt(year));
			}

			itemService.updateItem(item);

			feedbackLabel.setStyle("-fx-text-fill: green;");
			feedbackLabel.setText("Saved successfully!");

		} catch (Exception e) {
			feedbackLabel.setStyle("-fx-text-fill: red;");
			feedbackLabel.setText("Error: " + e.getMessage());
		}
	}

	@FXML
	private void handleCancel() {
		Stage stage = (Stage) titleField.getScene().getWindow();
		stage.close();
	}
}