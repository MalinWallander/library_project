package com.library.service;

import java.util.List;

import com.library.db.ReservationDao;
import com.library.model.items.Item;

public class ReservationService {

    private final ReservationDao reservationDao;

    // 🔹 En enda källa för aktuell användare
    private String currentUserId = "USR001"; // tillfällig

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public void reserveItem(String itemId) {

        // 1. Kolla om redan reserverad
        if (reservationDao.isAlreadyReserved(itemId)) {
            throw new RuntimeException("Item is already reserved");
        }

        // 2. Hämta itemType
        String type = reservationDao.findItemType(itemId);

        // 3. Blockera tidskrifter
        if ("Periodical".equalsIgnoreCase(type)) {
            throw new RuntimeException("Tidskrifter kan inte reserveras");
        }

        if (reservationDao.hasAvailableCopy(itemId)) {
    throw new IllegalStateException("AVAILABLE");
}

        // 4. Skapa reservation
        reservationDao.createReservation(itemId, currentUserId);
    }

    public List<Item> getMyReservations() {
        return reservationDao.findByUser(currentUserId);
    }

    // 🔹 används senare vid login
    public void setCurrentUser(String userId) {
        this.currentUserId = userId;
    }
}