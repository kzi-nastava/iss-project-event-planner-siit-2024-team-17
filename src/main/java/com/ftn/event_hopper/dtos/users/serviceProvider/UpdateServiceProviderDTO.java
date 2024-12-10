package com.ftn.event_hopper.dtos.users.serviceProvider;


import com.ftn.event_hopper.dtos.location.LocationDTO;
import com.ftn.event_hopper.dtos.users.person.UpdatePersonDTO;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class UpdateServiceProviderDTO extends UpdatePersonDTO {
    private String companyDescription;
    private List<String> companyPhotos;
    private LocalTime workStart;
    private LocalTime workEnd;

    private LocationDTO companyLocation;
}
