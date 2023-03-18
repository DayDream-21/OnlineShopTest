package com.slavamashkov.onlineshoptest.repository;

import com.slavamashkov.onlineshoptest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User getUserByUsername(String username);
}
