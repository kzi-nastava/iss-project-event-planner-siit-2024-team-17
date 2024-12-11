package com.ftn.event_hopper.dtos.users.serviceProvider;


import com.ftn.event_hopper.dtos.location.CreateLocationDTO;
import com.ftn.event_hopper.dtos.users.person.CreatePersonDTO;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class CreateServiceProviderDTO extends CreatePersonDTO {
    private String companyName;
    private String companyEmail;
    private String companyDescription;
    private String companyPhoneNumber;
    private List<String> companyPhotos;
    private CreateLocationDTO companyLocation;
}
