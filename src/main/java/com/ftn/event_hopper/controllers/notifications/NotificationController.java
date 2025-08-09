package com.ftn.event_hopper.controllers.notifications;

import com.ftn.event_hopper.dtos.notifications.CreateNotificationDTO;
import com.ftn.event_hopper.dtos.notifications.SimpleNotificationDTO;
import com.ftn.event_hopper.services.notifications.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Controller
public class NotificationController {
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(SimpMessagingTemplate messagingTemplate, NotificationService notificationService) {
        this.messagingTemplate = messagingTemplate;
        this.notificationService = notificationService;
    }

    @MessageMapping("/notification")
    public boolean sendNotification(CreateNotificationDTO createNotificationDTO, UUID eventId, UUID productId,UUID personId) {
        return notificationService.sendNotification(createNotificationDTO, eventId, productId, personId);
    }

    @GetMapping(value = "/api/notification/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SimpleNotificationDTO> getNotification(@PathVariable UUID id) {
        SimpleNotificationDTO notificationDTO = notificationService.findOne(id);
        if (notificationDTO == null) {
            return new ResponseEntity<SimpleNotificationDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(notificationDTO, HttpStatus.OK);
    }
}
