package com.library.db;

import java.util.List;

import com.library.model.items.Item;

public interface ReservationDao {

    void createReservation(String itemId, String userId);

    boolean isAlreadyReserved(String itemId);

    List<Item> findByUser(String userId);
}
