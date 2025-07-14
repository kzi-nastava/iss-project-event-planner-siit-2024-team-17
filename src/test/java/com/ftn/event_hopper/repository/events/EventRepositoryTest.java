package com.ftn.event_hopper.repository.events;

import com.ftn.event_hopper.repositories.events.EventRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test") // uses application-test.properties
public class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Test
    void testFindByIdWithRatings_returnsEventWithRatings() {
        UUID existingEventId = UUID.fromString("3f7b2c9e-4a6f-4d5b-b8c1-7a2f9e3b6d4a");
        var optional = eventRepository.findByIdWithRatings(existingEventId);

        assertTrue(optional.isPresent());

        var event = optional.get();
        assertNotNull(event.getRatings());
        assertFalse(event.getRatings().isEmpty());

        System.out.println("Ratings: " + event.getRatings());
    }

}
