package com.library.view.librarian;

import com.library.config.AppContext;
import com.library.model.items.Item;
import com.library.service.ItemService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class EditItemsController {

	@FXML
	private TextField searchField;
	@FXML
	private VBox resultsContainer;
	@FXML
	private Label emptyLabel;

	private ItemService itemService;

	@FXML
	public void initialize() {
		itemService = AppContext.getInstance().itemService;
		showResults(itemService.searchByTitle(""));
	}

	@FXML
	private void handleSearch(KeyEvent event) {
		showResults(itemService.searchByTitle(searchField.getText()));
	}

	private void showResults(List<Item> items) {
		resultsContainer.getChildren().clear();
		emptyLabel.setVisible(items.isEmpty());

		for (Item item : items) {
			resultsContainer.getChildren().add(createItemRow(item));
		}
	}

	private HBox createItemRow(Item item) {
		HBox row = new HBox();
		row.getStyleClass().add("loan-row");
		row.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

		VBox info = new VBox(4);
		Label title = new Label(item.getItemTitle());
		title.getStyleClass().add("loan-row-title");
		// creator field stores copy count from our query
		Label subtitle = new Label(item.getItemType() + " • " + item.getCreator());
		subtitle.getStyleClass().add("loan-row-due");
		info.getChildren().addAll(title, subtitle);

		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);

		Button editBtn = new Button("Edit");
		editBtn.getStyleClass().add("badge");
		editBtn.setOnAction(e -> openEditPopup(item));

		Button addCopyBtn = new Button("Add Copy");
		addCopyBtn.getStyleClass().add("reservation-status-ready");
		addCopyBtn.setOnAction(e -> openAddCopyPopup(item));

		row.setSpacing(8);
		row.getChildren().addAll(info, spacer, editBtn, addCopyBtn);
		return row;
	}

	private void openEditPopup(Item item) {
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/com/library/edit_item_popup.fxml"));
			Parent root = loader.load();

			EditItemPopupController controller = loader.getController();
			controller.setItem(item);

			Stage stage = new Stage();
			stage.setTitle("Edit Item");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(root));
			stage.showAndWait();

			// Refresh results after edit
			showResults(itemService.searchByTitle(searchField.getText()));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void openAddCopyPopup(Item item) {
		// TODO: open add copy popup pre-filled with this item's ID
	}
}