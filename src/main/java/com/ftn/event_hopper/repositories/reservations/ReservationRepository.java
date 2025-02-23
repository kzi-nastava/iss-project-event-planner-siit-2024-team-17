package com.ftn.event_hopper.repositories.reservations;

import com.ftn.event_hopper.models.events.Event;
import com.ftn.event_hopper.models.reservations.Reservation;
import com.ftn.event_hopper.models.solutions.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    boolean existsByProductAndEvent(Product product, Event event);

    Collection<Reservation> findByProductAndTimestamp(Product product, LocalDateTime timeStamp);
    Collection<Reservation> findByEvent(Event event);
}
