package com.ftn.event_hopper.dtos.users.serviceProvider;


import com.ftn.event_hopper.dtos.location.SimpleLocationDTO;
import com.ftn.event_hopper.dtos.users.person.UpdatePersonDTO;
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
public class UpdateServiceProviderDTO extends UpdatePersonDTO {
    private String companyDescription;
    private List<String> companyPhotos;
    private Time workStart;
    private Time workEnd;

    private SimpleLocationDTO companyLocation;
}
