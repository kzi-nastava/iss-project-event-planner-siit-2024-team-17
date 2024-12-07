package com.ftn.event_hopper.models.solutions;


import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "services")
@DiscriminatorValue("SERVICE")
public class Service extends Product {

    @Column(nullable = false)
    private int durationMinutes;

    @Column(nullable = false)
    private int reservationWindowDays;

    @Column(nullable = false)
    private int cancellationWindowDays;

    @Column(nullable = false)
    private boolean isAutoAccept;
}
