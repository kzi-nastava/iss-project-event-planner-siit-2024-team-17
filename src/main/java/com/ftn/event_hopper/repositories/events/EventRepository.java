package com.ftn.event_hopper.repositories.events;

import com.ftn.event_hopper.models.events.Event;
import com.ftn.event_hopper.models.shared.EventPrivacyType;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> , JpaSpecificationExecutor<Event> {
    List<Event> findTop5ByLocationCityAndPrivacyAndTimeAfterOrderByMaxAttendanceDesc(String city, EventPrivacyType privacyType, LocalDateTime now);
    List<Event> findByLocationCityAndPrivacyAndTimeAfterOrderByMaxAttendanceDesc(String city, EventPrivacyType privacyType, LocalDateTime now);

    Page<Event> findAll(Specification<Event> specification, Pageable page);

    @Query("SELECT e FROM Event e LEFT JOIN FETCH e.ratings WHERE e.id = :eventId")
    Optional<Event> findByIdWithRatings(@Param("eventId") UUID eventId);


}
