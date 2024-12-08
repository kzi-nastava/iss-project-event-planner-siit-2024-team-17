package com.ftn.event_hopper.dtos.users.serviceProvider;


import com.ftn.event_hopper.dtos.location.LocationDTO;
import com.ftn.event_hopper.dtos.users.person.ProfileForPersonDTO;
import com.ftn.event_hopper.dtos.users.person.SimplePersonDTO;
import lombok.*;

import java.sql.Time;
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
    private Time workStart;
    private Time workEnd;
    private LocationDTO companyLocation;
}
