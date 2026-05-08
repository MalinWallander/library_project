package com.library.view.shared;

import com.library.config.AppContext;
import com.library.model.administration.Loan;
import com.library.service.LoanService;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class OverdueLoansController {

    @FXML
    private TableView<Loan> loanTable;

    @FXML
    private TableColumn<Loan, String> copyIdColumn;

    @FXML
    private TableColumn<Loan, String> userIdColumn;

    @FXML
    private TableColumn<Loan, String> dueDateColumn;

    private LoanService loanService;

    @FXML
    public void initialize() {

        loanService = AppContext.getInstance().loanService;

        copyIdColumn.setCellValueFactory(new PropertyValueFactory<>("copyId"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

        loanTable.setItems(
            FXCollections.observableArrayList(
                loanService.getOverdueLoans()
            )
        );
    }
}