package com.ftn.event_hopper.dtos.users.serviceProvider;


import com.ftn.event_hopper.dtos.location.LocationDTO;
import com.ftn.event_hopper.dtos.users.person.ProfileForPersonDTO;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
/*Main difference between this and simple is that the favorite events/products are included, as well as
* the attending events necessary for the calendar*/
public class ProfileForServiceProviderDTO extends ProfileForPersonDTO {
    private String companyName;
    private String companyEmail;
    private String companyDescription;
    private List<String> companyPhotos;
    private LocalTime workStart;
    private LocalTime workEnd;
    private LocationDTO companyLocation;
}
