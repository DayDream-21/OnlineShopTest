package com.slavamashkov.onlineshoptest.repository;

import com.slavamashkov.onlineshoptest.entity.Notification;
import com.slavamashkov.onlineshoptest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Set<Notification> getAllByUsers(User user);
}