package com.ftn.event_hopper.services.notifications;

import com.ftn.event_hopper.dtos.notifications.CreateNotificationDTO;
import com.ftn.event_hopper.dtos.notifications.SimpleNotificationDTO;
import com.ftn.event_hopper.models.events.Event;
import com.ftn.event_hopper.models.notifications.Notification;
import com.ftn.event_hopper.models.solutions.Product;
import com.ftn.event_hopper.models.users.Account;
import com.ftn.event_hopper.models.users.Person;
import com.ftn.event_hopper.repositories.events.EventRepository;
import com.ftn.event_hopper.repositories.notifications.NotificationRepository;
import com.ftn.event_hopper.repositories.solutions.ProductRepository;
import com.ftn.event_hopper.repositories.users.AccountRepository;
import com.ftn.event_hopper.repositories.users.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    public boolean sendNotification(CreateNotificationDTO createNotificationDTO,UUID eventId, UUID productID, UUID personId) {
        Notification notification = new Notification();
        notification.setContent(createNotificationDTO.getContent().trim());
        notification.setTimestamp(LocalDateTime.now());

        Event event = eventRepository.findById(eventId).orElse(null);
        Product product = productRepository.findById(productID).orElse(null);

        if (event == null && product == null) {
            return false;
        }

        notification.setEvent(event);
        notification.setProduct(product);

        notificationRepository.save(notification);
        notificationRepository.flush();

        Person person = personRepository.findById(personId).orElse(null);

        if (person != null) {
            person.getNotifications().add(notification);
            personRepository.save(person);
            Account account = accountRepository.findByPersonId(personId).orElse(null);
            if (account == null) {
                return false;
            }

            messagingTemplate.convertAndSendToUser(account.getUsername(),"/topic/notifications", notification);
            return true;
        }

        return false;

    }

    public SimpleNotificationDTO findOne(UUID id) {
        Notification notification = notificationRepository.findById(id).orElse(null);
        SimpleNotificationDTO simpleNotificationDTO = new SimpleNotificationDTO();
        simpleNotificationDTO.setContent(notification.getContent());
        simpleNotificationDTO.setTimestamp(notification.getTimestamp());
        return simpleNotificationDTO;
    }
}
