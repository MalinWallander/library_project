package com.library.service;

import java.util.List;

import com.library.db.ReservationDao;
import com.library.db.UserDao;
import com.library.model.items.Item;
import com.library.service.AuthService;

public class ReservationService {

    private final ReservationDao reservationDao;
    private final AuthService authService;
    // TODO: Never used
    private final UserDao dao;

    // TODO: UserDao not used, remove as input
    public ReservationService(UserDao dao, ReservationDao reservationDao, AuthService authService) {
        // TODO: dao not used
        this.dao = dao;
        this.reservationDao = reservationDao;
        this.authService = authService;
    }

    public void reserveItem(String itemId) {

        // 🔐 Kolla login
        if (!authService.isLoggedIn()) {
            // TODO: Use either english or swedish, not both.
            throw new RuntimeException("Du måste vara inloggad");
        }

        String userId = authService.getCurrentSession().getUserId().toString();

        // 1. Redan reserverad?
        if (reservationDao.isAlreadyReserved(itemId)) {
            throw new RuntimeException("Item is already reserved");
        }

        // 2. Typ
        String type = reservationDao.findItemType(itemId);

        // 3. Blockera tidskrifter
        if ("Periodical".equalsIgnoreCase(type)) {
            // TODO: Use either english or swedish, not both.
            throw new RuntimeException("Tidskrifter kan inte reserveras");
        }

        // 4. Endast reservera om EJ tillgänglig
        if (reservationDao.hasAvailableCopy(itemId)) {
            throw new IllegalStateException("Item is available - no reservation needed");
        }

        // 5. Skapa reservation
        reservationDao.createReservation(itemId, userId);
    }
    public List<Item> getMyReservations() {

        if (!authService.isLoggedIn()) {
            // TODO: Use either english or swedish, not both.
            throw new RuntimeException("Du måste vara inloggad");
        }

        // TODO: .toString() is redundant, can be removed.
        String userId = authService.getCurrentSession().getUserId().toString();

        return reservationDao.findByUser(userId);
}
}


