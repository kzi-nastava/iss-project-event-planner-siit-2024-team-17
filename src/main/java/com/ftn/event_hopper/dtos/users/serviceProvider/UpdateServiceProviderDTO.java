package com.ftn.event_hopper.dtos.users.serviceProvider;


import com.ftn.event_hopper.dtos.users.person.UpdatePersonDTO;
import com.ftn.event_hopper.models.shared.Location;
import lombok.*;

import java.sql.Time;
import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class UpdateServiceProviderDTO extends UpdatePersonDTO {
    private String companyDescription;
    private String[] companyPhotos;
    private Time workStart;
    private Time workEnd;

    private UUID companyLocationId;
    private Collection<UUID> productsIds;

}
