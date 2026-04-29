package com.library.view.user;

import com.library.model.items.Item;
import com.library.service.ReservationService;
import com.library.config.AppContext;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;

import java.io.IOException;
import java.util.List;

public class UserReservationsController {

    @FXML private TableView<Item> reservationsTable;
    @FXML private TableColumn<Item, String> titleColumn;
    @FXML private TableColumn<Item, String> creatorColumn;
    @FXML private TableColumn<Item, String> statusColumn;

    private ReservationService reservationService; 

    @FXML
    public void initialize() {
        // 1. Hämta servicen från din AppContext
        // Om variabeln i AppContext är public (vilket den verkar vara i din tidigare kod)
this.reservationService = AppContext.getInstance().reservationService;

        // 2. Koppla kolumnerna (stämmer med getItemTitle() och getCreator() i Item)
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("itemTitle"));
        creatorColumn.setCellValueFactory(new PropertyValueFactory<>("creator")); 
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadData();
    }

    private void loadData() {
        try {
            // 3. Använd servicen (som vi hårdkodade till USR001 tidigare)
            List<Item> myReservations = reservationService.getMyReservations();
            
            if (myReservations != null) {
                reservationsTable.setItems(FXCollections.observableArrayList(myReservations));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
private void handleBackToDashboard() {
    try {
        // Förutsatt att din App-klass har en static metod för att byta vy
        com.library.App.setRoot("user_dashboard"); 
    } catch (IOException e) {
        e.printStackTrace();
    }

}
}