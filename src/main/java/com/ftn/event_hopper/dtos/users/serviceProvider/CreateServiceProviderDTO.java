package com.ftn.event_hopper.dtos.users.serviceProvider;


import com.ftn.event_hopper.dtos.location.LocationDTO;
import com.ftn.event_hopper.dtos.solutions.SimpleProductDTO;
import com.ftn.event_hopper.dtos.users.person.CreatePersonDTO;
import lombok.*;

import java.sql.Time;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class CreateServiceProviderDTO extends CreatePersonDTO {
    private String companyName;
    private String companyEmail;
    private String companyDescription;
    private List<String> companyPhotos;
    private Time workStart;

    private LocationDTO companyLocation;
    private Set<SimpleProductDTO> products;
}
