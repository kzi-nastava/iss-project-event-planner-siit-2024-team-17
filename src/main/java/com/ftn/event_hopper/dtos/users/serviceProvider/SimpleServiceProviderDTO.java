package com.ftn.event_hopper.dtos.users.serviceProvider;


import com.ftn.event_hopper.dtos.location.LocationDTO;
import com.ftn.event_hopper.dtos.users.person.SimplePersonDTO;
import lombok.*;

import java.sql.Time;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class SimpleServiceProviderDTO extends SimplePersonDTO {
    private String companyName;
    private String companyEmail;
    private String companyDescription;
    private List<String> companyPhotos;
    private Time workStart;
    private Time workEnd;

    private LocationDTO companyLocation;
}
