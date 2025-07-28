package com.ftn.event_hopper.dtos.users.account;

import com.ftn.event_hopper.dtos.location.LocationDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdatedCompanyAccountDTO {
    private String companyDescription;
    private LocationDTO companyLocation;
    private String companyPhoneNumber;
}

