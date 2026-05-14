package com.library.view.user;

import java.io.IOException;

import com.library.App;
import com.library.config.AppContext;
import com.library.model.auth.CurrentSession;
import com.library.view.shared.SearchController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UserPageController {

	@FXML
	// TODO: Never used
	private VBox searchCard;
	@FXML
	// TODO: Never used
	private VBox loansCard;
	@FXML
	// TODO: Never used
	private VBox reservationsCard;
	@FXML
	// TODO: Never used
	private Label activeLoansLabel;
	@FXML
	// TODO: Never used
	private Label pendingReservationsLabel;
	@FXML
	// TODO: Never used
	private VBox recentLoansContainer;
	@FXML
	// TODO: Never used
	private VBox pendingReservationsContainer;
	@FXML
	private Label welcomeLabel;

	@FXML
	public void initialize() {
		CurrentSession session = AppContext.getInstance().authService.getCurrentSession();
		if (session != null) {
			welcomeLabel.setText("Welcome Back!");
			// TODO: Never used
			String userId = session.getUserId();
		}
	}

	@FXML
	private void handleLogout() {
		AppContext.getInstance().authService.logout();
		App.setRoot("first_page");
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
			// TODO: Remove print stack trace, stick to relevant logging
			e.printStackTrace();
		}
	}

	@FXML
	private void handleLoans() {
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/com/library/my_loans.fxml"));
			Parent root = loader.load();

			Stage stage = new Stage();
			stage.setTitle("My Loans");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(App.createStyledScene(root));
			stage.showAndWait();
		} catch (IOException e) {
			// TODO: Remove print stack trace, stick to relevant logging
			e.printStackTrace();
		}
	}

	@FXML
	private void handleReservations() {
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/com/library/user_reservation.fxml"));
			Parent root = loader.load();

			Stage stage = new Stage();
			stage.setTitle("My Reservations");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(App.createStyledScene(root));
			stage.showAndWait();
		} catch (IOException e) {
			// TODO: Remove print stack trace, stick to relevant logging
			e.printStackTrace();
		}
	}
}
