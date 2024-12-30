package com.ftn.event_hopper.services.events;

import com.ftn.event_hopper.dtos.categories.SimpleCategoryDTO;
import com.ftn.event_hopper.dtos.eventTypes.CreateEventTypeDTO;
import com.ftn.event_hopper.dtos.eventTypes.SimpleEventTypeDTO;
import com.ftn.event_hopper.dtos.eventTypes.UpdateEventTypeDTO;
import com.ftn.event_hopper.mapper.categories.CategoryDTOMapper;
import com.ftn.event_hopper.mapper.eventTypes.EventTypeDTOMapper;
import com.ftn.event_hopper.models.eventTypes.EventType;
import com.ftn.event_hopper.repositories.eventTypes.EventTypeRepository;
import com.ftn.event_hopper.services.categories.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventTypeService {
    @Autowired
    private EventTypeRepository eventTypeRepository;
    @Autowired
    private EventTypeDTOMapper eventTypeDTOMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryDTOMapper categoryDTOMapper;

    public List<SimpleEventTypeDTO> findAll(){
        List<SimpleEventTypeDTO> eventTypeDTOS = eventTypeDTOMapper.fromEventTypeListToSimpleDTOList(eventTypeRepository.findAll());
        for(SimpleEventTypeDTO eventTypeDTO : eventTypeDTOS){
            eventTypeDTO.setSuggestedCategories(categoryDTOMapper.fromCategoryListToSimpleDTOList(categoryService.getCategoriesForEventType(eventTypeDTO.getId())));
        }
        return eventTypeDTOS;
    }

    public SimpleEventTypeDTO findOneDTO(UUID id) {
        return eventTypeDTOMapper.fromEventTypeToSimpleDTO(eventTypeRepository.findById(id).orElseGet(null));
    }

    public EventType findOne(UUID id) {
        return eventTypeRepository.findById(id).orElseGet(null);
    }

    public SimpleEventTypeDTO create(CreateEventTypeDTO createDTO) {
        EventType createEventType = new EventType();
        createEventType.setName(createDTO.getName());
        createEventType.setDescription(createDTO.getDescription());
        createEventType.setDeactivated(false);
        createEventType = this.save(createEventType);
        List<UUID> categoriesIds = createDTO.getSuggestedCategories().stream()
                .map(SimpleCategoryDTO::getId).toList();
        categoryService.addEventType(createEventType, categoriesIds);
        return eventTypeDTOMapper.fromEventTypeToSimpleDTO(createEventType);
    }


    public SimpleEventTypeDTO update(UUID id, UpdateEventTypeDTO updateEventTypeDTO) {
        EventType eventType = this.findOne(id);
        eventType.setDescription(updateEventTypeDTO.getDescription());
        categoryService.manageEventTypes(eventType.getId(), updateEventTypeDTO.getSuggestedCategories());


        this.save(eventType);
        return eventTypeDTOMapper.fromEventTypeToSimpleDTO(this.findOne(id));
    }


    public void deactivate(UUID id) {
        EventType eventType = this.findOne(id);
        eventType.setDeactivated(true);
        this.save(eventType);
    }

    public EventType save(EventType eventType) {
        return eventTypeRepository.save(eventType);
    }

}
