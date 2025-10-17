package com.ftn.event_hopper.repositories;

import com.ftn.event_hopper.models.events.Event;
import com.ftn.event_hopper.models.locations.Location;
import com.ftn.event_hopper.models.reservations.Reservation;
import com.ftn.event_hopper.models.shared.EventPrivacyType;
import com.ftn.event_hopper.models.shared.ProductStatus;
import com.ftn.event_hopper.models.solutions.Product;
import com.ftn.event_hopper.repositories.events.EventRepository;
import com.ftn.event_hopper.repositories.locations.LocationRepository;
import com.ftn.event_hopper.repositories.reservations.ReservationRepository;
import com.ftn.event_hopper.repositories.solutions.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Collection;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class ReservationRepositoryTest {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private LocationRepository locationRepository;

    private Product testProduct;


    @Test
    public void shouldReturnReservationsForGivenProductAndDate() {

        Location location = new Location();
        location.setCity("City");
        location.setAddress("Address");
        location.setLatitude(0.0);
        location.setLongitude(0.0);
        location = locationRepository.save(location);

        Product product = new Product();
        product.setName("Test Product");
        product.setAvailable(true);
        product.setVisible(true);
        product.setStatus(ProductStatus.APPROVED);
        product.setEditTimestamp(LocalDateTime.now());
        product.setDeleted(false);
        final Product savedProduct = productRepository.save(product);

        Event event = new Event();
        event.setName("Test Event");
        event.setMaxAttendance(600);
        event.setDescription("Test Description");
        event.setTime(LocalDateTime.now().plusDays(1));
        event.setPrivacy(EventPrivacyType.PRIVATE);
        event.setLocation(location);

        event = eventRepository.save(event);

        LocalDateTime reservationDate = LocalDateTime.of(2025, 10, 30, 10, 0);
        Reservation reservation = new Reservation();
        reservation.setProduct(savedProduct);
        reservation.setEvent(event);
        reservation.setTimestamp(LocalDateTime.now());
        reservation.setStartTime(reservationDate);
        reservation.setEndTime(reservationDate.plusHours(1));
        reservation = reservationRepository.save(reservation);

        LocalDateTime startOfDay = reservationDate.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = reservationDate.toLocalDate().atTime(23, 59, 59);

        Collection<Reservation> found = reservationRepository.findAll()
                .stream()
                .filter(r -> r.getProduct().equals(savedProduct))
                .filter(r -> !r.getStartTime().isBefore(startOfDay) && !r.getStartTime().isAfter(endOfDay))
                .toList();

        assertThat(found).isNotEmpty();
        assertThat(found).contains(reservation);
    }

    @Test
    public void shouldReturnEmptyCollectionIfNoReservationsForGivenDate() {
        Product product = new Product();
        product.setName("Test Product");
        product.setAvailable(true);
        product.setVisible(true);
        product.setStatus(ProductStatus.APPROVED);
        product.setEditTimestamp(LocalDateTime.now());
        product.setDeleted(false);
        final Product savedProduct = productRepository.save(product);

        LocalDateTime reservationDate = LocalDateTime.of(2025, 10, 30, 10, 0);
        LocalDateTime startOfDay = reservationDate.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = reservationDate.toLocalDate().atTime(23, 59, 59);

        Collection<Reservation> found = reservationRepository.findAll()
                .stream()
                .filter(r -> r.getProduct().equals(savedProduct))
                .filter(r -> !r.getStartTime().isBefore(startOfDay) && !r.getStartTime().isAfter(endOfDay))
                .toList();
        assertThat(found).isEmpty();
    }
}
