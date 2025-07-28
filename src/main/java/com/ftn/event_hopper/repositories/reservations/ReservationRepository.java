package com.ftn.event_hopper.repositories.reservations;

import com.ftn.event_hopper.models.events.Event;
import com.ftn.event_hopper.models.reservations.Reservation;
import com.ftn.event_hopper.models.solutions.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    boolean existsByProductAndEvent(Product product, Event event);

    @Query("SELECT r FROM Reservation r WHERE r.product = :product AND DATE(r.startTime) = DATE(:timeStamp)")
    Collection<Reservation> findByProductAndStartTime(@Param("product") Product product, @Param("timeStamp") LocalDateTime timeStamp);

    Collection<Reservation> findByEvent(Event event);
}
