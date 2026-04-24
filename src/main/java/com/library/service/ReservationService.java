package com.library.service;

import com.library.db.ReservationDao;
import com.library.model.items.Item;
import java.util.List;


public class ReservationService {

    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    String userId = "1"; // tillfällig testdata, ska hämtas från sessionen när den är implementerad

public void reserveItem(String itemId) {

      String userId = "1";

    if (reservationDao.isAlreadyReserved(itemId)) {
        throw new RuntimeException("Item is already reserved");
    }

    reservationDao.createReservation(itemId, userId);
}
public List<Item> getMyReservations() {
    return reservationDao.findByUser("USR001"); // Se till att detta matchar din SQL-tabell exakt
}

}
