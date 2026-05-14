package com.library.service;

import java.util.List;

import com.library.db.ReservationDao;
import com.library.model.items.Item;

public class ReservationService {

    private final ReservationDao reservationDao;
    private final AuthService authService;

    public ReservationService(ReservationDao reservationDao, AuthService authService) {
        this.reservationDao = reservationDao;
        this.authService = authService;
    }

    public void reserveItem(String itemId) {

        if (!authService.isLoggedIn()) {
            throw new RuntimeException("You must be logged in to reserve an item");
        }

        String userId = authService.getCurrentSession().getUserId().toString();

        if (reservationDao.isAlreadyReserved(itemId)) {
            throw new RuntimeException("Item is already reserved");
        }

        String type = reservationDao.findItemType(itemId);

        if ("Periodical".equalsIgnoreCase(type)) {
            throw new RuntimeException("Periodicals cannot be reserved");
        }

        if (reservationDao.hasAvailableCopy(itemId)) {
            throw new IllegalStateException("Item is available - no reservation needed");
        }

        reservationDao.createReservation(itemId, userId);
    }

    public List<Item> getMyReservations() {

        if (!authService.isLoggedIn()) {
            throw new RuntimeException("You must be logged in to view your reservations");
        }

        String userId = authService.getCurrentSession().getUserId();

        return reservationDao.findByUser(userId);
    }
}
