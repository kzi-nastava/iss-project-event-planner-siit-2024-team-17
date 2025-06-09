package com.ftn.event_hopper.models.solutions;


import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)

@Entity
@DiscriminatorValue("SERVICE")
public class Service extends Product {

    @Column(nullable = true)
    private int durationMinutes;

    @Column(nullable = true)
    private int reservationWindowDays;

    @Column(nullable = true)
    private int cancellationWindowDays;

    @Column(nullable = true)
    private boolean isAutoAccept;
}
