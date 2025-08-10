package com.ftn.event_hopper.mapper.notifications;


import com.ftn.event_hopper.dtos.notifications.SimpleNotificationDTO;
import com.ftn.event_hopper.models.notifications.Notification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationDTOMapper {

    @Autowired
    private final ModelMapper modelMapper;
    public NotificationDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public SimpleNotificationDTO fromNotificationToSimpleDTO(Notification notification) {
        return modelMapper.map(notification, SimpleNotificationDTO.class);
    }

}
