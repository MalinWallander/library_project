package com.library.view.shared;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
	private void handleLoginButton() {
		openLoginDialog();
	}

	@FXML
	private void handleSearchCard() {
		openSearchDialog();
	}

	@FXML
	private void handleLoansCard() {
		// Locked – prompt user to log in
		showLoginRequiredPopup("My Loans");
	}

	@FXML
	private void handleReservationsCard() {
		// Locked – prompt user to log in
		showLoginRequiredPopup("My Reservations");
	}


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
			dialog.initOwner(rootPane.getScene().getWindow());
			dialog.setScene(App.createStyledScene(root));
			dialog.setResizable(false);
			dialog.showAndWait();

			AuthService authService = AppContext.getInstance().authService;
			if (authService.isLoggedIn()) {
				if (authService.getCurrentRole() == AuthRole.EMPLOYEE) {
					App.setRoot("staff_page");
				} else {
					App.setRoot("user_page");
				}
			}

		} catch (IOException e) {
			// TODO: Add relevant logging instead of printing stacktrace
			e.printStackTrace();
			showError("Could not open the login screen.", e.getMessage());
		}
	}

	private void openSearchDialog() {

    try {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/library/search_item.fxml"));

        Parent root = loader.load();

        Stage dialog = new Stage();

        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initStyle(StageStyle.DECORATED);

        dialog.setTitle("Search Catalog – City Library");

        dialog.setScene(
                App.createStyledScene(root, 1150, 780));

        dialog.setResizable(true);

        dialog.showAndWait();

    } catch (IOException e) {

		// TODO: Remove print stack trace, stick to relevant logging
        e.printStackTrace();
        showError("Could not open the search screen.", e.getMessage());
    }
}

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

	private void showError(String header, String detail) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(header);
		alert.setContentText(detail);
		alert.showAndWait();
	}
}
