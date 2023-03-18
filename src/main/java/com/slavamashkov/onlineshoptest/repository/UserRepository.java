package com.slavamashkov.onlineshoptest.repository;

import com.slavamashkov.onlineshoptest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User getUserByUsername(String username);
    User getUserByEmail(String email);
}
