package com.library.view.user;

import java.io.IOException;
import java.util.UUID;

import com.library.App;
import com.library.config.AppContext;
import com.library.model.auth.CurrentSession;
import com.library.view.shared.SearchController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UserPageController {

	@FXML
	private VBox searchCard;
	@FXML
	private VBox loansCard;
	@FXML
	private VBox reservationsCard;
	@FXML
	private Label activeLoansLabel;
	@FXML
	private Label pendingReservationsLabel;
	@FXML
	private VBox recentLoansContainer;
	@FXML
	private VBox pendingReservationsContainer;
	@FXML
	private Label welcomeLabel;

	@FXML
	public void initialize() {
		CurrentSession session = AppContext.getInstance().authService.getCurrentSession();
		if (session != null) {
			// Personalize heading
			welcomeLabel.setText("Welcome Back!"); // you could add name if stored in session

			// Load real data using the user's ID
			UUID userId = session.getUserId();
			// e.g. loanService.getLoansForUser(userId)
		}
		// populateLoans();
		// populateReservations();
	}

	// activeLoansLabel.setText(loans.length + " active");

	// for (String[] loan : loans) {
	// recentLoansContainer.getChildren().add(createLoanRow(loan[0], loan[1]));
	// }
	// }

	// private void populateReservations() {
	// // Example — replace with real reservation data
	// String[][] reservations = {
	// { "To Kill a Mockingbird", "Ready" }
	// };

	// pendingReservationsLabel.setText(reservations.length + " pending");

	// for (String[] res : reservations) {
	// pendingReservationsContainer.getChildren().add(
	// createReservationRow(res[0], res[1]));
	// }
	// }

	// private HBox createLoanRow(String title, String due) {
	// HBox row = new HBox();
	// row.getStyleClass().add("loan-row");

	// Label titleLabel = new Label(title);
	// titleLabel.getStyleClass().add("loan-row-title");

	// Region spacer = new Region();
	// HBox.setHgrow(spacer, Priority.ALWAYS);

	// Label dueLabel = new Label(due);
	// dueLabel.getStyleClass().add("loan-row-due");

	// row.getChildren().addAll(titleLabel, spacer, dueLabel);
	// return row;
	// }

	// private HBox createReservationRow(String title, String status) {
	// HBox row = new HBox();
	// row.getStyleClass().add("loan-row");

	// Label titleLabel = new Label(title);
	// titleLabel.getStyleClass().add("loan-row-title");

	// Region spacer = new Region();
	// HBox.setHgrow(spacer, Priority.ALWAYS);

	// Label statusLabel = new Label(status);
	// statusLabel.getStyleClass().add("reservation-status-ready");

	// row.getChildren().addAll(titleLabel, spacer, statusLabel);
	// return row;
	// }

	@FXML
	private void handleLogout() {
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
			stage.setScene(new Scene(root));
			stage.showAndWait();
		} catch (IOException e) {
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
			stage.setScene(new Scene(root));
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}