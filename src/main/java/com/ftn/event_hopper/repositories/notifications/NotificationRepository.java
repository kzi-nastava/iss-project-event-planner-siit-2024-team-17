package com.ftn.event_hopper.repositories.notifications;

import com.ftn.event_hopper.models.events.Event;
import com.ftn.event_hopper.models.notifications.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {


    List<Notification> findByEvent(Event event);

}
