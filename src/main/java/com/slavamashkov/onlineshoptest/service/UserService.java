package com.slavamashkov.onlineshoptest.service;

import com.slavamashkov.onlineshoptest.entity.User;

import java.util.List;

public interface UserService {
    User getUserByUsername(String username);

    List<User> getAllUsers();

    void save(User user);

    void delete(User user);

    User getUserById(Long id);
}
