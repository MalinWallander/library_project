package com.library.view.user;

import com.library.model.items.Item;
import com.library.service.ReservationService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import com.library.config.AppContext;

public class ReservationController {

    private Item item;
    private ReservationService reservationService;

    @FXML private Label titleLabel;
    @FXML private Label creatorLabel;

    @FXML
    public void initialize() {
        // Hämta servicen en gång vid start
        this.reservationService = AppContext.getInstance().reservationService;
    }

    public void setItem(Item item) {
        this.item = item;
        if (item != null) {
            titleLabel.setText(item.getItemTitle());
            creatorLabel.setText(item.getCreator());
        }
    }

    @FXML
    private void handleConfirmReservation() {
        if (item == null) return;

        try {
            // Här sker själva magin mot databasen
            reservationService.reserveItem(item.getItemId());

            showAlert("Reservation skapad för " + item.getItemTitle() + "!");
            
            // Stäng fönstret automatiskt efter att användaren klickat OK på alerten
            closeWindow();

        } catch (Exception e) {
            // Visar felmeddelandet (t.ex. "Item is already reserved")
            showAlert("Kunde inte reservera: " + e.getMessage());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) titleLabel.getScene().getWindow();
        stage.close();
    }
}

