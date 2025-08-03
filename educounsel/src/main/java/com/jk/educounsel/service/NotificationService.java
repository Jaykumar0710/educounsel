package com.jk.educounsel.service;

import com.jk.educounsel.model.Notification;
import com.jk.educounsel.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

@Autowired
    public NotificationRepository notificationRepository;

        public List<Notification> getAllNotification(){
            return  notificationRepository.findAll();
        }

        public void saveNotification(Notification notification){
            notificationRepository.save(notification);
        }

        public Notification saveNotifications(Notification notification){
            return  notificationRepository.save(notification);
        }
        public Notification getNotification(Long id){
            return  notificationRepository.findById(id).orElse(null);
        }
        public void deleteNotification(Long id){
            notificationRepository.deleteById(id);
        }

    public Object countTimelines() {
            return notificationRepository.count();
    }

    public Object getAllNotifications() {
            return notificationRepository.findAll();
    }
}
