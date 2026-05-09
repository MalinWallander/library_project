package com.library.view.shared;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.event.ActionEvent;
import java.io.IOException;

import com.library.App;
import com.library.config.AppContext;
import com.library.model.auth.AuthRole;
import com.library.service.AuthService;
import com.library.view.login.LoginController;

public class FirstPageController {

	@FXML
	private BorderPane rootPane;
	@FXML
	private VBox searchCard;
	@FXML
	private VBox loansCard;
	@FXML
	private VBox reservationsCard;

	// ── Nav bar ──────────────────────────────────────────

	@FXML
	private void handleLoginButton(ActionEvent event) {
		openLoginDialog();
	}

	// ── Card handlers ────────────────────────────────────

	@FXML
	private void handleSearchCard(javafx.scene.input.MouseEvent event) {
		openSearchDialog();
	}

	@FXML
	private void handleLoansCard(javafx.scene.input.MouseEvent event) {
		// Locked – prompt user to log in
		showLoginRequiredPopup("My Loans");
	}

	@FXML
	private void handleReservationsCard(javafx.scene.input.MouseEvent event) {
		// Locked – prompt user to log in
		showLoginRequiredPopup("My Reservations");
	}

	// ── Private helpers ───────────────────────────────────

	/**
	 * Opens the Login view as a modal popup.
	 */
	private void openLoginDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/com/library/login.fxml"));
			Parent root = loader.load();

			LoginController loginController = loader.getController();
			loginController.setAuthService(AppContext.getInstance().authService);
			loginController.setLoginTitle("Login");

			Stage dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.initStyle(StageStyle.DECORATED);
			dialog.setTitle("Login – City Library");
			dialog.setScene(new Scene(root));
			dialog.setResizable(false);
			dialog.showAndWait();

			// After dialog closes, check who logged in
			AuthService authService = AppContext.getInstance().authService;
			if (authService.isLoggedIn()) {
				if (authService.getCurrentRole() == AuthRole.EMPLOYEE) {
					App.setRoot("staff_page");
				} else {
					App.setRoot("user_page");
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			showError("Could not open the login screen.", e.getMessage());
		}
	}

	/**
	 * Opens the Search view as a modal popup.
	 */
	private void openSearchDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/com/library/search_item.fxml"));
			Parent root = loader.load();

			Stage dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.initStyle(StageStyle.DECORATED);
			dialog.setTitle("Search Catalog – City Library");
			dialog.setScene(new Scene(root, 700, 500));
			dialog.setResizable(true);
			dialog.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
			showError("Could not open the search screen.", e.getMessage());
		}
	}

	/**
	 * Informs the user that login is required and offers to open the login dialog.
	 */
	private void showLoginRequiredPopup(String featureName) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Login Required");
		alert.setHeaderText(featureName + " requires you to be logged in.");
		alert.setContentText("Would you like to log in now?");

		alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.CANCEL);

		alert.showAndWait().ifPresent(response -> {
			if (response == ButtonType.YES) {
				openLoginDialog();
			}
		});
	}

	/**
	 * Shows a simple error alert.
	 */
	private void showError(String header, String detail) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(header);
		alert.setContentText(detail);
		alert.showAndWait();
	}
}
