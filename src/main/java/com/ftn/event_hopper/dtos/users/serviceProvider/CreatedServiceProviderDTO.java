package com.ftn.event_hopper.dtos.users.serviceProvider;


import com.ftn.event_hopper.dtos.location.LocationDTO;
import com.ftn.event_hopper.dtos.users.person.CreatedPersonDTO;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class CreatedServiceProviderDTO extends CreatedPersonDTO {
    private String companyName;
    private String companyEmail;
    private String companyDescription;
    private String companyPhoneNumber;
    private List<String> companyPhotos;
    private LocalTime workStart;
    private LocationDTO companyLocation;
}
