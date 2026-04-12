package com.library.service;

import com.library.model.User;
import com.library.db.UserDao;

import java.time.LocalDate;
import java.util.UUID;

public class UserService {

    UserDao dao;

    public UserService(UserDao dao) {
        this.dao = dao;
    }

    public void createUser(String fName,
            String lName,
            String email,
            LocalDate dateOfBirth,
            String categoryId,
            String phoneNumber) {

        UUID userId = UUID.randomUUID();
        User newUser = new User(userId, fName, lName, email, dateOfBirth, categoryId, phoneNumber);

        dao.createUser(newUser);
        System.out.println("User created: " + newUser);
    }
}
