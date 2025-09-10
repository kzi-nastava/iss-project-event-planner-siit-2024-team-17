package com.ftn.event_hopper.services.notifications;

import com.ftn.event_hopper.dtos.notifications.CreateNotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

@Service
public class NotificationSchedulerService {

    private final TaskScheduler taskScheduler;
    private final NotificationService notificationService;

    @Autowired
    public NotificationSchedulerService(TaskScheduler taskScheduler, NotificationService notificationService) {
        this.taskScheduler = taskScheduler;
        this.notificationService = notificationService;
    }

    public void scheduleNotification(CreateNotificationDTO notificationDTO, UUID personId, LocalDateTime startTime) {
        ZonedDateTime notificationTime = startTime.minusHours(1).atZone(ZoneId.systemDefault());

        taskScheduler.schedule(() -> {
            notificationService.sendNotification(notificationDTO, personId);
        }, Date.from(notificationTime.toInstant()));
    }
}
