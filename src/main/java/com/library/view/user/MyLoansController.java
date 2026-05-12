package com.library.view.user;

import com.library.config.AppContext;
import com.library.model.administration.LoanSummary;
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

		List<LoanSummary> loans = AppContext.getInstance().loanService
				.getLoanSummariesForUser(session.getUserId().toString());

		subtitleLabel.setText(loans.size() + " active loan" + (loans.size() == 1 ? "" : "s"));

		if (loans.isEmpty()) {
			emptyLabel.setVisible(true);
		} else {
			for (LoanSummary loan : loans) {
				loansContainer.getChildren().add(createLoanRow(loan));
			}
		}
	}

	private HBox createLoanRow(LoanSummary loan) {
		HBox row = new HBox();
		row.getStyleClass().add("loan-row");

		VBox info = new VBox(4);
		Label titleLabel = new Label(loan.getItemTitle() != null ? loan.getItemTitle() : "Unknown title");
		titleLabel.getStyleClass().add("loan-row-title");

		Label borrowedLabel = new Label("Borrowed: " + loan.getLoanDate());
		borrowedLabel.getStyleClass().add("loan-row-due");
		info.getChildren().addAll(titleLabel, borrowedLabel);

		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);

		boolean isOverdue = loan.getDueDate() != null
				&& loan.getDueDate().isBefore(java.time.LocalDate.now());

		String dueText = loan.getDueDate() != null
				? (isOverdue ? "⚠ Overdue since: " : "Due: ") + loan.getDueDate()
				: "No due date";

		Label dueLabel = new Label(dueText);
		dueLabel.getStyleClass().add(isOverdue ? "overdue-label" : "loan-row-due");

		row.getChildren().addAll(info, spacer, dueLabel);
		return row;
	}

	@FXML
	private void handleClose() {
		Stage stage = (Stage) loansContainer.getScene().getWindow();
		stage.close();
	}
}