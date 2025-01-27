package com.ftn.event_hopper.mapper.reservations;

import com.ftn.event_hopper.dtos.events.SimpleEventDTO;
import com.ftn.event_hopper.dtos.reservations.CreateReservationServiceDTO;
import com.ftn.event_hopper.dtos.reservations.CreatedReservationServiceDTO;
import com.ftn.event_hopper.dtos.reservations.ReservationDTO;
import com.ftn.event_hopper.dtos.solutions.SimpleProductDTO;
import com.ftn.event_hopper.mapper.events.EventDTOMapper;
import com.ftn.event_hopper.mapper.solutions.ProductDTOMapper;
import com.ftn.event_hopper.models.events.Event;
import com.ftn.event_hopper.models.reservations.Reservation;
import com.ftn.event_hopper.models.solutions.Product;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ReservationDTOMapper {
    private final ModelMapper modelMapper;
    private final ProductDTOMapper productDTOMapper;
    private final EventDTOMapper eventDTOMapper;

    public ReservationDTOMapper(ModelMapper modelMapper, EventDTOMapper eventDTOMapper, ProductDTOMapper productDTOMapper) {
        this.modelMapper = modelMapper;
        this.eventDTOMapper = eventDTOMapper;
        this.productDTOMapper = productDTOMapper;
        configureMappings();
    }

    public void configureMappings() {
        Converter<Event, SimpleEventDTO> reservationEventConverter = context ->
                eventDTOMapper.fromEventToSimpleDTO(context.getSource());

        modelMapper.typeMap(Reservation.class, ReservationDTO.class)
                .addMappings(mapper -> mapper.using(reservationEventConverter)
                        .map(Reservation::getEvent, ReservationDTO::setEvent));

        Converter<Product, SimpleProductDTO> reservationProductConverter = context ->
                productDTOMapper.fromProductToSimpleDTO(context.getSource());

        modelMapper.typeMap(Reservation.class, ReservationDTO.class)
                .addMappings(mapper -> mapper.using(reservationProductConverter)
                        .map(Reservation::getProduct, ReservationDTO::setProduct));

    }

    public Reservation fromCreateReservationServiceDTOtoReservation(CreateReservationServiceDTO createReservation) {
        return modelMapper.map(createReservation, Reservation.class);
    }

    public CreatedReservationServiceDTO fromReservationToCreatedReservationServiceDTO(Reservation reservation) {
        return modelMapper.map(reservation, CreatedReservationServiceDTO.class);
    }
}
