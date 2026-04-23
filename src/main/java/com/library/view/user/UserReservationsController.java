
package com.library.view.user;

import com.library.model.items.Item;
import com.library.db.ReservationDao;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import java.util.List;

public class UserReservationsController {

    @FXML private TableView<Item> reservationsTable;
    @FXML private TableColumn<Item, String> titleColumn;
    @FXML private TableColumn<Item, String> creatorColumn;
    @FXML private TableColumn<Item, String> statusColumn;

    // Här injicerar du din DAO (t.ex. via en Service eller direkt)
    private ReservationDao reservationDao; 

    @FXML
    public void initialize() {
        // Koppla kolumnerna till fält i Item-klassen
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("itemTitle"));
        
        // Observera: Beroende på om det är Book eller Dvd kan dessa heta olika, 
        // du kan behöva en logik i Item för att hämta "mainCreator"
        creatorColumn.setCellValueFactory(new PropertyValueFactory<>("creator")); 
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadData();
    }

    private void loadData() {
        // Just nu använder vi test-ID USR001 som vi såg i din databasbild
        List<Item> myReservations = reservationDao.findByUser("USR001");
        reservationsTable.setItems(FXCollections.observableArrayList(myReservations));
    }

    @FXML
    private void handleBackToDashboard() {
        // Logik för att byta scen tillbaka till huvudmenyn
    }
}