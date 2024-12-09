package com.ftn.event_hopper.dtos.users.serviceProvider;


import com.ftn.event_hopper.dtos.location.LocationDTO;
import com.ftn.event_hopper.dtos.solutions.SimpleProductDTO;
import com.ftn.event_hopper.dtos.users.person.CreatePersonDTO;
import lombok.*;

import java.time.LocalTime;
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
    private LocalTime workStart;

    private LocationDTO companyLocation;
    private Set<SimpleProductDTO> products;
}
