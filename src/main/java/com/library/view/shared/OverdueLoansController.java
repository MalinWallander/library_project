package com.library.view.shared;

import com.library.config.AppContext;
import com.library.model.administration.OverdueLoanSummary;
import com.library.service.LoanService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class OverdueLoansController {

    @FXML
    private TableView<OverdueLoanSummary> loanTable;
    @FXML
    private TableColumn<OverdueLoanSummary, String> itemTitleColumn;
    @FXML
    private TableColumn<OverdueLoanSummary, String> memberNameColumn;
    @FXML
    private TableColumn<OverdueLoanSummary, String> dueDateColumn;
    @FXML
    private TableColumn<OverdueLoanSummary, String> daysOverdueColumn;

    @FXML
    public void initialize() {
        LoanService loanService = AppContext.getInstance().loanService;

        itemTitleColumn.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getItemTitle()));
        memberNameColumn.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getMemberName()));
        dueDateColumn.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getDueDate().toString()));
        daysOverdueColumn.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getDaysOverdue() + " days"));

        loanTable.setItems(FXCollections.observableArrayList(
                loanService.getOverdueLoanSummaries()));
    }
}