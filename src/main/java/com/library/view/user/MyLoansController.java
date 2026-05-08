package com.library.view.user;

import com.library.config.AppContext;
import com.library.model.administration.Loan;
import com.library.model.auth.CurrentSession;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class MyLoansController {

	@FXML
	private VBox loansContainer;
	@FXML
	private Label subtitleLabel;
	@FXML
	private Label emptyLabel;

	@FXML
	public void initialize() {
		CurrentSession session = AppContext.getInstance().authService.getCurrentSession();
		if (session == null)
			return;

		List<Loan> loans = AppContext.getInstance().loanService
				.getLoansForUser(session.getUserId().toString());

		subtitleLabel.setText(loans.size() + " active loan" + (loans.size() == 1 ? "" : "s"));

		if (loans.isEmpty()) {
			emptyLabel.setVisible(true);
		} else {
			for (Loan loan : loans) {
				loansContainer.getChildren().add(createLoanRow(loan));
			}
		}
	}

	private HBox createLoanRow(Loan loan) {
		HBox row = new HBox();
		row.getStyleClass().add("loan-row");

		// copyId is what we have — ideally you'd look up the title
		Label titleLabel = new Label("Copy: " + loan.getCopyId());
		titleLabel.getStyleClass().add("loan-row-title");

		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);

		String dateText = loan.getReturnDate() != null
				? "Due: " + loan.getReturnDate()
				: "Loaned: " + loan.getLoanDate();
		Label dateLabel = new Label(dateText);
		dateLabel.getStyleClass().add("loan-row-due");

		row.getChildren().addAll(titleLabel, spacer, dateLabel);
		return row;
	}

	@FXML
	private void handleClose() {
		Stage stage = (Stage) loansContainer.getScene().getWindow();
		stage.close();
	}
}