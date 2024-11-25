package com.ftn.event_hopper.models.solutions;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class Service extends Product {
    private int durationMinutes;
    private int reservationWindowDays;
    private int cancellationWindowDays;
    private boolean isAutoAccept;
}
