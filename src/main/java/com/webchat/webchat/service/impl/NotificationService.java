package com.webchat.webchat.service.impl;

import com.webchat.webchat.entities.Notification;
import com.webchat.webchat.repository.NotificationRepository;
import com.webchat.webchat.service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService implements INotificationService {
    @Autowired
    private NotificationRepository notificationRepo;

    @Override
    public List<Notification> findByUser(String username) {
        List<Notification> list = notificationRepo.findByUser(username);
        return list.isEmpty() ? null : list;
    }

    @Override
    public List<Notification> findByUserAndStatus(String username, String status) {
        List<Notification> list = notificationRepo.findByUserAndStatus(username, status);
        return list.isEmpty() ? null : list;
    }

    @Override
    public void updateNotification(List<Notification> notifications) {
        notificationRepo.saveAll(notifications);
    }

    @Override
    public void saveOneNotification(Notification notifications) {
        notificationRepo.save(notifications);
    }
}
