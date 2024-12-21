package com.ftn.event_hopper.dtos.users.account;

import com.ftn.event_hopper.dtos.location.LocationDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UpdateCompanyAccountDTO {
    private UUID id;
    private String companyDescription;
    private LocationDTO companyLocation;
    private String companyPhoneNumber;
}
