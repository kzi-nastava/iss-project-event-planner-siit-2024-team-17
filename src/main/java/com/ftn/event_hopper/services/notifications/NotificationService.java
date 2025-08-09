package com.ftn.event_hopper.services.notifications;

import com.ftn.event_hopper.dtos.notifications.CreateNotificationDTO;
import com.ftn.event_hopper.models.notifications.Notification;
import com.ftn.event_hopper.models.users.Person;
import com.ftn.event_hopper.repositories.notifications.NotificationRepository;
import com.ftn.event_hopper.repositories.users.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private PersonRepository personRepository;

    public void sendNotification(CreateNotificationDTO createNotificationDTO, UUID personId) {
        Notification notification = new Notification();
        notification.setContent(createNotificationDTO.getContent().trim());
        notification.setTimestamp(LocalDateTime.now());

        notificationRepository.save(notification);
        notificationRepository.flush();

        Person person = personRepository.findById(personId).orElse(null);

        if (person != null) {
            person.getNotifications().add(notification);
            personRepository.save(person);
        }

    }
}
