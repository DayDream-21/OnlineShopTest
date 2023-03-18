package com.slavamashkov.onlineshoptest.service;

import com.slavamashkov.onlineshoptest.entity.User;

public interface UserService {
    User findByUsername(String username);

    void save(User user);
}
