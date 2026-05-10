package com.library.db;

import java.util.Optional;
import java.util.UUID;

import com.library.model.User;

public interface UserDao {

	public User createUser(User user);

	Optional<User> findById(UUID userId);

}