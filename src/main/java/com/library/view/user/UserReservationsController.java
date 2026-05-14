package com.library.view.user;

import com.library.config.AppContext;
import com.library.model.items.Item;
import com.library.service.ReservationService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class UserReservationsController {

    @FXML
    private TableView<Item> reservationsTable;
    @FXML
    private TableColumn<Item, String> titleColumn;
    @FXML
    private TableColumn<Item, String> creatorColumn;
    @FXML
    private TableColumn<Item, String> statusColumn;

    private ReservationService reservationService;

    @FXML
    public void initialize() {

        this.reservationService = AppContext.getInstance().reservationService;

        titleColumn.setCellValueFactory(new PropertyValueFactory<>("itemTitle"));
        creatorColumn.setCellValueFactory(new PropertyValueFactory<>("creator"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadData();
    }

    private void loadData() {
        try {
            List<Item> myReservations = reservationService.getMyReservations();

            if (myReservations != null) {
                reservationsTable.setItems(FXCollections.observableArrayList(myReservations));
            }
        } catch (Exception e) {
            // TODO: Remove print stack trace, stick to relevant logging
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackToDashboard() {
        Stage stage = (Stage) reservationsTable.getScene().getWindow();
        stage.close();
    }
}