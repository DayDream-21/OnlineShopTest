package com.slavamashkov.onlineshoptest.service;

import com.slavamashkov.onlineshoptest.entity.Notification;
import com.slavamashkov.onlineshoptest.entity.User;

import java.util.Set;

public interface NotificationService {
    void save(Notification notification);

    Set<Notification> getNotificationsByUser(User user);
}
