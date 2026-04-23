package com.library.view.user;

import com.library.model.items.Item;
import com.library.service.ReservationService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import com.library.config.AppContext;

public class ReservationController {


    private Item item;
    private ReservationService reservationService;

    @FXML private Label titleLabel;
    @FXML private Label creatorLabel;

    @FXML
    public void initialize() {
        this.reservationService = com.library.config.AppContext
            .getInstance().reservationService;
    }

    public void setItem(Item item) {
        this.item = item;

        // visa data i UI
        titleLabel.setText(item.getItemTitle());
        creatorLabel.setText(item.getCreator());
    }

    @FXML
    private void handleConfirmReservation() {
        reservationService.reserveItem(item.getItemId());
    }
}

