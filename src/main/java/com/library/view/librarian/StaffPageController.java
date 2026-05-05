package com.library.view.librarian;

import java.io.IOException;

import com.library.App;
import com.library.config.AppContext;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StaffPageController {

	@FXML
	private VBox checkoutCard;
	@FXML
	private VBox checkinCard;
	@FXML
	private VBox searchCard;
	@FXML
	private VBox addUserCard;
	@FXML
	private VBox inventoryCard;

	@FXML
	public void initialize() {
		// Any setup logic when the page loads
	}

	@FXML
	private void handleLogout() {
		App.setRoot("first_page");
	}

	@FXML
	private void handleCheckout() {
		App.setRoot("add_loan");
	}

	@FXML
	private void handleCheckin() {
		App.setRoot("return_copy");
	}

	@FXML
	private void handleSearch() {
		App.setRoot("search_item");
	}

	@FXML
	private void handleCreateUser() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/library/create_user.fxml"));
			Parent root = loader.load();

			CreateUserController userController = loader.getController();
			userController.setUserService(AppContext.getInstance().userService);
			System.out.println("userService: " + AppContext.getInstance().userService);

			Stage popupStage = new Stage();
			popupStage.setTitle("Create User");
			popupStage.initModality(Modality.APPLICATION_MODAL);
			popupStage.setScene(new Scene(root));
			popupStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void handleInventory() {
		App.setRoot("inventory_page");
	}
}