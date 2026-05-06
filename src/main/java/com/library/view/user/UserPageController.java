package com.library.view.user;

import com.library.App;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

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
	public void initialize() {
		// TODO: replace with real data from your services
		populateLoans();
		populateReservations();
	}

	private void populateLoans() {
		// Example — replace with real loan data
		String[][] loans = {
				{ "The Great Gatsby", "Due: May 1" },
				{ "1984", "Due: May 5" },
				{ "Inception (DVD)", "Due: Apr 28" }
		};

		activeLoansLabel.setText(loans.length + " active");

		for (String[] loan : loans) {
			recentLoansContainer.getChildren().add(createLoanRow(loan[0], loan[1]));
		}
	}

	private void populateReservations() {
		// Example — replace with real reservation data
		String[][] reservations = {
				{ "To Kill a Mockingbird", "Ready" }
		};

		pendingReservationsLabel.setText(reservations.length + " pending");

		for (String[] res : reservations) {
			pendingReservationsContainer.getChildren().add(
					createReservationRow(res[0], res[1]));
		}
	}

	private HBox createLoanRow(String title, String due) {
		HBox row = new HBox();
		row.getStyleClass().add("loan-row");

		Label titleLabel = new Label(title);
		titleLabel.getStyleClass().add("loan-row-title");

		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);

		Label dueLabel = new Label(due);
		dueLabel.getStyleClass().add("loan-row-due");

		row.getChildren().addAll(titleLabel, spacer, dueLabel);
		return row;
	}

	private HBox createReservationRow(String title, String status) {
		HBox row = new HBox();
		row.getStyleClass().add("loan-row");

		Label titleLabel = new Label(title);
		titleLabel.getStyleClass().add("loan-row-title");

		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);

		Label statusLabel = new Label(status);
		statusLabel.getStyleClass().add("reservation-status-ready");

		row.getChildren().addAll(titleLabel, spacer, statusLabel);
		return row;
	}

	@FXML
	private void handleLogout() {
		App.setRoot("first_page");
	}

	@FXML
	private void handleSearch() {
		App.setRoot("search_item");
	}

	@FXML
	private void handleLoans() {
		App.setRoot("my_loans");
	}

	@FXML
	private void handleReservations() {
		App.setRoot("my_reservations");
	}
}