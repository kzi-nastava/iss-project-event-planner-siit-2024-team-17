package com.ftn.event_hopper.dtos.solutions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreatedServiceDTO extends CreatedProductDTO{
    private int durationMinutes;
    private int reservationWindowDays;
    private int cancellationWindowDays;
    private boolean isAutoAccept;
}
