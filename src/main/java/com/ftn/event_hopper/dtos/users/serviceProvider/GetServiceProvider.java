package com.ftn.event_hopper.dtos.users.serviceProvider;


import com.ftn.event_hopper.dtos.users.person.CreatedPersonDTO;
import com.ftn.event_hopper.models.shared.Location;
import com.ftn.event_hopper.models.solutions.Product;
import lombok.*;

import java.sql.Time;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class GetServiceProvider extends CreatedPersonDTO {
    private String companyName;
    private String companyEmail;
    private String companyDescription;
    private String[] companyPhotos;
    private Time workStart;
    private Time workEnd;

    private Location companyLocation;
    private Collection<Product> products;

}
