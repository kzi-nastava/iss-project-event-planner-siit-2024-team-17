package com.ftn.event_hopper.service.events;


import com.ftn.event_hopper.repositories.users.PersonRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@Transactional
public class EventServiceTest {

    @Autowired
    private PersonRepository yourRepository;

    @Test
    void testReadData() {
        var entities = yourRepository.findAll();
        assertFalse(entities.isEmpty());
        entities.forEach(System.out::println);
    }
}

