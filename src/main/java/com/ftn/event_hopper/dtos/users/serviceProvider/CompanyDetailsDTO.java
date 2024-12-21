package com.ftn.event_hopper.dtos.users.serviceProvider;

import lombok.*;

import com.ftn.event_hopper.models.locations.Location;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CompanyDetailsDTO {

    private String companyName;
    private String companyEmail;
    private String companyPhoneNumber;
    private String companyDescription;
    private List<String> companyPhotos;
    private Location companyLocation;
}
