package com.library.view.librarian;

import java.io.IOException;

import com.library.App;
import com.library.config.AppContext;
import com.library.view.shared.SearchController;

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
	private void handleCreateLoan() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/library/add_loan.fxml"));
			Parent root = loader.load();

			// Inject service from AppContext into the popup controller
			AddLoanController loanController = loader.getController();
			loanController.setLoanService(AppContext.getInstance().loanService);

			Stage popupStage = new Stage();
			popupStage.setTitle("Create Loan");
			popupStage.initOwner(checkoutCard.getScene().getWindow());
			popupStage.initModality(Modality.APPLICATION_MODAL);
			popupStage.setScene(App.createStyledScene(root));
			popupStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void handleReturnCopy() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/library/return_copy.fxml"));
			Parent root = loader.load();

			ReturnCopyController returnController = loader.getController();
			returnController.setReturnCopyService(AppContext.getInstance().returnCopyService);

			Stage popupStage = new Stage();
			popupStage.setTitle("Return Copy");
			popupStage.initModality(Modality.APPLICATION_MODAL);
			popupStage.setScene(App.createStyledScene(root));
			popupStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void handleSearch() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/library/search_item.fxml"));
			Parent root = loader.load();

			SearchController searchController = loader.getController();
			searchController.setSearchService(AppContext.getInstance().searchService);

			Stage popupStage = new Stage();
			popupStage.setTitle("Search Items");
			popupStage.initModality(Modality.APPLICATION_MODAL);
			popupStage.setScene(App.createStyledScene(root));
			popupStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
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
			popupStage.setScene(App.createStyledScene(root));
			popupStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void handleInventory() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/library/inventory.fxml"));
			Parent root = loader.load();

			Stage popupStage = new Stage();
			popupStage.setTitle("Manage Inventory");
			popupStage.initModality(Modality.APPLICATION_MODAL);
			popupStage.setScene(App.createStyledScene(root));
			popupStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
