package com.library.db;

import java.util.Optional;

import com.library.model.User;

public interface UserDao {

	User createUser(User user);

	Optional<User> findById(String userId);

}