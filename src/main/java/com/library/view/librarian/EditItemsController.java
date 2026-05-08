package com.library.view.librarian;

import com.library.App;
import com.library.config.AppContext;
import com.library.model.items.Copy;
import com.library.model.items.Item;
import com.library.service.ItemService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
			resultsContainer.getChildren().add(createItemSection(item));
		}
	}

	private VBox createItemSection(Item item) {
		VBox section = new VBox();
		section.getStyleClass().add("loan-row");
		section.setSpacing(0);

		// ── Item header row ──
		HBox header = new HBox(8);
		header.setAlignment(Pos.CENTER_LEFT);
		header.setStyle("-fx-padding: 10 12 10 12;");

		VBox info = new VBox(4);
		Label title = new Label(item.getItemTitle());
		title.getStyleClass().add("loan-row-title");
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

		// Toggle button to expand/collapse copies
		Button toggleBtn = new Button("▶ Copies");
		toggleBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #888; " +
				"-fx-cursor: hand; -fx-font-size: 12px;");

		header.getChildren().addAll(info, spacer, editBtn, addCopyBtn, toggleBtn);

		// ── Copies container (hidden by default) ──
		VBox copiesContainer = new VBox(4);
		copiesContainer.setVisible(false);
		copiesContainer.setManaged(false);
		copiesContainer.setStyle("-fx-padding: 0 12 10 24; -fx-background-color: #f9f9f7;");

		toggleBtn.setOnAction(e -> {
			boolean expanded = copiesContainer.isVisible();
			if (!expanded) {
				// Load copies lazily when first expanded
				copiesContainer.getChildren().clear();
				List<Copy> copies = itemService.getCopiesForItem(item.getItemId());
				if (copies.isEmpty()) {
					Label none = new Label("No active copies.");
					none.getStyleClass().add("loan-row-due");
					copiesContainer.getChildren().add(none);
				} else {
					for (Copy copy : copies) {
						copiesContainer.getChildren().add(createCopyRow(copy, copiesContainer, item));
					}
				}
				toggleBtn.setText("▼ Copies");
			} else {
				toggleBtn.setText("▶ Copies");
			}
			copiesContainer.setVisible(!expanded);
			copiesContainer.setManaged(!expanded);
		});

		section.getChildren().addAll(header, copiesContainer);
		return section;
	}

	private HBox createCopyRow(Copy copy, VBox copiesContainer, Item item) {
		HBox row = new HBox(8);
		row.setAlignment(Pos.CENTER_LEFT);
		row.setStyle("-fx-padding: 6 0 6 0; -fx-border-color: transparent transparent #eeeeee transparent;");

		// Status dot color
		String dotColor = "Available".equals(copy.getStatus()) ? "#2d5a3d" : "#c0392b";
		Label statusDot = new Label("●");
		statusDot.setStyle("-fx-text-fill: " + dotColor + "; -fx-font-size: 10px;");

		Label barcodeLabel = new Label(copy.getBarcode());
		barcodeLabel.getStyleClass().add("loan-row-title");

		Label statusLabel = new Label(copy.getStatus());
		statusLabel.getStyleClass().add("loan-row-due");

		Label locationLabel = new Label(copy.getLocation() != null ? copy.getLocation() : "—");
		locationLabel.getStyleClass().add("loan-row-due");

		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);

		Button editCopyBtn = new Button("Edit");
		editCopyBtn.setStyle("-fx-background-color: #e8eeea; -fx-text-fill: #2d5a3d; " +
				"-fx-background-radius: 6px; -fx-padding: 3 8 3 8; " +
				"-fx-font-size: 11px; -fx-cursor: hand;");
		editCopyBtn.setOnAction(e -> openEditCopyPopup(copy, copiesContainer, item));

		Button withdrawBtn = new Button("Withdraw");
		withdrawBtn.setStyle("-fx-background-color: #c0392b; -fx-text-fill: white; " +
				"-fx-background-radius: 6px; -fx-padding: 3 8 3 8; " +
				"-fx-font-size: 11px; -fx-cursor: hand;");
		withdrawBtn.setOnAction(e -> handleWithdrawCopy(copy, copiesContainer, item));

		row.getChildren().addAll(statusDot, barcodeLabel, statusLabel, locationLabel,
				spacer, editCopyBtn, withdrawBtn);
		return row;
	}

	private void handleWithdrawCopy(Copy copy, VBox copiesContainer, Item item) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Withdraw Copy");
		alert.setHeaderText("Withdraw copy \"" + copy.getBarcode() + "\"?");
		alert.setContentText("This copy will be marked as withdrawn and removed from circulation.");

		alert.showAndWait().ifPresent(response -> {
			if (response == javafx.scene.control.ButtonType.OK) {
				try {
					itemService.withdrawCopy(copy.getCopyId());
					// Refresh just the copies list
					copiesContainer.getChildren().clear();
					List<Copy> copies = itemService.getCopiesForItem(item.getItemId());
					if (copies.isEmpty()) {
						Label none = new Label("No active copies.");
						none.getStyleClass().add("loan-row-due");
						copiesContainer.getChildren().add(none);
					} else {
						for (Copy c : copies) {
							copiesContainer.getChildren().add(createCopyRow(c, copiesContainer, item));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void openEditCopyPopup(Copy copy, VBox copiesContainer, Item item) {
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/com/library/edit_copy_popup.fxml"));
			Parent root = loader.load();

			EditCopyPopupController controller = loader.getController();
			controller.setCopy(copy);

			Stage stage = new Stage();
			stage.setTitle("Edit Copy");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(root));
			stage.showAndWait();

			// Refresh copies list after edit
			copiesContainer.getChildren().clear();
			List<Copy> copies = itemService.getCopiesForItem(item.getItemId());
			if (copies.isEmpty()) {
				Label none = new Label("No active copies.");
				none.getStyleClass().add("loan-row-due");
				copiesContainer.getChildren().add(none);
			} else {
				for (Copy c : copies) {
					copiesContainer.getChildren().add(createCopyRow(c, copiesContainer, item));
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void openEditPopup(Item item) {
		try {
			Item fullItem = itemService.findById(item.getItemId());
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/com/library/edit_item_popup.fxml"));
			Parent root = loader.load();

			EditItemPopupController controller = loader.getController();
			controller.setItem(fullItem);

			Stage stage = new Stage();
			stage.setTitle("Edit Item");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(root));
			stage.showAndWait();

			showResults(itemService.searchByTitle(searchField.getText()));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void openAddCopyPopup(Item item) {
    try {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/com/library/add_item.fxml"));
        Parent root = loader.load();

        AddItemController controller = loader.getController();
        controller.setItemService(AppContext.getInstance().itemService);
        controller.preSelectItem(item.getItemId(), item.getItemTitle());

        Stage stage = new Stage();
        stage.setTitle("Add Copy – " + item.getItemTitle());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(App.createStyledScene(root, 600, 500));
        stage.showAndWait();

        // Refresh results after copy is added
        showResults(itemService.searchByTitle(searchField.getText()));

    } catch (IOException e) {
        e.printStackTrace();
    }
}
}