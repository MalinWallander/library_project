package com.library.db;

import com.library.model.items.Item;
import java.util.List;

public interface ReservationDao {

    void createReservation(String itemId, String userId);

    boolean isAlreadyReserved(String itemId);

    List<Item> findByUser(String userId);
}
