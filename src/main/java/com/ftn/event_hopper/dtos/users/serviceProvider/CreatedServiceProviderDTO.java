package com.ftn.event_hopper.dtos.users.serviceProvider;


import com.ftn.event_hopper.dtos.location.LocationDTO;
import com.ftn.event_hopper.dtos.users.person.CreatedPersonDTO;
import lombok.*;

import java.sql.Time;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class CreatedServiceProviderDTO extends CreatedPersonDTO {
    private String companyName;
    private String companyEmail;
    private String companyDescription;
    private List<String> companyPhotos;
    private Time workStart;
    private LocationDTO companyLocation;
}
