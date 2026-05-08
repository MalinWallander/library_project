package com.library.view.shared;

import java.time.format.DateTimeFormatter;

import com.library.model.administration.Receipt;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ReceiptController {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    @FXML
    private Label itemTitleLabel;

    @FXML
    private Label itemTypeLabel;

    @FXML
    private Label memberNameLabel;

    @FXML
    private Label memberIdLabel;

    @FXML
    private Label loanDateLabel;

    @FXML
    private Label dueDateLabel;

    @FXML
    private Label loanIdLabel;

    public void receipt(Receipt receipt) {
        itemTitleLabel.setText(receipt.getItemTitle());
        itemTypeLabel.setText(receipt.getItemType());
        memberNameLabel.setText(receipt.getMemberName());
        memberIdLabel.setText("ID: " + receipt.getMemberId());
        loanDateLabel.setText(DATE_FORMATTER.format(receipt.getLoanDate()));
        dueDateLabel.setText(DATE_FORMATTER.format(receipt.getDueDate()));
        loanIdLabel.setText(receipt.getLoanId());
    }

    @FXML
    private void handleDone() {
        Stage stage = (Stage) loanIdLabel.getScene().getWindow();
        stage.close();
    }
}
